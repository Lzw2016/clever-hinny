package org.clever.hinny.graaljs.utils;

import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.graaljs.GraalConstant;
import org.graalvm.polyglot.*;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:59 <br/>
 */
public class ScriptEngineUtils {
    /**
     * Context 默认选项
     */
    public static final Map<String, String> Context_Default_Options = Map.of(
            "js.ecmascript-version", GraalConstant.ECMAScript_Version
    );

    private static final Source Object_Constructor_Source = Source.newBuilder(GraalConstant.Js_Language_Id, "Object", "Unnamed").cached(true).buildLiteral();
    private static final Source Array_Constructor_Source = Source.newBuilder(GraalConstant.Js_Language_Id, "Array", "Unnamed").cached(true).buildLiteral();
    private static final Source Json_Constructor_Source = Source.newBuilder(GraalConstant.Js_Language_Id, "JSON", "Unnamed").cached(true).buildLiteral();

    /**
     * 创建一个新的 Context
     */
    public static Context creatEngine(Engine engine) {
        Assert.notNull(engine, "参数engine不能为空");
        // TODO 沙箱环境控制
        return Context.newBuilder(GraalConstant.Js_Language_Id)
//                .allowHostAccess(HostAccess.NONE)
//                .allowNativeAccess(false)
//                .allowCreateThread(false)
                .allowAllAccess(true)
//                .allowHostClassLoading(false)
//                // .allowHostClassLookup()
//                .allowExperimentalOptions(false)
//                .allowPolyglotAccess(PolyglotAccess.NONE)
//                .allowIO(false)
//                .allowCreateProcess(false)
//                .allowEnvironmentAccess(EnvironmentAccess.NONE)
                // .resourceLimits()
                .options(Context_Default_Options)
                .engine(engine)
                .build();
    }

    /**
     * 新建一个js 普通对象
     */
    public static Value newObject(Context context, Object... args) {
        Assert.notNull(context, "参数context不能为空");
        context.enter();
        Value constructor = context.eval(Object_Constructor_Source);
        context.leave();
        return constructor.newInstance(args);
    }

    /**
     * 新建一个js 数组对象
     */
    public static Value newArray(Context context, Object... args) {
        Assert.notNull(context, "参数context不能为空");
        context.enter();
        Value constructor = context.eval(Array_Constructor_Source);
        context.leave();
        return constructor.newInstance(args);
    }

    /**
     * 解析Json成为 Value 对象
     */
    public static Value parseJson(Context context, String json) {
        Assert.notNull(context, "参数context不能为空");
        context.enter();
        Value constructor = context.eval(Json_Constructor_Source);
        context.leave();
        return constructor.invokeMember("parse", json);
    }
}
