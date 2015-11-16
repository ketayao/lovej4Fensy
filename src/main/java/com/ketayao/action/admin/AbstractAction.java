/**
 * ketayao.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.ketayao.action.admin;

import com.ketayao.fensy.mvc.IUser;
import com.ketayao.pojo.User;

/**
 * 
 * @author yaoqiang.yq
 * @version $Id: AbstractAction.java, v 0.1 2015年9月20日 下午11:12:56 yaoqiang.yq Exp $
 */
public class AbstractAction {
    protected boolean hasPermission(User user, byte role) {
        return user.getRole() == role;
    }

    protected boolean isAdmin(User user) {
        return hasPermission(user, IUser.ROLE_TOP);
    }

}
