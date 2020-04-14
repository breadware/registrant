package br.com.breadware.dao;

import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.properties.GoogleSheetsProperties;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class AssociateDao {

    private final Sheets sheets;

    private final GoogleSheetsProperties googleSheetsProperties;

    @Inject
    public AssociateDao(Sheets sheets, GoogleSheetsProperties googleSheetsProperties) {
        this.sheets = sheets;
        this.googleSheetsProperties = googleSheetsProperties;
    }

    public ValueRange getAll() throws DataAccessException {
        try {
            return retrieveSpreadsheetValues()
                    .get(googleSheetsProperties.getSheetId(), googleSheetsProperties.getDataRange())
                    .execute();
        } catch (IOException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_RETRIEVING_SPREADSHEET, googleSheetsProperties.getSheetId());
        }
    }

    public void append(ValueRange valueRange) throws DataAccessException {
        try {
            retrieveSpreadsheetValues()
                    .append(googleSheetsProperties.getSheetId(), googleSheetsProperties.getDataRange(), valueRange)
                    .execute();
        } catch (IOException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_APPENDING_VALUE_ON_SPREADSHEET, googleSheetsProperties.getSheetId());
        }
    }

    private Sheets.Spreadsheets.Values retrieveSpreadsheetValues() {
        return sheets.spreadsheets()
                .values();
    }
}
