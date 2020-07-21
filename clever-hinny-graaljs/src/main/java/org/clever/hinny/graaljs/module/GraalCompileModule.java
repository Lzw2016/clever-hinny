package org.clever.hinny.graaljs.module;

import org.apache.commons.lang3.StringUtils;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.folder.ReadFileContentException;
import org.clever.hinny.api.module.AbstractCompileModule;
import org.clever.hinny.graaljs.GraalConstant;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:57 <br/>
 */
public class GraalCompileModule extends AbstractCompileModule<Context, Value> {

    public GraalCompileModule(ScriptEngineContext<Context, Value> context) {
        super(context);
    }

    @Override
    public Value compileJsonModule(Folder path) {
        final String json = path.getFileContent();
        if (StringUtils.isBlank(json)) {
            throw new ReadFileContentException("读取文件Json内容失败: path=" + path.getFullPath());
        }
        return ScriptEngineUtils.parseJson(context.getEngine(), json);
    }

    @Override
    public Value compileJavaScriptModule(Folder path) {
        final String code = path.getFileContent();
        if (code == null) {
            throw new ReadFileContentException("读取文件内容失败: path=" + path.getFullPath());
        }
        final String moduleScriptCode = getModuleScriptCode(code);
        Source source = Source.newBuilder(GraalConstant.Js_Language_Id, moduleScriptCode, path.getFullPath()).cached(true).buildLiteral();
        context.getEngine().enter();
        Value modelFunction = context.getEngine().eval(source);
        context.getEngine().leave();
        return modelFunction;
    }
}
