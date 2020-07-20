package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptObject;
import org.graalvm.polyglot.Value;

import java.util.Collection;

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

    }

    @Override
    public void setMember(String name, Object value) {

    }

    @Override
    public int size() {
        return 0;
    }
}
