package org.clever.hinny.api.folder;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 10:48 <br/>
 */
public class FolderNotFoundException extends RuntimeException {

    public FolderNotFoundException() {
    }

    public FolderNotFoundException(String message) {
        super(message);
    }

    public FolderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotFoundException(Throwable cause) {
        super(cause);
    }
}
