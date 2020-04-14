package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(PropertiesPath.GOOGLE_SHEETS)
@Validated
public class GoogleSheetsProperties {

    @NotBlank
    private String sheetId;

    @NotBlank
    private String dataRange;

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getDataRange() {
        return dataRange;
    }

    public void setDataRange(String dataRange) {
        this.dataRange = dataRange;
    }
}
