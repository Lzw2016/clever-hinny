//package org.clever.hinny.api.folder;
//
//import org.clever.hinny.api.utils.Assert;
//
//import java.util.List;
//
///**
// * 作者：lizw <br/>
// * 创建时间：2020/08/29 19:04 <br/>
// */
//public class ResourceFolder extends AbstractFolder {
//    private final ClassLoader loader;
//
//    public ResourceFolder(ClassLoader loader, String basePath) {
//        super(basePath);
//        Assert.notNull(loader, "参数loader不能为null");
//        this.loader = loader;
//    }
//
//    public ResourceFolder(ClassLoader loader, String basePath, String path) {
//        super(basePath, path);
//        Assert.notNull(loader, "参数loader不能为null");
//        this.loader = loader;
//    }
//
//    @Override
//    protected String getAbsolutePath(String path) {
//        return null;
//    }
//
//    @Override
//    protected boolean exists(String absolutePath) {
//        return false;
//    }
//
//    @Override
//    protected boolean isFile(String absolutePath) {
//        return false;
//    }
//
//    @Override
//    protected String getContent(String absolutePath) {
//        return null;
//    }
//
//    @Override
//    protected List<String> getChildren(String absolutePath) {
//        return null;
//    }
//
//    @Override
//    public Folder create(String path) {
//        return null;
//    }
//}
