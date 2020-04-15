package br.com.breadware.dao;

import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.AssociateFieldOrder;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.properties.google.GoogleSheetsProperties;
import br.com.breadware.util.SpreadsheetUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class AssociateDao {

    private static final String INPUT_OPTION_RAW = "RAW";

    private final Sheets sheets;

    private final GoogleSheetsProperties googleSheetsProperties;

    private final SpreadsheetUtil spreadsheetUtil;

    private final String sheetDataRange;

    @Inject
    public AssociateDao(Sheets sheets, GoogleSheetsProperties googleSheetsProperties, SpreadsheetUtil spreadsheetUtil) {
        this.sheets = sheets;
        this.googleSheetsProperties = googleSheetsProperties;
        this.spreadsheetUtil = spreadsheetUtil;
        this.sheetDataRange = calculateSheetRange(googleSheetsProperties);
    }

    public ValueRange getAll() throws DataAccessException {
        try {
            return retrieveSpreadsheetValues()
                    .get(googleSheetsProperties.getSpreadsheetId(), sheetDataRange)
                    .execute();
        } catch (IOException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_RETRIEVING_SPREADSHEET, googleSheetsProperties.getSpreadsheetId());
        }
    }

    public void replace(ValueRange valueRange) throws DataAccessException {
        try {
            retrieveSpreadsheetValues()
                    .update(googleSheetsProperties.getSpreadsheetId(), sheetDataRange, valueRange)
                    .setValueInputOption(INPUT_OPTION_RAW)
                    .execute();
        } catch (IOException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_UPDATING_VALUES_ON_SPREADSHEET, googleSheetsProperties.getSpreadsheetId());
        }
    }

    private Sheets.Spreadsheets.Values retrieveSpreadsheetValues() {
        return sheets.spreadsheets()
                .values();
    }

    private String calculateSheetRange(GoogleSheetsProperties googleSheetsProperties) {
        return spreadsheetUtil.convertToRange(googleSheetsProperties.getSheetName(), 0, AssociateFieldOrder.values().length);
    }
}
