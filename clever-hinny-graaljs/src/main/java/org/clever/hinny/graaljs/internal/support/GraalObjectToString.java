package org.clever.hinny.graaljs.internal.support;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracle.truffle.api.interop.TruffleObject;
import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.internal.support.ObjectToString;
import org.clever.hinny.api.utils.JacksonMapper;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 15:21 <br/>
 */
@Slf4j
public class GraalObjectToString extends ObjectToString {
    public static final GraalObjectToString Instance = new GraalObjectToString();

    protected GraalObjectToString() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Value.class, ToStringSerializer.instance);
        module.addSerializer(TruffleObject.class, ToStringSerializer.instance);
        try {
            Class<?> clazz = Class.forName("com.oracle.truffle.polyglot.HostWrapper");
            module.addSerializer(clazz, HostWrapperSerializer.instance);
        } catch (ClassNotFoundException e) {
            log.warn("类型com.oracle.truffle.polyglot.HostWrapper加载失败", e);
        }
        JacksonMapper.getInstance().getMapper().registerModules(module);
    }

    @Override
    public String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof Value) {
            return obj.toString();
        }
        String className = obj.getClass().getName();
        if (!HostWrapperSerializer.isSupport(className) && (obj instanceof TruffleObject || className.startsWith("com.oracle.truffle.") || className.startsWith("org.graalvm."))) {
            return obj.toString();
        }
        return super.toString(obj);
    }

//    public static class GraalToStringSerializer extends JsonSerializer<Object> {
//        public static final GraalToStringSerializer instance = new GraalToStringSerializer();
//
//        private GraalToStringSerializer() {
//        }
//
//        @Override
//        public void serialize(Object object, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            String json = null;
//            if (object != null) {
//                json = object.toString();
//            }
//            if (StringUtils.isNotBlank(json)) {
//                gen.writeRawValue(json);
//                // gen.writeObject(new JSONObject(json).toMap());
//            }
//        }
//    }
}
