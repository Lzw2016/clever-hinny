package org.clever.hinny.graaljs.utils;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.graaljs.internal.GraalJObject;
import org.graalvm.polyglot.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * JavaScript 与 Java 相互交互工具
 * 作者：lizw <br/>
 * 创建时间：2020/07/30 21:36 <br/>
 */
@Slf4j
public class JavaInteropUtils {
    /**
     * Java 8中基本类型
     */
    private static final Set<String> Java_Base_Type = Set.of("long", "int", "short", "char", "byte", "boolean", "float", "double");

    public static final JavaInteropUtils Instance = new JavaInteropUtils();

    private JavaInteropUtils() {
    }

    // ------------------------------------------------------------------------------------------------------------------ toScriptObject

    /**
     * 把Java Map对象转换成JavaScript Object对象<br />
     * <strong>注意: key会被强制转换成String</strong> <br />
     *
     * @param map Java Map对象
     */
    public Value toScriptObject(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        Value value = ScriptEngineUtils.newObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            value.putMember(String.valueOf(entry.getKey()), entry.getValue());
        }
        return value;
    }

    /**
     * 把Java 集合对象转换成JavaScript Array对象<br />
     * <strong>注意: 集合元素如果是Java Map类型会做进一步转换(JavaScript Object对象)</strong> <br />
     *
     * @param list Java 集合对象
     */
    public Value toScriptArray(Collection<?> list) {
        if (list == null) {
            return null;
        }
        Object[] paramsArray = list.toArray();
        return toScriptArray(paramsArray);
    }

    /**
     * 把Java数组装换成JavaScript Array对象<br />
     * * <strong>注意: 集合元素如果是Java Map类型会做进一步转换(JavaScript Object对象)</strong> <br />
     *
     * @param array Java数组
     */
    public Value toScriptArray(Object[] array) {
        if (array == null) {
            return null;
        }
        Object[] resArray = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            Object item = array[i];
            if (item instanceof Map) {
                resArray[i] = toScriptObject((Map<?, ?>) item);
            } else {
                resArray[i] = item;
            }
        }
        return ScriptEngineUtils.newArray(resArray);
    }

//    public Collection<Value> collectionInMapToScript(Collection<?> collection) {
//        if (collection == null) {
//            return null;
//        }
//        Collection<Value> res;
//        if (collection instanceof Set) {
//            res = new HashSet<>(collection.size());
//        } else {
//            res = new ArrayList<>(collection.size());
//        }
//        Context context = null;
//        for (Object item : collection) {
//            if (item instanceof Map) {
//                res.add(toScriptObject((Map<?, ?>) item));
//            } else {
//                if (context == null) {
//                    context = Context.getCurrent();
//                }
//                res.add(context.asValue(item));
//            }
//        }
//        return res;
//    }

    // ------------------------------------------------------------------------------------------------------------------ toJavaObject

    /**
     * 把JavaScript对象转换成Java对象 <br />
     * 1. 只考虑简单对象和“数组”、“Object对象” <br />
     * 2. 只做浅转换(一层转换) <br />
     *
     * @param value JavaScript对象
     */
    public Object toJavaObject(Value value) {
        if (value == null) {
            return null;
        }
        Object object = toJavaObjectForBase(value);
        if (object != value) {
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
                array[i] = toJavaObjectForBase(value.getArrayElement(i));
            }
            return array;
        } else {
            Set<String> keys = value.getMemberKeys();
            Map<String, Object> map = new HashMap<>(keys.size());
            for (String key : keys) {
                map.put(key, toJavaObjectForBase(value.getMember(key)));
            }
            return map;
        }
    }

//    public Object[] toJavaObject(Object[] array) {
//        Collection<?> collection = toJavaObject(Arrays.asList(array));
//        // TODO
//        return null;
//    }
//
//    public Collection<?> toJavaObject(Collection<?> collection) {
//        // TODO
//        return null;
//    }
//
//    public Map<?, ?> toJavaObject(Map<?, ?> map) {
//        // TODO
//        return null;
//    }

    /**
     * 把JavaScript对象转换成Java对象 <br />
     * 1. 只考虑简单对象，不考虑“数组”、“Object对象” <br />
     * 2. 只做浅转换(一层转换) <br />
     *
     * @param value JavaScript对象
     */
    protected Object toJavaObjectForBase(Value value) {
        if (value == null || value.isNull()) {
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
            return GraalJObject.Instance.asJDate(value);
        } else if (value.isDuration()) {                                        // Duration 时间段
            return value.asDuration();
        } else if (value.isTimeZone()) {                                        // Duration 时间段
            return value.asTimeZone();
        } else if (value.isProxyObject()) {                                     // Proxy 对象
            return value.asProxyObject();
        } else if (value.isMetaObject()) {                                      // Class 类型
            return value.getMetaObject();
        } else if (value.isNativePointer()) {                                   // 本机指针
            return value;
        }
        return value;
    }
}
