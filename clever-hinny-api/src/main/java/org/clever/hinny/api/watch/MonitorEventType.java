package org.clever.hinny.api.watch;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/30 11:49 <br/>
 */
public enum MonitorEventType {
//    /**
//     * 文件系统观察器开始检查事件
//     */
//    OnStart,
//    /**
//     * 文件系统观察程序完成了检查事件
//     */
//    OnStop,
    /**
     * 目录创建事件
     */
    OnDirectoryCreate,
    /**
     * 目录已更改事件
     */
    OnDirectoryChange,
    /**
     * 目录删除事件
     */
    OnDirectoryDelete,
    /**
     * 文件创建事件
     */
    OnFileCreate,
    /**
     * 文件更改事件
     */
    OnFileChange,
    /**
     * 文件删除事件
     */
    OnFileDelete,
}