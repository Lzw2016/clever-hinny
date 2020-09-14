package org.clever.hinny.graaljs.internal.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.clever.hinny.graaljs.utils.InteropScriptToJavaUtils;
import org.graalvm.polyglot.Value;

import java.io.IOException;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/29 08:05 <br/>
 */
public class ValueSerializer extends JsonSerializer<Value> {
    public final static ValueSerializer instance = new ValueSerializer();

    @Override
    public void serialize(Value value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        Object obj = InteropScriptToJavaUtils.Instance.toJavaObject(value);
        if (obj != null && (obj.getClass().getName().startsWith("com.oracle.truffle.") || obj.getClass().getName().startsWith("org.graalvm."))) {
            gen.writeString(String.valueOf(obj));
        } else {
            gen.writeObject(obj);
        }
    }
}
