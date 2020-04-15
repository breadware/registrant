package br.com.breadware.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpreadsheetUtilTest {

    private SpreadsheetUtil spreadsheetUtil;

    @BeforeEach
    public void beforeEach() {
        spreadsheetUtil = new SpreadsheetUtil();
    }

    @Test
    public void testConvertToRange() {
        String sheetName = "data";
        int startIndex = 0;
        int endIndex = 18277;
        String expected = sheetName + "!A:ZZZ";

        String actual = spreadsheetUtil.convertToRange(sheetName, startIndex, endIndex);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}