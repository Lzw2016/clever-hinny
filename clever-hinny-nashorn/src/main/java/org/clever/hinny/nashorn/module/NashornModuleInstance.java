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

    public NashornModuleInstance(
            NashornScriptObject value,
            String id,
            String filename,
            boolean loaded,
            ModuleInstance<NashornScriptObject> parent,
            List<String> paths,
            List<ModuleInstance<NashornScriptObject>> children,
            ModuleInstance<NashornScriptObject> exports) {
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

    @Override
    public void onLoaded() {
    }

    @Override
    public void onRemove() {
    }
}
