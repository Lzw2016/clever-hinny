package org.clever.hinny.nashorn.utils;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.io.IOUtils;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.api.utils.ExceptionUtils;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/08/26 12:01 <br/>
 */
public class JSTools {
    public static final ScriptObjectMirror BaseUtils;

    static {
        NashornScriptEngine engine = ScriptEngineUtils.creatEngine();
        String jsCode = null;
        ClassLoader loader = JSTools.class.getClassLoader();
        try (InputStream stream = loader.getResourceAsStream("javascript/BaseUtils.js")) {
            if (stream != null) {
                jsCode = IOUtils.toString(stream, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        }
        Assert.isNotBlank(jsCode, "JSTools初始化失败，javascript/BaseUtils.js 文件读取失败");
        BaseUtils = ScriptEngineUtils.newObject();
        engine.getBindings(ScriptContext.ENGINE_SCOPE).put("exports", BaseUtils);
        engine.getBindings(ScriptContext.ENGINE_SCOPE).put("InternalUtils", InternalUtils.Instance);
        try {
            engine.eval(jsCode);
        } catch (ScriptException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 使用Json序列化 JS 对象(解决了循环依赖问题)
     *
     * @param object JS 对象
     */
    public static String inspect(JSObject object) {
        Object res = BaseUtils.callMember("inspect", object);
        return String.valueOf(res);
    }

    /**
     * 使用Json序列化 JS 对象(解决了循环依赖问题)
     *
     * @param object JS 对象
     */
    public static String inspect(Bindings object) {
        Object res = BaseUtils.callMember("inspect", object);
        return String.valueOf(res);
    }

//    /**
//     * 使用Json序列化 JS 对象(解决了循环依赖问题)
//     *
//     * @param object JS 对象
//     */
//    public static String inspect(PropertyAccess object) {
//        Object res = BaseUtils.callMember("inspect", object);
//        return String.valueOf(res);
//    }

    /**
     * 使用Json序列化 JS 对象(存在循环依赖问题)
     *
     * @param object JS 对象
     */
    public static String stringify(JSObject object) {
        Object res = BaseUtils.callMember("stringify", object);
        return String.valueOf(res);
    }

    /**
     * 使用Json序列化 JS 对象(存在循环依赖问题)
     *
     * @param object JS 对象
     */
    public static String stringify(Bindings object) {
        Object res = BaseUtils.callMember("stringify", object);
        return String.valueOf(res);
    }

//    /**
//     * 使用Json序列化 JS 对象(存在循环依赖问题)
//     *
//     * @param object JS 对象
//     */
//    public static String stringify(PropertyAccess object) {
//        Object res = BaseUtils.callMember("stringify", object);
//        return String.valueOf(res);
//    }
}
