package org.clever.hinny.nashorn.module;

import org.clever.hinny.api.module.ModuleInstance;
import org.clever.hinny.nashorn.NashornScriptObject;

import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/15 22:39 <br/>
 */
public class NashornModuleInstance implements ModuleInstance<NashornScriptObject> {

    private final NashornScriptObject value;

    public NashornModuleInstance(NashornScriptObject value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public ModuleInstance<NashornScriptObject> getParent() {
        return null;
    }

    @Override
    public List<String> paths() {
        return null;
    }

    @Override
    public Set<ModuleInstance<NashornScriptObject>> getChildren() {
        return null;
    }

    @Override
    public NashornScriptObject getExports() {
        return null;
    }

    @Override
    public NashornScriptObject require(String id) throws Exception {
        return null;
    }

    @Override
    public NashornScriptObject getModuleInstance() {
        return null;
    }

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
