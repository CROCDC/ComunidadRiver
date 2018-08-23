package com.river.comunidad.comunidadriver.Utils;

public class MiRelojDeArena {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "ahora";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "hace un minuto";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return "hace " + diff / MINUTE_MILLIS + " minutos";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "hace una hora";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "hace " + diff / HOUR_MILLIS + " horas";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "ayer";
        } else {
            return "hace " + diff / DAY_MILLIS + " dias";
        }
    }
}
