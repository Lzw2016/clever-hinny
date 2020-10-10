package org.clever.hinny.graaljs.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 14:51 <br/>
 */
public class HashMapProxy extends HashMap<String, Object> implements ProxyObject {
    public HashMapProxy() {
    }

    public HashMapProxy(int initialCapacity) {
        super(initialCapacity);
    }

    public HashMapProxy(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public HashMapProxy(Map<? extends String, ?> m) {
        super(m);
    }

    @Override
    public Object getMember(String key) {
        return this.get(key);
    }

    @Override
    public Object getMemberKeys() {
        // return null;
        final Object[] keyArray = this.keySet().toArray();
        return new ProxyArray() {
            private final Object[] keys = keyArray;

            public void set(long index, Value value) {
                throw new UnsupportedOperationException();
            }

            public long getSize() {
                return keys.length;
            }

            public Object get(long index) {
                if (index < 0 || index > Integer.MAX_VALUE) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return keys[(int) index];
            }
        };
    }

    @Override
    public boolean hasMember(String key) {
        return this.containsKey(key);
    }

    @Override
    public void putMember(String key, Value value) {
        this.put(key, value.isHostObject() ? value.asHostObject() : value);
    }

    @Override
    public boolean removeMember(String key) {
        if (this.containsKey(key)) {
            this.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
