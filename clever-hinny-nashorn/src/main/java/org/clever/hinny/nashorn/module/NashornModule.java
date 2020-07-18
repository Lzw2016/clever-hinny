package org.clever.hinny.nashorn.module;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/15 22:39 <br/>
 */
public class NashornModule implements Module<ScriptObjectMirror> {
    /**
     * 当前模块对象对应的 module 对象
     */
    private final ScriptObjectMirror module;
    /**
     * 模块的识别符，通常是带有绝对路径的模块文件名
     */
    private final String id;
    /**
     * 模块的完全解析后的文件名，带有绝对路径
     */
    private final String filename;
    /**
     * 当前模块对应的script对象
     */
    private final ScriptObjectMirror exports;
    /**
     * 返回一个对象，最先引用该模块的模块
     */
    private final Module<ScriptObjectMirror> parent;
    /**
     * module.require() 方法提供了一种加载模块的方法，就像从原始模块调用 require() 一样
     */
    private final Require<ScriptObjectMirror> require;
    /**
     * 子模块ID集合
     */
    private final Set<String> childrenIds = new HashSet<>();
    /**
     * 模块是否已经加载完成，或正在加载中
     */
    private boolean loaded = false;
    /**
     * 模块是否已经移除
     */
    private boolean removed = false;

    public NashornModule(String id, String filename, ScriptObjectMirror exports, Module<ScriptObjectMirror> parent, Require<ScriptObjectMirror> require) {
        // TODO 参数校验
        this.id = id;
        this.filename = filename;
        this.exports = exports;
        this.parent = parent;
        this.parent.addChildModule(this);
        this.require = require;
        this.module = ScriptEngineUtils.newObject();
        this.module.put(GlobalConstant.Module_Id, this.id);
        this.module.put(GlobalConstant.Module_Filename, this.filename);
        this.module.put(GlobalConstant.Module_Loaded, this.loaded);
        this.module.put(GlobalConstant.Module_Parent, this.parent.getModule());
        // TODO  Module_Paths
        this.module.put(GlobalConstant.Module_Paths, ScriptEngineUtils.newArray());
        // TODO  Module_Children
        this.module.put(GlobalConstant.Module_Children, ScriptEngineUtils.newArray());
        this.module.put(GlobalConstant.Module_Exports, this.exports);
        this.module.put(GlobalConstant.Module_Require, this.require);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public Module<ScriptObjectMirror> getParent() {
        return parent;
    }

    @Override
    public List<String> paths() {
        // TODO paths
        return null;
    }

    @Override
    public List<Module<ScriptObjectMirror>> getChildren() {
        // TODO getChildren
        return null;
    }

    @Override
    public ScriptObjectMirror getExports() {
        return exports;
    }

    @Override
    public ScriptObjectMirror require(String id) throws Exception {
        return require.require(id);
    }

    @Override
    public ScriptObjectMirror getModule() {
        return module;
    }

    @Override
    public void triggerOnLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        removed = false;
        // TODO triggerOnLoaded
        this.module.put(GlobalConstant.Module_Loaded, true);

    }

    @Override
    public void triggerOnRemove() {
        if (removed) {
            return;
        }
        removed = true;
        // TODO triggerOnRemove
    }

    @Override
    public void addChildModule(Module<ScriptObjectMirror> childModule) {
        if (childModule == null || childrenIds.contains(childModule.getId())) {
            return;
        }
        // TODO addChildModule
    }
}
