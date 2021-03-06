package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptObject;
import org.clever.hinny.api.ScriptEngineContext;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalScriptObject extends AbstractScriptObject<Context, Value> {

    public GraalScriptObject(ScriptEngineContext<Context, Value> context, Value original) {
        super(context, original);
    }

    @Override
    public Collection<String> getMemberNames() {
        return original.getMemberKeys();
    }

    @Override
    public Object getMember(String name) {
        return original.getMember(name);
    }

    @Override
    public boolean hasMember(String name) {
        return original.hasMember(name);
    }

    @Override
    public Collection<Object> getMembers() {
        List<Object> list = new ArrayList<>(size());
        if (original.hasArrayElements()) {
            for (int i = 0; i < original.getArraySize(); i++) {
                list.add(original.getArrayElement(i));
            }
        } else {
            for (String memberKey : original.getMemberKeys()) {
                list.add(original.getMember(memberKey));
            }
        }
        return list;
    }

    @Override
    public Object callMember(String functionName, Object... args) {
        Context engine = context.getEngine();
        Object res;
        try {
            engine.enter();
            res = original.invokeMember(functionName, args);
        } finally {
            if (engine != null) {
                engine.leave();
            }
        }
        return res;
    }

    @Override
    public void delMember(String name) {
        original.removeMember(name);
    }

    @Override
    public void setMember(String name, Object value) {
        original.putMember(name, value);
    }

    @Override
    public int size() {
        if (original.hasArrayElements()) {
            long size = original.getArraySize();
            if (size > Integer.MAX_VALUE) {
                throw new ClassCastException("数组 length=" + size + " 太长(超出范围)");
            }
            return (int) size;
        }
        return original.getMemberKeys().size();
    }
}
