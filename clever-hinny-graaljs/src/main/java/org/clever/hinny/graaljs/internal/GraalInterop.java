package org.clever.hinny.graaljs.internal;

import org.clever.hinny.api.internal.Interop;
import org.graalvm.polyglot.proxy.*;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GraalInterop extends Interop {
    public static final GraalInterop Instance = new GraalInterop();

    private GraalInterop() {
    }

    @Override
    public ProxyArray fromJList(List<Object> values) {
        return ProxyArray.fromList(values);
    }

    @Override
    public ProxyArray fromJArray(Object... values) {
        return ProxyArray.fromArray(values);
    }

    @Override
    public ProxyObject fromJMap(Map<String, Object> values) {
        return ProxyObject.fromMap(values);
    }

    @Override
    public ProxyDate fromJDate(LocalDate date) {
        return ProxyDate.from(date);
    }

    @Override
    public ProxyDate fromJDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return ProxyDate.from(localDate);
    }

    @Override
    public ProxyDate fromJDate(java.sql.Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return fromJDate(localDate);
    }

    @Override
    public ProxyDuration fromJDate(Duration duration) {
        return ProxyDuration.from(duration);
    }

    @Override
    public ProxyInstant fromJDate(Instant instant) {
        return ProxyInstant.from(instant);
    }

    @Override
    public ProxyTime fromJDate(LocalTime localTime) {
        return ProxyTime.from(localTime);
    }

    @Override
    public ProxyTimeZone fromJDate(ZoneId zoneId) {
        return ProxyTimeZone.from(zoneId);
    }

//    public ProxyInstantiable fromJDate() {
//    }

//    public ProxyExecutable fromJDate(Duration duration) {
//    }
}
