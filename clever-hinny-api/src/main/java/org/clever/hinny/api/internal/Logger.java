package org.clever.hinny.api.internal;

import lombok.Getter;
import org.clever.hinny.api.internal.support.ObjectToString;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.api.utils.TupleTow;
import org.slf4j.LoggerFactory;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 11:26 <br/>
 */
public class Logger {
    /**
     * 日志记录器
     */
    protected final org.slf4j.Logger logger;

    /**
     * toString实现
     */
    @Getter
    protected ObjectToString objectToString = ObjectToString.Instance;

    public Logger(String name) {
        logger = LoggerFactory.getLogger(name);
    }

    public void setObjectToString(ObjectToString objectToString) {
        Assert.notNull(objectToString, "参数objectToString不能为空");
        this.objectToString = objectToString;
    }

    /**
     * 打印输出
     *
     * @param args 输出数据
     */
    public void trace(String format, Object... args) {
        if (logger.isTraceEnabled()) {
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.trace(tupleTow.getValue1());
            } else {
                logger.trace(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    /**
     * debug打印输出
     *
     * @param args 输出数据
     */
    public void debug(String format, Object... args) {
        if (logger.isDebugEnabled()) {
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.debug(tupleTow.getValue1());
            } else {
                logger.debug(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }


    /**
     * info打印输出
     *
     * @param args 输出数据
     */
    public void info(String format, Object... args) {
        if (logger.isInfoEnabled()) {
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.info(tupleTow.getValue1());
            } else {
                logger.info(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    /**
     * warn打印输出
     *
     * @param args 输出数据
     */
    public void warn(String format, Object... args) {
        if (logger.isWarnEnabled()) {
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.warn(tupleTow.getValue1());
            } else {
                logger.warn(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    /**
     * error打印输出
     *
     * @param args 输出数据
     */
    public void error(String format, Object... args) {
        if (logger.isErrorEnabled()) {
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.error(tupleTow.getValue1());
            } else {
                logger.error(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    /**
     * 根据日志输出参数得到日志字符串
     */
    protected TupleTow<String, Throwable> logString(String format, Object... args) {
        if (args == null || args.length <= 0) {
            return TupleTow.creat(format, null);
        }
        Throwable throwable = null;
        if (args[args.length - 1] instanceof Throwable) {
            throwable = (Throwable) args[args.length - 1];
        }
        String logsText;
        if (throwable == null) {
            logsText = objectToString.format(format, args);
        } else {
            int length = args.length - 1;
            Object[] array = new Object[length];
            System.arraycopy(args, 0, array, 0, length);
            logsText = objectToString.format(format, array);
        }
        return TupleTow.creat(logsText, throwable);
    }
}
