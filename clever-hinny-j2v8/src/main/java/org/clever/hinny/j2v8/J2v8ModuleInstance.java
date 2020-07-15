package org.clever.hinny.j2v8;

import com.eclipsesource.v8.V8Object;
import org.clever.hinny.api.ModuleInstance;

import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/15 22:57 <br/>
 */
public class J2v8ModuleInstance implements ModuleInstance<V8Object> {
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
    public ModuleInstance<V8Object> getParent() {
        return null;
    }

    @Override
    public List<String> paths() {
        return null;
    }

    @Override
    public Set<ModuleInstance<V8Object>> getChildren() {
        return null;
    }

    @Override
    public V8Object getExports() {
        return null;
    }

    @Override
    public V8Object require(String id) throws Exception {
        return null;
    }

    @Override
    public V8Object getModuleInstance() {
        return null;
    }
}
