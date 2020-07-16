package org.clever.hinny.api.module;

import org.clever.hinny.api.ScriptObject;

import java.util.List;
import java.util.Set;

/**
 * 脚本模块 AbstractModuleInstance
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:14 <br/>
 *
 * @param <T> script对象类型
 */
public interface ModuleInstance<T extends ScriptObject> {
    /**
     * 模块的识别符，通常是带有绝对路径的模块文件名
     */
    String getId();

    /**
     * 模块的完全解析后的文件名，带有绝对路径
     */
    String getFilename();

    /**
     * 模块是否已经加载完成，或正在加载中
     */
    boolean isLoaded();

    /**
     * 返回一个对象，最先引用该模块的模块
     */
    ModuleInstance<T> getParent();

    /**
     * 模块的搜索路径
     */
    List<String> paths();

    /**
     * 被该模块引用的模块对象
     */
    Set<ModuleInstance<T>> getChildren();

    /**
     * 表示模块对外输出的值
     */
    T getExports();

    /**
     * module.require() 方法提供了一种加载模块的方法，就像从原始模块调用 require() 一样
     *
     * @param id 模块ID
     */
    T require(String id) throws Exception;

    /**
     * 返回表示当前模块的script对象
     */
    T getModuleInstance();

//    module实例
//    module.id           模块的识别符，通常是带有绝对路径的模块文件名。
//    module.filename     模块的完全解析后的文件名，带有绝对路径。
//    module.loaded       模块是否已经加载完成，或正在加载中。
//    module.parent       返回一个对象，最先引用该模块的模块。
//    module.paths        模块的搜索路径
//    module.children     被该模块引用的模块对象
//    module.exports      表示模块对外输出的值。
//    module.require(id)  module.require() 方法提供了一种加载模块的方法，就像从原始模块调用 require() 一样
}
