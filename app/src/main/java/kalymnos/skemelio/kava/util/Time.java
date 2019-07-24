package kalymnos.skemelio.kava.util;

import java.text.SimpleDateFormat;

public class Time {

    private Time() {
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(System.currentTimeMillis()));
    }
}
