package org.clever.hinny.j2v8;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8Value;
import org.clever.hinny.api.AbstractScriptObject;
import org.clever.hinny.api.ScriptEngineContext;

import java.util.Collection;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:40 <br/>
 */
public class J2V8ScriptObject extends AbstractScriptObject<V8, V8Value> {

    public J2V8ScriptObject(ScriptEngineContext<V8, V8Value> context, V8Value original) {
        super(context, original);
    }

    @Override
    public Collection<String> getMemberNames() {
        return null;
    }

    @Override
    public Object getMember(String name) {
        return null;
    }

    @Override
    public boolean hasMember(String name) {
        return false;
    }

    @Override
    public Collection<Object> getMembers() {
        return null;
    }

    @Override
    public Object callMember(String functionName, Object... args) {
        return null;
    }

    @Override
    public void delMember(String name) {
        V8Object v8Object = null;
    }

    @Override
    public void setMember(String name, Object value) {

    }

    @Override
    public int size() {
        return 0;
    }
}
