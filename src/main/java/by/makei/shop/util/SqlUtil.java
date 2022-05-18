package by.makei.shop.util;

public class SqlUtil {
    private static final String COMMON_REGEX = "[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]";

    public static String mysql_escape_string(String str)
            {
        if (str == null) {
            return null;
        }

        if (str.replaceAll(COMMON_REGEX, "").length() < 1) {
            return str;
        }

        String clean_string = str;
        clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");
        clean_string = clean_string.replaceAll("\\n", "\\\\n");
        clean_string = clean_string.replaceAll("\\r", "\\\\r");
        clean_string = clean_string.replaceAll("\\t", "\\\\t");
        clean_string = clean_string.replaceAll("\\00", "\\\\0");
        clean_string = clean_string.replaceAll("'", "\\\\'");
        clean_string = clean_string.replaceAll("\\\"", "\\\\\"");
        return clean_string;
    }
}
