package org.clever.hinny.api;

import org.clever.hinny.api.folder.Folder;

/**
 * 脚本引擎实例
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:15 <br/>
 *
 * @param <T> script对象类型
 */
public interface ScriptEngineInstance<T extends ScriptObject<?>> {

    /**
     * 获取脚本引擎上下文
     */
    ScriptEngineContext<T> getScriptEngineContext();

    Folder getRootPath();

    T getGlobal();




//    全局变量
//    module    module变量代表当前模块
//    exports   就是module.exports
//    require   require用于加载其他模块
//    global    共享的全局变量
}
