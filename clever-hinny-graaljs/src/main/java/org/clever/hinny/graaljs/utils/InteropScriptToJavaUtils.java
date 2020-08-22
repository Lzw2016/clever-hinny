package org.clever.hinny.graaljs.utils;

import com.oracle.truffle.api.interop.TruffleObject;
import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.graaljs.internal.GraalInterop;
import org.graalvm.polyglot.Value;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/03 10:42 <br/>
 */
@Slf4j
public class InteropScriptToJavaUtils {
    public static final InteropScriptToJavaUtils Instance = new InteropScriptToJavaUtils();

    private InteropScriptToJavaUtils() {
    }

    /**
     * 把JavaScript对象转换成Java对象 <br />
     * 1. 只考虑简单对象和“数组”、“Object对象” <br />
     * 2. 只做浅转换(一层转换) <br />
     *
     * @param object JavaScript对象
     */
    public Object toJavaObject(Object object) {
        return deepToJavaObject(object, 1);
    }

//    public Object[] toJavaObject(Object[] array) {
//        Collection<?> collection = toJavaObject(Arrays.asList(array));
//        // TODO
//        return null;
//    }

    /**
     * 处理 Map
     */
    public Map<String, Object> convertMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            object = toJavaObject(object);
            map.put(key, object);
        }
        return map;
    }

    /**
     * 处理 Collection Map
     */
    public List<Map<String, Object>> convertMapList(List<Map<String, Object>> mapList) {
        if (mapList == null) {
            return null;
        }
        List<Map<String, Object>> res = new ArrayList<>(mapList.size());
        for (Map<String, Object> item : mapList) {
            res.add(convertMap(item));
        }
        return res;
    }

    /**
     * 处理 Collection
     */
    @SuppressWarnings("unchecked")
    public List<Object> convertList(List<Object> list) {
        if (list == null) {
            return null;
        }
        List<Object> res = new ArrayList<>(list.size());
        for (Object item : list) {
            if (item instanceof Map) {
                res.add(convertMap((Map<String, Object>) item));
            } else {
                res.add(toJavaObject(item));
            }
        }
        return res;
    }

    /**
     * 把JavaScript对象转换成Java对象(深度转换) <br />
     *
     * @param object JavaScript对象
     * @param deep   转换深度值(应该大于等于1)
     */
    public Object deepToJavaObject(Object object, int deep) {
        if (deep <= 0) {
            return object;
        }
        if (object == null) {
            return null;
        }
        Object res = toJavaObjectForBase(object);
        if (res != object) {
            return object;
        }
        Value value = toValue(object);
        if (value == null) {
            return object;
        }
        // 数组, 对象
        if (value.hasArrayElements()) {
            long size = value.getArraySize();
            if (size > Integer.MAX_VALUE) {
                throw new ClassCastException("数组 arg.length=" + size + " 太长无法转换");
            }
            Object[] array = new Object[(int) size];
            for (int i = 0; i < size; i++) {
                Object tmp = toJavaObjectForBase(value.getArrayElement(i));
                array[i] = deepToJavaObject(tmp, deep - 1);
            }
            return array;
        } else {
            Set<String> keys = value.getMemberKeys();
            Map<String, Object> map = new HashMap<>(keys.size());
            for (String key : keys) {
                Object tmp = toJavaObjectForBase(value.getMember(key));
                map.put(key, deepToJavaObject(tmp, deep - 1));
            }
            return map;
        }
    }

    /**
     * 把JavaScript对象转换成Java对象(深度转换，深度值为16) <br />
     *
     * @param object JavaScript对象
     */
    public Object deepToJavaObject(Object object) {
        return deepToJavaObject(object, 16);
    }

    /**
     * 把JavaScript对象转换成Java对象 <br />
     * 1. 只考虑简单对象，不考虑“数组”、“Object对象” <br />
     * 2. 只做浅转换(一层转换) <br />
     *
     * @param object JavaScript对象
     */
    protected Object toJavaObjectForBase(Object object) {
        if (object == null) {
            return null;
        }
        final String className = object.getClass().getName();
        if (!className.startsWith("com.oracle.truffle.") && !className.startsWith("org.graalvm.")) {
            return object;
        }
        Value value = toValue(object);
        if (value == null) {
            return object;
        }
        if (value.isNull()) {
            return null;
        } else if (value.isHostObject()) {                                      // Java对象
            return value.asHostObject();
        } else if (value.isException()) {                                       // 异常
            return value.throwException();
        } else if (value.isBoolean()) {                                         // boolean
            return value.asBoolean();
        } else if (value.isNumber()) {
            if (value.fitsInByte()) {                                           // byte
                return value.asByte();
            }
            if (value.fitsInShort()) {                                          // short
                return value.asShort();
            }
            if (value.fitsInInt()) {                                            // int
                return value.asInt();
            }
            if (value.fitsInLong()) {                                           // long
                return value.asLong();
            }
            if (value.fitsInFloat()) {                                          // float
                return value.asFloat();
            }
            if (value.fitsInDouble()) {                                         // double
                return value.asDouble();
            }
            return value.asDouble();
        } else if (value.isString()) {                                          // String
            return value.asString();
        } else if (value.isDate() || value.isTime() || value.isInstant()) {     // Date 日期+时间
            return GraalInterop.Instance.asJDate(value);
        } else if (value.isDuration()) {                                        // Duration 时间段
            return value.asDuration();
        } else if (value.isTimeZone()) {                                        // Duration 时间段
            return value.asTimeZone();
        } else if (value.isProxyObject()) {                                     // Proxy 对象
            return value.asProxyObject();
        } else if (value.isMetaObject()) {                                      // Class 类型
            return value;
        } else if (value.isNativePointer()) {                                   // 本机指针
            return value;
        }
        return object;
    }

    /**
     * 得到原始的JavaScript对象
     */
    protected Value toValue(Object object) {
        if (object == null) {
            return null;
        }
        Value value = null;
        if (object instanceof Value) {
            value = (Value) object;
        }
        final String className = object.getClass().getName();
        if (value == null && (object instanceof TruffleObject || className.startsWith("com.oracle.truffle.") || className.startsWith("org.graalvm."))) {
            value = Value.asValue(object);
        }
        return value;
    }
}
