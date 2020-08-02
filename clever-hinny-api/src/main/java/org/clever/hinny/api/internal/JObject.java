package org.clever.hinny.api.internal;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/31 10:43 <br/>
 *
 * @param <T> script引擎对象类型
 */
public abstract class JObject<T> {
    private static final String[] Date_Patterns = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "EEE MMM dd HH:mm:ss zzz yyyy",
            "yyyy-MM-dd", "HH:mm:ss",
    };

    public byte asJByte(byte b) {
        return b;
    }

    public byte asJByte(String b) {
        return Byte.parseByte(b);
    }

    public short asJShort(short s) {
        return s;
    }

    public short asJShort(String s) {
        return Short.parseShort(s);
    }

    public int asJInt(int i) {
        return i;
    }

    public int asJInt(String i) {
        return Integer.parseInt(i);
    }

    public long asJLong(long l) {
        return l;
    }

    public long asJLong(String l) {
        return Long.parseLong(l);
    }

    public float asJLong(float f) {
        return Float.valueOf(f).longValue();
    }

    public double asJDouble(double d) {
        return d;
    }

    public double asJDouble(String d) {
        return Double.parseDouble(d);
    }

    public boolean asJBoolean(boolean b) {
        return b;
    }

    public boolean asJBoolean(String b) {
        return Boolean.parseBoolean(b);
    }

    public char asJChar(char c) {
        return c;
    }

    public String asJString(String str) {
        return str;
    }

    /**
     * 创建Java java.util.Date 对象,支持以下格式<br />
     * "yyyy-MM-dd HH:mm:ss" <br />
     * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" <br />
     * "EEE MMM dd HH:mm:ss zzz yyyy" <br />
     * "yyyy-MM-dd" <br />
     * "HH:mm:ss" <br />
     */
    @SneakyThrows
    public Date asJDate(String arg) {
        if (StringUtils.isBlank(arg)) {
            return null;
        }
        return DateUtils.parseDate(arg, Date_Patterns);
    }

    public Date asJDate(long arg) {
        return new Date(arg);
    }

    public abstract Date asJDate(T arg);

    public Date toJDate(Timestamp arg) {
        if (arg == null) {
            return null;
        }
        return new Date(arg.getTime());
    }

    public Date toJDate(Time arg) {
        if (arg == null) {
            return null;
        }
        return new Date(arg.getTime());
    }

    public BigDecimal asJBigDecimal(String arg) {
        return new BigDecimal(arg);
    }

    public List<Object> asJList(Object... args) {
        if (args == null) {
            return null;
        }
        return Arrays.asList(args);
    }

    public List<Object> asJList(Object arg) {
        if (arg == null) {
            return null;
        }
        if (arg.getClass().isArray()) {
            Object[] array = (Object[]) arg;
            return Arrays.asList(array);
        } else {
            return Collections.singletonList(arg);
        }
    }

    public Set<Object> asJSet(Object... args) {
        if (args == null) {
            return null;
        }
        Set<Object> set = new HashSet<>(args.length);
        set.addAll(Arrays.asList(args));
        return set;
    }

    public Set<Object> asJSet(Object arg) {
        if (arg == null) {
            return null;
        }
        if (arg.getClass().isArray()) {
            Object[] array = (Object[]) arg;
            Set<Object> set = new HashSet<>(array.length);
            set.addAll(Arrays.asList(array));
            return set;
        } else {
            return Collections.singleton(arg);
        }
    }

    public abstract Map<Object, Object> asJMap(T arg);

    // TODO 补充常用类型

    public Date toJDate(java.sql.Date arg) {
        if (arg == null) {
            return null;
        }
        return new Date(arg.getTime());
    }

    public Integer toJInt(BigInteger arg) {
        if (arg == null) {
            return null;
        }
        return arg.intValue();
    }
}