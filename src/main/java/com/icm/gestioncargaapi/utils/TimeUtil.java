package com.icm.gestioncargaapi.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    // Zona horaria de Lima, Perú
    private static ZoneId LIMA_ZONE_ID = ZoneId.of("America/Lima");
    // Formato de fecha y hora deseado
    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss, dd/MM/yyyy");

    // Método para obtener la hora y fecha en Lima, Perú
    public static String getCurrentTime() {
        ZonedDateTime limaDateTime = ZonedDateTime.now(LIMA_ZONE_ID);
        return limaDateTime.format(DATE_TIME_FORMATTER);
    }


}
