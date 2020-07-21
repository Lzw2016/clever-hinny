package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptObject;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalScriptObject extends AbstractScriptObject<Value> {

    public GraalScriptObject(Value original) {
        super(original);
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
        // TODO original.getContext().enter();
        Object res = original.invokeMember(functionName, args);
        // TODO original.getContext().leave();
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
        // TODO ??
        return original.hasArrayElements() ? (int) original.getArraySize() : original.getMemberKeys().size();
    }
}
