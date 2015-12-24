/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.ketayao.util;

import java.util.List;

import org.apache.commons.mail.HtmlEmail;

import com.ketayao.system.SystemConfig;

/**
 * 
 * @author yaoqiang.yq
 * @version $Id: EmailUtils.java, v 0.1 2015年12月12日 下午8:12:41 yaoqiang.yq Exp $
 */
public class EmailUtils {

    /**
     * 描述
     * 
     * @param asList
     * @param title
     * @param content
     * @throws Exception
     */
    public static void sendHtmlMail(List<String> emailAddress, String title,
                                    String content) throws Exception {
        for (String address : emailAddress) {
            HtmlEmail email = new HtmlEmail();
            email.setSmtpPort(587);
            email.setStartTLSEnabled(true);
            email.setHostName(SystemConfig.getConfig().get("blog.exception.email.hotname"));
            email.setAuthentication(SystemConfig.getConfig().get("blog.exception.email.name"),
                SystemConfig.getConfig().get("blog.exception.email.password"));
            email.setFrom(SystemConfig.getConfig().get("blog.exception.email.name"));

            email.setCharset("utf-8");// 解决中文乱码
            email.addTo(address);

            email.setSubject(title);
            // set the html message
            email.setHtmlMsg(content);
            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            email.send();
        }
    }
}
