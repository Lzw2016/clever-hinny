package org.clever.hinny.graaljs.internal.support;

import com.oracle.truffle.api.interop.TruffleObject;
import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.internal.support.ObjectToString;
import org.clever.hinny.graaljs.jackson.HostWrapperSerializer;
import org.clever.hinny.graaljs.jackson.JacksonMapperSupport;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 15:21 <br/>
 */
@Slf4j
public class GraalObjectToString extends ObjectToString {
    public static final GraalObjectToString Instance = new GraalObjectToString();

    protected GraalObjectToString() {
        JacksonMapperSupport.initGraalModule();
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
}
