package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.AbstractScriptObject;

import java.util.Arrays;
import java.util.Collection;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:26 <br/>
 */
public class NashornScriptObject extends AbstractScriptObject<ScriptObjectMirror> {

    public NashornScriptObject(ScriptObjectMirror original) {
        super(original);
    }

    @Override
    public Collection<String> getMemberNames() {
        return Arrays.asList(original.getOwnKeys(false));
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
        return original.values();
    }

    @Override
    public Object callMember(String functionName, Object... args) {
        return original.callMember(functionName, args);
    }

    @Override
    public void delMember(String name) {
        original.removeMember(name);
    }

    @Override
    public void setMember(String name, Object value) {
        original.setMember(name, value);
    }

    @Override
    public int size() {
        return original.size();
    }
}
