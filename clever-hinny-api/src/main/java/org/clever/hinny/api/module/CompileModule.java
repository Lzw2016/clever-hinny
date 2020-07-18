package org.clever.hinny.api.module;

import org.clever.hinny.api.folder.Folder;

/**
 * 编译脚本成ScriptModule
 *
 * @param <T> script引擎对象类型
 */
public interface CompileModule<T> {
    /**
     * 编译 Json Module
     */
    T compileJsonModule(Folder path) throws Exception;

    /**
     * 编译 JavaScript Module
     */
    T compileJavaScriptModule(Folder path) throws Exception;
}
