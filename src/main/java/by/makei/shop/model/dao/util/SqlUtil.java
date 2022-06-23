package by.makei.shop.model.dao.util;

public class SqlUtil {
    private static final String COMMON_REGEX = "[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]";

    public static String makeSafeMysqlString(String str)
            {
        if (str == null) {
            return null;
        }

        if (str.replaceAll(COMMON_REGEX, "").length() < 1) {
            return str;
        }

        String cleanString = str;
        cleanString = cleanString.replaceAll("\\\\", "\\\\\\\\");
        cleanString = cleanString.replaceAll("\\n", "\\\\n");
        cleanString = cleanString.replaceAll("\\r", "\\\\r");
        cleanString = cleanString.replaceAll("\\t", "\\\\t");
        cleanString = cleanString.replaceAll("\\00", "\\\\0");
        cleanString = cleanString.replaceAll("'", "\\\\'");
        cleanString = cleanString.replaceAll("\\\"", "\\\\\"");
        return cleanString;
    }
}
