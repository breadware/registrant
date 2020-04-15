package br.com.breadware.properties.google;

import br.com.breadware.properties.PropertiesPath;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(PropertiesPath.GOOGLE_SHEETS)
@Validated
public class GoogleSheetsProperties {

    @NotBlank
    private String spreadsheetId;

    @NotBlank
    private String sheetName;

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
