package org.clever.hinny.nashorn.module;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.AbstractModule;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.nashorn.NashornScriptObject;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/15 22:39 <br/>
 */
public class NashornModule extends AbstractModule<NashornScriptEngine, ScriptObjectMirror> {
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
    private ScriptObjectMirror exports;
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

    public NashornModule(
            ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context,
            String id,
            String filename,
            ScriptObjectMirror exports,
            Module<ScriptObjectMirror> parent,
            Require<ScriptObjectMirror> require) {
        super(context);
        Assert.isNotBlank(id, "参数id不能为空");
        Assert.isNotBlank(filename, "参数filename不能为空");
        Assert.notNull(exports, "参数exports不能为空");
        Assert.notNull(parent, "参数parent不能为空");
        Assert.notNull(require, "参数require不能为空");
        this.id = id;
        this.filename = filename;
        this.exports = exports;
        this.parent = parent;
        this.parent.addChildModule(this);
        this.require = require;
        this.module = ScriptEngineUtils.newObject();
        initModule();
    }

    private NashornModule(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        super(context);
        this.id = GlobalConstant.Module_Main;
        this.filename = Folder.Root_Path + GlobalConstant.Module_Main;
        this.exports = ScriptEngineUtils.newObject();
        this.parent = null;
        this.require = context.getRequire();
        this.module = ScriptEngineUtils.newObject();
        initModule();
    }

    private void initModule() {
        this.module.put(GlobalConstant.Module_Id, this.id);
        this.module.put(GlobalConstant.Module_Filename, this.filename);
        this.module.put(GlobalConstant.Module_Loaded, this.loaded);
        if (this.parent != null) {
            this.module.put(GlobalConstant.Module_Parent, this.parent.getModule());
        }
        // TODO  Module_Paths
        this.module.put(GlobalConstant.Module_Paths, ScriptEngineUtils.newArray());
        // TODO  Module_Children
        this.module.put(GlobalConstant.Module_Children, ScriptEngineUtils.newArray());
        this.module.put(GlobalConstant.Module_Exports, this.exports);
        this.module.put(GlobalConstant.Module_Require, this.require);
    }

    /**
     * 创建主模块(根模块)
     */
    public static NashornModule createMainModule(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        return new NashornModule(context);
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
    public ScriptObject<ScriptObjectMirror> getExportsWrapper() {
        return new NashornScriptObject(exports);
    }

    @Override
    public ScriptObjectMirror require(String id) throws Exception {
        return require.require(id);
    }

    @Override
    public Require<ScriptObjectMirror> getRequire() {
        return require;
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
        this.module.put(GlobalConstant.Module_Loaded, true);
        // 修正导出对象
        Object exportsObject = this.module.getMember(GlobalConstant.Module_Exports);
        if (exportsObject instanceof ScriptObjectMirror) {
            exports = (ScriptObjectMirror) exportsObject;
        }
        // TODO triggerOnLoaded
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
        childrenIds.add(childModule.getId());
    }
}
