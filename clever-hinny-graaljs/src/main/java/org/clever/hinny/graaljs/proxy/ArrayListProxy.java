package org.clever.hinny.graaljs.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.ArrayList;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 15:02 <br/>
 */
public class ArrayListProxy extends ArrayList<Object> implements ProxyArray {
    @Override
    public Object get(long index) {
        checkIndex(index);
        return this.get((int) index);
    }

    @Override
    public void set(long index, Value value) {
        checkIndex(index);
        Object element = value.isHostObject() ? value.asHostObject() : value;
        this.set((int) index, element);
    }

    @Override
    public boolean remove(long index) {
        checkIndex(index);
        this.remove((int) index);
        return true;
    }

    @Override
    public long getSize() {
        return this.size();
    }

    protected void checkIndex(long index) {
        if (index > Integer.MAX_VALUE || index < 0) {
            throw new ArrayIndexOutOfBoundsException("invalid index. index=" + index);
        }
    }
}
