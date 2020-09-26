package org.clever.hinny.api.folder;

import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/29 19:04 <br/>
 */
public class ClassPathFolder implements Folder {
//    /**
//     * 基础物理绝对路径(所有的文件和文件夹都在这个基础路径下)
//     */
//    protected final String baseAbsolutePath;
//    /**
//     * 当前路径的物理绝对路径
//     */
//    protected final String absolutePath;
//    /**
//     * 当前路径的逻辑绝对路径(使用了统一的分隔符)
//     */
//    protected final String fullPath;

    /**
     * @param basePath 基础路径
     */
    private ClassPathFolder(String basePath) {
//        basePath = FilenameUtils.normalize(basePath);
//        this.baseAbsolutePath = getAbsolutePath(basePath);
//        this.absolutePath = this.baseAbsolutePath;
//        this.fullPath = Folder.Root_Path;
//        checkPath();
    }

    /**
     * @param basePath 基础路径
     * @param path     当前路径(相当路径或者绝对路径)
     */
    private ClassPathFolder(String basePath, String path) {

    }

    @Override
    public Folder getRoot() {
        return null;
    }

    @Override
    public Folder getParent() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getFullPath() {
        return null;
    }

    @Override
    public String getAbsolutePath() {
        return null;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isDir() {
        return false;
    }

    @Override
    public String getFileContent(String name) {
        return null;
    }

    @Override
    public String getFileContent() {
        return null;
    }

    @Override
    public List<Folder> getChildren() {
        return null;
    }

    @Override
    public Folder concat(String... paths) {
        return null;
    }

    @Override
    public Folder create(String path) {
        return null;
    }
}
