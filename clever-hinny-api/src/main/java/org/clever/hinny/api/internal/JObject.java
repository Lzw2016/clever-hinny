package org.clever.hinny.api.internal;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/31 10:43 <br/>
 *
 * @param <T> script引擎对象类型
 */
public abstract class JObject<T> {
    public byte jByte(byte b) {
        return b;
    }

    public short jShort(short s) {
        return s;
    }

    public int jInt(int i) {
        return i;
    }

    public long jLong(long l) {
        return l;
    }

    public float jLong(float f) {
        return f;
    }

    public double jDouble(double d) {
        return d;
    }

    public boolean jBoolean(boolean b) {
        return b;
    }

    public char jChar(char c) {
        return c;
    }

    public String jStr(String str) {
        return str;
    }

    public Date jDate(String arg) {
        // TODO Date
        return null;
    }

    public abstract Date jDate(T arg);

    public BigDecimal jBigDecimal(String arg) {
        // TODO BigDecimal
        return null;
    }

    public BigDecimal jBigDecimal(double arg) {
        // TODO BigDecimal
        return null;
    }

    // TODO ...更多
}
