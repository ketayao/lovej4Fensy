/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月5日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action.admin;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.util.CryptUtils;
import com.ketayao.fensy.webutil.ImageCaptchaService;
import com.ketayao.pojo.User;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月5日 下午4:38:12 
 */
public class LoginAction {
    private static final String LOGIN = "admin/login";

    public String index(WebContext rc) {
        User user = User.getLoginUser(rc);
        if (user != null && user.getRole() >= IUser.ROLE_TOP) {
            return "redirect:" + rc.getContextPath() + "/admin/index";
        }

        return LOGIN;
    }

    public String login(WebContext rc) throws Exception {
        String username = rc.getParam("username");
        String password = rc.getParam("password");
        String captcha = rc.getParam("verifyCode");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
            || StringUtils.isBlank(captcha)) {
            rc.setRequestAttr("msg", "输入项不能为空");
            return LOGIN;
        }

        boolean correct = ImageCaptchaService.validate(rc.getRequest());
        if (!correct) {
            rc.setRequestAttr("msg", "验证码错误");
            return LOGIN;
        }

        User user = User.INSTANCE.getByAttr("username", username);
        if (user == null) {
            rc.setRequestAttr("msg", "NotFoundUserException");
            return LOGIN;
        }

        String dbPwd = CryptUtils.decrypt(user.getPassword(), user.getSalt());
        if (!password.equals(dbPwd)) {
            rc.setRequestAttr("msg", "NotMatchUserPasswordException");
            return LOGIN;
        }

        if (user.isBlocked()) {
            rc.setRequestAttr("msg", "UserIsFrozenException");
            return LOGIN;
        }

        rc.saveUserInCookie(user, false);
        return "redirect:" + rc.getContextPath() + "/admin/index";
    }

    public String logout(WebContext rc) throws Exception {
        HttpSession session = rc.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        rc.deleteUserFromCookie();
        return "redirect:" + rc.getContextPath() + "/admin/login";
    }

}
