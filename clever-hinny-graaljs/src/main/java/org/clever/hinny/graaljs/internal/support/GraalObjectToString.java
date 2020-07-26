package org.clever.hinny.graaljs.internal.support;

import org.clever.hinny.api.internal.support.ObjectToString;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 15:21 <br/>
 */
public class GraalObjectToString extends ObjectToString {
    public static final GraalObjectToString Instance = new GraalObjectToString();

    protected GraalObjectToString() {
    }

    @Override
    public String toString(Object obj) {
        if (obj != null && "com.oracle.truffle.polyglot.PolyglotMap".equals(obj.getClass().getName())) {
            return obj.toString();
        } else if (obj instanceof Value) {
            //return JSTools.inspect((Value) obj);
            return obj.toString();
        }
        return super.toString(obj);
    }
}
