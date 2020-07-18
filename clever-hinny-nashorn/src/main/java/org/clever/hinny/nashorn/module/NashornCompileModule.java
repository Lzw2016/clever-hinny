package org.clever.hinny.nashorn.module;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.folder.ReadFileContentException;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/18 09:20 <br/>
 */
public class NashornCompileModule implements CompileModule<ScriptObjectMirror> {
    /**
     * NashornScriptEngine
     */
    private final NashornScriptEngine engine;

    public NashornCompileModule(NashornScriptEngine engine) {
        this.engine = engine;
    }

    @Override
    public ScriptObjectMirror compileJsonModule(Folder path) {
        return ScriptEngineUtils.parseJson(path.getFileContent());
    }

    @Override
    public ScriptObjectMirror compileJavaScriptModule(Folder path) throws Exception {
        final String code = path.getFileContent();
        if (code == null) {
            throw new ReadFileContentException("读取文件内容失败: path=" + path.getFullPath());
        }
        final String scriptCode = "(function(exports, require, module, __filename, __dirname) {\n" + code + "\n});";
        return (ScriptObjectMirror) engine.eval(scriptCode);
    }
}
