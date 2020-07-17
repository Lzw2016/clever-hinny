package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.ScriptObject;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:26 <br/>
 */
public class NashornScriptObject implements ScriptObject<ScriptObjectMirror> {
    /**
     * Script引擎对应的对象值
     */
    public ScriptObjectMirror scriptObjectMirror;

    @Override
    public ScriptObjectMirror getValue() {
        return scriptObjectMirror;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void putAll(Map<String, Object> map) {

    }

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public <T> T remove(String key) {
        return null;
    }
}
