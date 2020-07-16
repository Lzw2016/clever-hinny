package org.clever.hinny.nashorn;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.require.NashornRequire;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:25 <br/>
 */
public class NashornScriptEngineInstance implements ScriptEngineInstance<NashornScriptObject> {
    /**
     * 根路径文件夹
     */
    private final Folder rootPath;
    /**
     * 引擎上下文
     */
    private final ScriptEngineContext<NashornScriptObject> engineContext = new NashornScriptEngineContext();
    /**
     * 共享的全局变量
     */
    private final NashornScriptObject global = new NashornScriptObject();
    /**
     * require用于加载其他模块
     */
    private final Require<NashornScriptObject> require = new NashornRequire();

    public NashornScriptEngineInstance(Folder rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public ScriptEngineContext<NashornScriptObject> getScriptEngineContext() {
        return null;
    }

    @Override
    public Folder getRootPath() {
        return null;
    }

    @Override
    public NashornScriptObject getGlobal() {
        return null;
    }
}