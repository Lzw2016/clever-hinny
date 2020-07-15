package org.clever.hinny.api.support;

import java.util.List;

/**
 * 脚本路径
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:08 <br/>
 */
public interface Path {
    /**
     * 获取根路径
     */
    Path getRoot();

    /**
     * 获取父路径
     * <pre>
     *  "/"           -> null
     *  "/a"          -> "/"
     *  "/a/file.js"  -> "/a"
     * </pre>
     */
    Path getParent();

    /**
     * 返回当前路径
     * <pre>
     *  "/"           -> ""
     *  "/a"          -> "a"
     *  "/a/file.js"  -> "file.js"
     * </pre>
     */
    String getName();

    /**
     * 返回全路径
     */
    String getFullPath();

    /**
     * 当前路径是否存在
     */
    boolean exists();

    /**
     * 当前路径是否是文件
     * <pre>
     *  不存在    -> false
     *  文件夹    -> false
     *  是文件    -> true
     * </pre>
     */
    boolean isFile();

    /**
     * 当前路径是否是文件夹
     * <pre>
     *  不存在    -> false
     *  文件夹    -> true
     *  是文件    -> false
     * </pre>
     *
     * @return true: 文件；false: 文件夹
     */
    boolean isDir();

    /**
     * 得到当前文件夹下的文件内容<br/>
     * <pre>
     *  不存在    -> null
     *  文件夹    -> null
     *  是文件    -> FileContent
     * </pre>
     *
     * @param name 文件名称
     * @return 文件不存在返回null
     */
    String getFileContent(String name);

    /**
     * 获取子路径列表
     * <pre>
     *  不存在    -> null
     *  文件夹    -> List<Path>
     *  是文件    -> null
     * </pre>
     */
    List<Path> getChildren();

    /**
     * 连接路径
     * <pre>
     *  /foo/ + bar          -->   /foo/bar
     *  /foo + bar           -->   /foo/bar
     *  /foo + /bar          -->   /bar
     *  /foo + C:/bar        -->   C:/bar
     *  /foo + C:bar         -->   C:bar (*)
     *  /foo/a/ + ../bar     -->   foo/bar
     *  /foo/ + ../../bar    -->   null
     *  /foo/ + /bar         -->   /bar
     *  /foo/.. + /bar       -->   /bar
     *  /foo + bar/c.txt     -->   /foo/bar/c.txt
     *  /foo/c.txt + bar     -->   /foo/c.txt/bar (!)
     * </pre>
     *
     * @param paths 子路径
     */
    Path concat(String... paths);

    /**
     * 创建 Path 对象
     */
    Path create(String path);

    // absolutePath
}
