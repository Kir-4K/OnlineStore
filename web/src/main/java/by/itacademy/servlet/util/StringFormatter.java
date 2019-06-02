package by.itacademy.servlet.util;

import lombok.experimental.UtilityClass;

import static java.lang.Double.parseDouble;
import static java.lang.Long.*;

@UtilityClass
public class StringFormatter {

    private static final String DEFAULT_OFFSET = "0";
    private static final String DEFAULT_LIMIT = "10";
    private static final String DEFAULT_MIN_PRICE = "0";
    private static final String DEFAULT_MAX_PRICE = "9999";
    private static final String NULL_VALUE = "";
    private static final String POINT = ".";
    private static final String REGEX_FOR_DOUBLE = "[\\D]";

    public static Double minToDouble(String min) {
        return parseDouble(min.replaceAll(REGEX_FOR_DOUBLE, POINT).equals(NULL_VALUE) ? DEFAULT_MIN_PRICE : min);
    }

    public static Double maxToDouble(String max) {
        return parseDouble(max.replaceAll(REGEX_FOR_DOUBLE, POINT).equals(NULL_VALUE) ? DEFAULT_MAX_PRICE : max);
    }

    public static Long limitToLong(String limit) {
        return parseLong(limit.equals(NULL_VALUE) ? DEFAULT_LIMIT : limit);
    }

    public static Long offsetToLong(String offset) {
        return parseLong(offset.equals(NULL_VALUE) ? DEFAULT_OFFSET : offset);
    }
}
