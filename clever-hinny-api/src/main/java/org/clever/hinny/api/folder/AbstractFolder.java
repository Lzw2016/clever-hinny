package org.clever.hinny.api.folder;

import lombok.EqualsAndHashCode;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 08:58 <br/>
 */
@EqualsAndHashCode
public abstract class AbstractFolder implements Folder {
    /**
     * 基础物理绝对路径(所有的文件和文件夹都在这个基础路径下)
     */
    private final String baseAbsolutePath;
    /**
     * 当前路径的物理绝对路径
     */
    private final String absolutePath;
    /**
     * 当前路径(逻辑绝对路径，使用了统一的分隔符)
     */
    private final String fullPath;

    /**
     * @param basePath 基础路径
     */
    public AbstractFolder(String basePath) {
        this.baseAbsolutePath = getAbsolutePath(basePath);
        this.absolutePath = this.baseAbsolutePath;
        checkPath();
        this.fullPath = Folder.Root_Path;
    }

    /**
     * @param basePath 基础路径
     * @param path     当前路径(相当路径或者绝对路径)
     */
    public AbstractFolder(String basePath, String path) {
        this.baseAbsolutePath = getAbsolutePath(basePath);
        this.absolutePath = FilenameUtils.concat(this.baseAbsolutePath, path);
        checkPath();
        path = this.absolutePath.substring(this.baseAbsolutePath.length());
        path = replaceSeparate(path);
        if (path.length() > 1 && path.endsWith(Folder.Path_Separate)) {
            path = path.substring(0, path.length() - 1);
        }
        this.fullPath = path;
    }

    @Override
    public Folder getRoot() {
        return this.concat(Folder.Root_Path);
    }

    @Override
    public Folder getParent() {
        return this.concat(Folder.Parent_Path);
    }

    @Override
    public String getName() {
        return FilenameUtils.getName(fullPath);
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public boolean exists() {
        return exists(this.absolutePath);
    }

    @Override
    public boolean isFile() {
        if (!exists(this.absolutePath)) {
            return false;
        }
        return isFile(this.absolutePath);
    }

    @Override
    public boolean isDir() {
        if (!exists(this.absolutePath)) {
            return false;
        }
        return !isFile(this.absolutePath);
    }

    @Override
    public String getFileContent(String name) {
        String filePath = FilenameUtils.concat(this.absolutePath, name);
        if (filePath == null) {
            return null;
        }
        filePath = getAbsolutePath(filePath);
        if (filePath.startsWith(this.baseAbsolutePath) && exists(filePath) && isFile(filePath)) {
            return getContent(filePath);
        }
        return null;
    }

    @Override
    public String getFileContent() {
        if (exists(this.baseAbsolutePath) && isFile(this.baseAbsolutePath)) {
            return getContent(this.baseAbsolutePath);
        }
        return null;
    }

    @Override
    public List<Folder> getChildren() {
        List<String> children = getChildren(this.absolutePath);
        if (children == null) {
            return null;
        }
        List<Folder> folders = new ArrayList<>(children.size());
        for (String child : children) {
            Folder folder = this.create(child);
            folders.add(folder);
        }
        return folders;
    }

    @Override
    public Folder concat(String... paths) {
        String fullPath = concatPath(this.fullPath, paths);
        if (fullPath == null) {
            return null;
        }
        return this.create(fullPath);
    }

    /**
     * 获取一个路径的绝对路径
     */
    protected abstract String getAbsolutePath(String path);

    /**
     * 判断一个路径是否存在
     *
     * @param absolutePath 物理绝对路径
     */
    protected abstract boolean exists(String absolutePath);

    /**
     * 判断是否是文件
     *
     * @param absolutePath 物理绝对路径
     */
    protected abstract boolean isFile(String absolutePath);

    /**
     * 获取文件文本内容
     *
     * @param absolutePath 物理绝对路径
     */
    protected abstract String getContent(String absolutePath);

    /**
     * 获取子路径列表
     *
     * @param absolutePath 物理绝对路径
     */
    protected abstract List<String> getChildren(String absolutePath);

    /**
     * 校验当前对象是否合法<br/>
     * 1. baseAbsolutePath 必须存在
     * 2. absolutePath必须是baseAbsolutePath的子目录
     */
    protected void checkPath() {
        if (StringUtils.isBlank(this.baseAbsolutePath)) {
            throw new IllegalArgumentException("属性baseAbsolutePath不能为空");
        }
        if (StringUtils.isBlank(this.fullPath)) {
            throw new IllegalArgumentException("属性path不能为空");
        }
        if (StringUtils.isBlank(this.absolutePath)) {
            throw new IllegalArgumentException("属性absolutePath不能为空");
        }
        if (!exists(this.baseAbsolutePath)) {
            throw new FolderNotFoundException(String.format("路径:%s不存在", this.baseAbsolutePath));
        }
        if (!this.absolutePath.startsWith(this.baseAbsolutePath)) {
            throw new IllegalArgumentException("absolutePath必须是baseAbsolutePath的子目录");
        }
    }

    /**
     * 连接路径，超出路径范围返回null(结束字符不是路径分隔符，除了根路径)
     */
    public static String concatPath(String basePath, String... paths) {
        if (StringUtils.isBlank(basePath)) {
            basePath = Folder.Root_Path;
        }
        if (paths != null) {
            for (String path : paths) {
                path = StringUtils.trim(path);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                basePath = FilenameUtils.concat(basePath, path);
            }
        }
        if (basePath != null) {
            basePath = replaceSeparate(basePath);
            if (basePath.length() > 1 && basePath.endsWith(Folder.Path_Separate)) {
                basePath = basePath.substring(0, basePath.length() - 1);
            }
        }
        return basePath;
    }

    /**
     * 处理路径分隔符，使用统一的分隔符
     */
    private static String replaceSeparate(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        return path.replaceAll("\\\\", Folder.Path_Separate);
    }
}
