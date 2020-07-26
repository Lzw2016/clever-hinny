package org.clever.hinny.api.internal;

import org.slf4j.Logger;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 08:45 <br/>
 */
public class LoggerFactory {
    public static final LoggerFactory Instance = new LoggerFactory();

    private LoggerFactory() {
    }

    /**
     * 获取日志对象
     *
     * @param name 名称
     */
    public Logger getLogger(String name) {
        return org.slf4j.LoggerFactory.getLogger(name);
    }
}
