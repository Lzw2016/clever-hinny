package org.clever.hinny.api;

import org.clever.hinny.api.folder.AbstractFolder;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.clever.hinny.api.internal.*;
import org.clever.hinny.api.module.AbstractModule;
import org.clever.hinny.api.module.MemoryModuleCache;
import org.clever.hinny.api.require.AbstractRequire;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/17 21:13 <br/>
 */
public interface GlobalConstant {
    /**
     * JS时间默认格式
     */
    String JS_Default_Format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // --------------------------------------------------------------------------------------------
    // 全局变量
    // --------------------------------------------------------------------------------------------
    /**
     * require用于加载其他模块
     */
    String Engine_Require = "require";
    /**
     * 共享的全局变量
     */
    String Engine_Global = "global";

    // --------------------------------------------------------------------------------------------
    // module实例成员
    // --------------------------------------------------------------------------------------------
    /**
     * 主模块
     */
    String Module_Main = "<main>";
    /**
     * 模块的识别符，通常是带有绝对路径的模块文件名
     */
    String Module_Id = "id";
    /**
     * 模块的完全解析后的文件名，带有绝对路径
     */
    String Module_Filename = "filename";
    /**
     * 模块是否已经加载完成，或正在加载中
     */
    String Module_Loaded = "loaded";
    /**
     * 返回一个对象，最先引用该模块的模块
     */
    String Module_Parent = "parent";
    /**
     * 模块的搜索路径
     */
    String Module_Paths = "paths";
    /**
     * 被该模块引用的模块对象
     */
    String Module_Children = "children";
    /**
     * 表示模块对外输出的值
     */
    String Module_Exports = "exports";
    /**
     * module.require(id) 方法提供了一种加载模块的方法，就像从原始模块调用 require(id) 一样
     */
    String Module_Require = "require";

    // --------------------------------------------------------------------------------------------
    // CommonJS解析模块
    // --------------------------------------------------------------------------------------------
    /**
     * package.json 文件名
     */
    String CommonJS_Package = "package.json";
    /**
     * package.json 的 main 属性
     */
    String CommonJS_Package_Main = "main";
    /**
     * index.js 文件名
     */
    String CommonJS_Index = "index.js";
    /**
     * node_modules 文件夹名
     */
    String CommonJS_Node_Modules = "node_modules";

    // --------------------------------------------------------------------------------------------
    // 默认允许访问的class和全局对象
    // --------------------------------------------------------------------------------------------

    Set<Class<?>> Default_Allow_Access_Class = Collections.unmodifiableSet(
            new HashSet<>(
                    Arrays.asList(
                            Map.class,
                            List.class,
                            Set.class,
                            TestInternal.class,
                            CommonUtils.class,
                            AbstractFolder.class,
                            FileSystemFolder.class,
                            AbstractModule.class,
                            MemoryModuleCache.class,
                            AbstractRequire.class,
                            AbstractConsole.class,
                            LoggerConsole.class,
                            OutputStreamConsole.class,
                            LoggerFactory.class,
                            Logger.class,
                            org.slf4j.Logger.class
                    )
            )
    );

    Map<String, Object> Default_Context_Map = Collections.unmodifiableMap(new HashMap<String, Object>() {{
        put("TestInternal", TestInternal.Instance);
        put("CommonUtils", CommonUtils.Instance);
        put("console", LoggerConsole.Instance);
        put("print", LoggerConsole.Instance);
        put("LoggerFactory", LoggerFactory.Instance);
    }});
}
