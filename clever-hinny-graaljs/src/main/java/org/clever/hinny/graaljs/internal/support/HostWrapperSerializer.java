package org.clever.hinny.graaljs.internal.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/26 18:37 <br/>
 */
public class HostWrapperSerializer extends JsonSerializer<Object> {
    public static final String FunctionProxyHandler_Class = "com.oracle.truffle.polyglot.FunctionProxyHandler";
    public static final String ObjectProxyHandler_Class = "com.oracle.truffle.polyglot.ObjectProxyHandler";
    public static final String PolyglotFunction_Class = "com.oracle.truffle.polyglot.PolyglotFunction";
    public static final String PolyglotList_Class = "com.oracle.truffle.polyglot.PolyglotList";
    public static final String PolyglotListAndFunction_Class = "com.oracle.truffle.polyglot.PolyglotListAndFunction";
    public static final String PolyglotMap_Class = "com.oracle.truffle.polyglot.PolyglotMap";
    public static final String PolyglotMapAndFunction_Class = "com.oracle.truffle.polyglot.PolyglotMapAndFunction";
    public static final String ProxyObject_Class = "org.graalvm.polyglot.proxy.ProxyObject";

    @SuppressWarnings("Java9CollectionFactory")
    public static final Set<String> Support_Class = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(
                    FunctionProxyHandler_Class,
                    ObjectProxyHandler_Class,
                    PolyglotFunction_Class,
                    PolyglotList_Class,
                    PolyglotListAndFunction_Class,
                    PolyglotMap_Class,
                    PolyglotMapAndFunction_Class
            )
    ));

    private static Boolean gotProxyObjectValues;

    public final static HostWrapperSerializer instance = new HostWrapperSerializer();

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        String className = value.getClass().getName();
        if (Objects.equals(FunctionProxyHandler_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(ObjectProxyHandler_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotFunction_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotList_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotListAndFunction_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotMap_Class, className) && value instanceof Map) {
            Value val = Value.asValue(value);
            if (val.hasArrayElements() || val.canExecute()) {
                gen.writeObject(val);
            } else {
                gen.writeObject(new HashMap<>((Map) value));
            }
        } else if (Objects.equals(PolyglotMapAndFunction_Class, className) && value instanceof Map) {
            gen.writeObject(new HashMap<>((Map) value));
        } else if (value instanceof ProxyObject) {
            if (gotProxyObjectValues == null && Objects.equals("org.graalvm.polyglot.proxy.ProxyObject$1", className)) {
                try {
                    Field field = value.getClass().getDeclaredField("val$values");
                    // noinspection ConstantConditions
                    gotProxyObjectValues = (field != null);
                } catch (Exception ignored) {
                    gotProxyObjectValues = false;
                }
            }
            if (gotProxyObjectValues != null && gotProxyObjectValues) {
                try {
                    gen.writeObject(reflectGetValue(value, "val$values"));
                    return;
                } catch (Exception ignored) {
                    gotProxyObjectValues = false;
                }
            }
            ProxyObject proxyObject = (ProxyObject) value;
            Object[] keys;
            try {
                keys = (Object[]) reflectGetValue(proxyObject.getMemberKeys(), "keys");
            } catch (Exception ignored) {
                keys = Value.asValue(value).getMemberKeys().toArray(new Object[0]);
            }
            Map<String, Object> map = new HashMap<>(keys.length);
            for (Object key : keys) {
                if (key == null) {
                    continue;
                }
                String name = String.valueOf(key);
                map.put(name, proxyObject.getMember(name));
            }
            gen.writeObject(map);
        } else {
            gen.writeString(String.valueOf(value));
        }
    }

    @SneakyThrows
    private static Object reflectGetValue(Object instance, String fieldName) {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static boolean isSupport(final String className) {
        return Support_Class.contains(className) || className.startsWith(ProxyObject_Class);
    }
}
