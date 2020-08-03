package org.clever.hinny.graaljs.utils;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JavaScript 与 Java 相互交互工具
 * 作者：lizw <br/>
 * 创建时间：2020/07/30 21:36 <br/>
 */
@Slf4j
public class JavaInteropUtils {
    /**
     * Java 8中基本类型
     */
    private static final Set<String> Java_Base_Type = Set.of("long", "int", "short", "char", "byte", "boolean", "float", "double");

    public static final JavaInteropUtils Instance = new JavaInteropUtils();

    private JavaInteropUtils() {
    }

    // ------------------------------------------------------------------------------------------------------------------ Java -> JS

    /**
     * 把Java Map对象转换成JavaScript Object对象<br />
     * <strong>注意: key会被强制转换成String</strong> <br />
     *
     * @param map Java Map对象
     */
    public Value toScriptObject(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        Value value = ScriptEngineUtils.newObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            value.putMember(String.valueOf(entry.getKey()), entry.getValue());
        }
        return value;
    }

    /**
     * 把Java 集合对象转换成JavaScript Array对象<br />
     * <strong>注意: 集合元素如果是Java Map类型会做进一步转换(JavaScript Object对象)</strong> <br />
     *
     * @param list Java 集合对象
     */
    public Value toScriptArray(List<?> list) {
        if (list == null) {
            return null;
        }
        Object[] paramsArray = list.toArray();
        return toScriptArray(paramsArray);
    }

    /**
     * 把Java数组装换成JavaScript Array对象<br />
     * * <strong>注意: 集合元素如果是Java Map类型会做进一步转换(JavaScript Object对象)</strong> <br />
     *
     * @param array Java数组
     */
    public Value toScriptArray(Object[] array) {
        if (array == null) {
            return null;
        }
        Object[] resArray = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            Object item = array[i];
            if (item instanceof Map) {
                resArray[i] = toScriptObject((Map<?, ?>) item);
            } else {
                resArray[i] = item;
            }
        }
        return ScriptEngineUtils.newArray(resArray);
    }

//    public Collection<Value> collectionInMapToScript(Collection<?> collection) {
//        if (collection == null) {
//            return null;
//        }
//        Collection<Value> res;
//        if (collection instanceof Set) {
//            res = new HashSet<>(collection.size());
//        } else {
//            res = new ArrayList<>(collection.size());
//        }
//        Context context = null;
//        for (Object item : collection) {
//            if (item instanceof Map) {
//                res.add(toScriptObject((Map<?, ?>) item));
//            } else {
//                if (context == null) {
//                    context = Context.getCurrent();
//                }
//                res.add(context.asValue(item));
//            }
//        }
//        return res;
//    }
}
