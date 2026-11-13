package com.hotel.hotel_api.configuracion;

import java.time.LocalTime;

public final class HorariosHotel {

    /** Hora oficial de Check-In: 04:00 PM */
    public static final LocalTime HORA_CHECK_IN = LocalTime.of(16, 0);

    /** Hora oficial de Check-Out: 01:00 PM */
    public static final LocalTime HORA_CHECK_OUT = LocalTime.of(13, 0);

    private HorariosHotel() {
        // Clase de constantes: no se debe instanciar
    }
}