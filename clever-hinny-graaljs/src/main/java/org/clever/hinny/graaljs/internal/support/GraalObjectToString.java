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
        if (null == obj) {
            return null;
        }
        if (obj instanceof Value) {
            //return JSTools.inspect((Value) obj);
            return obj.toString();
        }
        String className = obj.getClass().getName();
        if (className.startsWith("com.oracle.truffle.") || className.startsWith("org.graalvm.")) {
            return obj.toString();
            // Context context = Context.getCurrent();
            // Value function = context.eval(GraalConstant.Js_Language_Id, "(function(obj) { return JSON.stringify(obj); });");
            // return String.valueOf(function.execute(obj));
        }
        return super.toString(obj);
    }
}
