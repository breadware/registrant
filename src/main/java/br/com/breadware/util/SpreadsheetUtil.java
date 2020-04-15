package br.com.breadware.util;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
public class SpreadsheetUtil {

    private static final long MIN_RANGE_VALUE = 0L;

    private static final long MAX_RANGE_VALUE = 18278L;

    private static final int ENGLISH_ALPHABET_SIZE = 26;

    private static final char FIRST_LETTER = 'A';

    private static final String RANGE_FORMAT = "%s!%s:%s";

    public String convertToRange(String sheetName, int startIndex, int endIndex) {

        if (startIndex < MIN_RANGE_VALUE || endIndex >= MAX_RANGE_VALUE) {
            throw new RegistrantRuntimeException(ErrorMessage.INVALID_SHEETS_RANGE, startIndex, endIndex);
        }

         String alphabeticStartIndex = numericToAlphabeticIndex(startIndex);
        String alphabeticEndIndex = numericToAlphabeticIndex(endIndex);

        return String.format(RANGE_FORMAT, sheetName, alphabeticStartIndex, alphabeticEndIndex);

    }

    private String numericToAlphabeticIndex(int index) {
        StringBuilder stringBuilder = new StringBuilder();
        int modulo = index%ENGLISH_ALPHABET_SIZE;
        int quotient = (index-modulo)/ENGLISH_ALPHABET_SIZE;

        if (quotient > 0) {
            stringBuilder.append(numericToAlphabeticIndex(quotient-1));
        }

        char character = (char)((int)FIRST_LETTER + modulo);
        stringBuilder.append(character);

        return stringBuilder.toString();
    }
}
