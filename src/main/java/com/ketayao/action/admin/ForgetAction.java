/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.ketayao.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.util.CryptUtils;
import com.ketayao.pojo.User;
import com.ketayao.system.SystemConfig;
import com.ketayao.util.EmailUtils;

/**
 * 
 * @author yaoqiang.yq
 * @version $Id: ForgetAction.java, v 0.1 2015年12月12日 下午9:59:49 yaoqiang.yq Exp $
 */
public class ForgetAction {

    public String modifyForgetPwd(WebContext rc) throws Exception {
        if (checkForgetInfos(rc)) {
            String username = rc.getParam("username");
            User user = User.INSTANCE.getByAttr("username", username);

            String newPassword = rc.getParam("newPassword");

            String pwd = CryptUtils.encrypt(newPassword, user.getSalt());
            user.updateAttr("password", pwd);
        }
        return "redirect:" + rc.getContextPath() + "/admin/login";
    }

    public String inputPwd(WebContext rc) throws Exception {
        if (checkForgetInfos(rc)) {
            return "admin/forget/modifyForgetPwd";
        }
        return "redirect:" + rc.getContextPath() + "/admin/login";
    }

    private boolean checkForgetInfos(WebContext rc) {
        String username = rc.getParam("username");
        String forgetCode = rc.getParam("forgetCode");

        User user = User.INSTANCE.getByAttr("username", username);
        if (user != null && StringUtils.equals(user.getForgetCode(), forgetCode)) {

            if (user.getForgetDuration().after(new Date())) {
                rc.setRequestAttr("username", username);
                rc.setRequestAttr("forgetCode", forgetCode);

                return true;
            }
        }

        return false;
    }

    public void forgetPwd(WebContext rc) throws Exception {
        String username = rc.getParam("username");

        User user = User.INSTANCE.getByAttr("username", username);
        Date duration = DateUtils.addDays(new Date(), 1);
        String uuid = UUID.randomUUID().toString();

        user.updateAttrs(new String[] { "forgetCode", "forgetDuration" },
            new Object[] { uuid, duration });

        List<String> arr = new ArrayList<String>();
        arr.add(user.getEmail());

        String path = rc.getRequest().getContextPath();
        String base = rc.getRequest().getScheme() + "://" + rc.getRequest().getServerName() + ":"
                      + rc.getRequest().getServerPort() + path;
        base = base + "/admin/forget/inputPwd?username=" + user.getUsername() + "&forgetCode="
               + uuid;

        EmailUtils.sendHtmlMail(arr,
            SystemConfig.getSiteConfig().getName() + "找回密码", "请在有效时间内("
                                                             + DateFormatUtils.format(duration,
                                                                 "yyyy-MM-dd HH:mm:ss")
                                                             + ")点击链接，修改密码。<br/>找回密码：<a href='"
                                                             + base + "'>" + base + "<a>");

        rc.print("请查收邮件，修改密码...");
    }
}
