package org.clever.hinny.graaljs.internal;

import org.clever.hinny.api.internal.Interop;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.*;

import java.time.*;
import java.util.*;

public class GraalInterop extends Interop<Value> {
    public static final GraalInterop Instance = new GraalInterop();

    private GraalInterop() {
    }

    @Override
    public Date asJDate(Value arg) {
        if (arg == null) {
            return null;
        }
        final boolean isDate = arg.isDate();
        final boolean isTime = arg.isTime();
        final boolean isInstant = arg.isInstant();
        if (isInstant) {
            return Date.from(arg.asInstant());
        }
        if (isDate && isTime) {
            LocalDateTime localDateTime = LocalDateTime.of(arg.asDate(), arg.asTime());
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        throw new ClassCastException("参数 arg=:" + arg.toString() + "不能转换成Date类型");
    }

    @Override
    public Map<Object, Object> asJMap(Value arg) {
        if (arg == null) {
            return null;
        }
        Map<Object, Object> map;
        if (arg.hasArrayElements()) {
            long size = arg.getArraySize();
            if (size > Integer.MAX_VALUE) {
                throw new ClassCastException("数组 arg.length=" + size + " 太长无法转换成Map");
            }
            map = new HashMap<>((int) size);
            for (int i = 0; i < size; i++) {
                map.put(i, arg.getArrayElement(i));
            }
        } else {
            Set<String> keys = arg.getMemberKeys();
            map = new HashMap<>(keys.size());
            for (String key : keys) {
                map.put(key, arg.getMember(key));
            }
        }
        return map;
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
