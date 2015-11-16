/**
 * ketayao.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.ketayao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ketayao.fensy.mvc.IUser;

/**
 * 
 * @author yaoqiang.yq
 * @version $Id: RolePermission.java, v 0.1 2015年9月20日 下午11:29:14 yaoqiang.yq Exp $
 */
/**
 * 用户权限注释
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RolePermission {
    byte ROLE_BACKGROUND = 1;

    /**
     * 用户的角色
     * @return
     */
    public byte role() default IUser.ROLE_GENERAL;
}
