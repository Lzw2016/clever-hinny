package org.clever.hinny.nashorn.require;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.module.ModuleInstance;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.require.RequireInstance;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:41 <br/>
 */
public class NashornRequireInstance implements RequireInstance {
    /**
     * require加载其他模块
     */
    private final Require<ScriptObjectMirror> require;
    /**
     * 所有缓存的模块
     */
    private final ModuleCache<ScriptObjectMirror> moduleCache;
    /**
     * 主模块，加载的入口脚本
     */
    private final ModuleInstance<ScriptObjectMirror> mainModule;

    public NashornRequireInstance(Require<ScriptObjectMirror> require, ModuleCache<ScriptObjectMirror> moduleCache, ModuleInstance<ScriptObjectMirror> mainModule) {
        this.require = require;
        this.moduleCache = moduleCache;
        this.mainModule = mainModule;
    }


//    require成员
//    require.resolve(request[, options])：    查询某个模块的完整路径，如果找不到模块，则会抛出 MODULE_NOT_FOUND 错误
//    require.resolve.paths(request) 返回一个数组，其中包含解析 request 过程中被查询的路径，如果 request 字符串指向核心模块（例如 http 或 fs）则返回 null
//    require.main：         指向主模块，表示当 Node.js 进程启动时加载的入口脚本
//    require.cache：        指向所有缓存的模块
}
