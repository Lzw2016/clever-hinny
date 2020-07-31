package org.clever.hinny.graaljs.internal;

import org.clever.hinny.api.internal.JObject;
import org.graalvm.polyglot.Value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
}
