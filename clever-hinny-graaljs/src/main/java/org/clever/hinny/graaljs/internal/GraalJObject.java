package org.clever.hinny.graaljs.internal;

import org.clever.hinny.api.internal.JObject;
import org.graalvm.polyglot.Value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/31 21:37 <br/>
 */
public class GraalJObject extends JObject<Value> {
    public static final GraalJObject Instance = new GraalJObject();

    private GraalJObject() {
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
}
