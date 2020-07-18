package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.ScriptEngineContext;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:53 <br/>
 */
public class NashornScriptEngineContext implements ScriptEngineContext<ScriptObjectMirror> {

    @Override
    public Map<String, Object> getContextMap() {
        return null;
    }
}
