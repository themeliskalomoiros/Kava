package kalymnos.skemelio.kava.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class JsonDataRetriever {
    private JsonDataRetriever() {
    }

    public static String rawJson(InputStream jsonStream) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(jsonStream))) {
            return getTextOf(in);
        } catch (IOException e) {
            return null;
        }
    }

    private static String getTextOf(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null)
            sb.append(line);
        return sb.toString();
    }
}
