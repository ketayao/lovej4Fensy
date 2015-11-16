/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.ketayao.exception;

/**
 * 
 * @author yaoqiang.yq
 * @version $Id: PermissionException.java, v 0.1 2015年9月20日 下午11:59:23 yaoqiang.yq Exp $
 */
public class PermissionException extends LoveJException {

    /** uid */
    private static final long serialVersionUID = -3869787063692108516L;

    /**
     * 
     */
    public PermissionException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public PermissionException(Throwable cause) {
        super(cause);
    }

}
