package org.clever.hinny.api.internal;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Interop {
    public static final Interop Instance = new Interop();

    protected Interop() {
    }

    public Object fromJList(List<Object> values) {
        return values;
    }

    public Object fromJArray(Object... values) {
        return values;
    }

    public Object fromJMap(Map<String, Object> values) {
        return values;
    }

    public Object fromJDate(LocalDate date) {
        return date;
    }

    public Object fromJDate(Date date) {
        return date;
    }

    public Object fromJDate(java.sql.Date date) {
        return date;
    }

    public Object fromJDate(Duration duration) {
        return duration;
    }

    public Object fromJDate(Instant instant) {
        return instant;
    }

    public Object fromJDate(LocalTime localTime) {
        return localTime;
    }

    public Object fromJDate(ZoneId zoneId) {
        return zoneId;
    }
}
