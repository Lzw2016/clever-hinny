package org.clever.hinny.api.internal;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/08/29 14:01 <br/>
 */
@Slf4j
public class TestInternal {
    public static final TestInternal Instance = new TestInternal();

    private TestInternal() {
    }

    // ------------------------------------------------------------------------------------------------------------------ test

    public String getClass(Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName();
    }

    public void test(Object param) {
        // jdk.nashorn.internal.objects.NativeDate
        log.info("----------> {} | {}", param == null ? "null" : param.getClass(), param);
    }

    // ------------------------------------------------------------------------------------------------------------------ java类型

    public byte getByte() {
        return 1;
    }

    public short getShort() {
        return 2;
    }

    public int getInt() {
        return 123;
    }

    public long getLong() {
        return 456;
    }

    public float getFloat() {
        return 123.456F;
    }

    public double getDouble() {
        return 123.456D;
    }

    public boolean getBoolean() {
        return true;
    }

    public char getChar() {
        return 'A';
    }

    public String getString() {
        return "aaa";
    }

    public Date getDate() {
        return new Date();
    }

    public String[] getArray() {
        return new String[]{"aaa", "bbb", "ccc"};
    }

    public List<String> getList() {
        return new ArrayList<String>() {{
            add("aaa");
            add("bbb");
            add("ccc");
        }};
    }

    public Set<String> getSet() {
        return new HashSet<String>() {{
            add("aaa");
            add("bbb");
            add("ccc");
        }};
    }

    public Map<String, Object> getMap() {
        return new HashMap<String, Object>() {{
            put("int", 1);
            put("float", 1.1F);
            put("double", 1.3D);
            put("long", 123L);
            put("char", 'A');
            put("string", "aaa");
            put("boolean", false);
        }};
    }
}
