package org.openlmis.resttest.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public final class JsonUtil {

    public static String readJsonFileAsString(String filename) throws IOException {
        return readJsonFileAsString(filename, new HashMap<String, String>());
    }

    public static String readJsonFileAsString(String filename, Map<String, String> params) throws IOException {
        try (InputStream in = JsonUtil.class.getClassLoader().getResourceAsStream(filename)) {
            String value = IOUtils.toString(in, String.valueOf(Charset.forName("UTF-8")));
            StrSubstitutor sub = new StrSubstitutor(params);
            return sub.replace(value);
        }
    }

    private JsonUtil() {
    }
}
