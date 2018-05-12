package UtilsPackage;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class EscapeUtils {

    private static final HashMap m = new HashMap();
    static {
        m.put(34, "&quot;"); // < - less-than
        m.put(60, "&lt;");   // < - less-than
        m.put(62, "&gt;");   // > - greater-than
    }

    public static void escape(Writer writer, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int ascii = (int) c;
            String entityName = (String) m.get(ascii);
            if (entityName == null) {
                if (c > 0x7F) {
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                } else {
                    writer.write(c);
                }
            } else {
                writer.write(entityName);
            }
        }
    }
}
