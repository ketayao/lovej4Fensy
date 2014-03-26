/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年7月27日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action.admin;

import java.io.IOException;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.util.CryptUtils;
import com.ketayao.pojo.User;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年7月27日 上午11:54:57 
 */
public class UserAction {
	private static final String INDEX = "admin/user/user";
	private static final String PWD = "admin/user/password";
	
	public String index() {
		return INDEX;
	}
	
//	public void register(RequestContext rc) throws Exception {
//		User user = new User();
//		BeanUtils.populate(user, rc.getRequest().getParameterMap());
//
//		user.setNickname(user.getUsername());
//		user.setRole(IUser.ROLE_TOP);
//		user.setSalt(RandomStringUtils.randomAlphanumeric(Constants.SALT_LENGTH));
//		user.setPassword(CryptUtils.encrypt(user.getPassword(), user.getSalt()));
//
//		user.save();
//	}
	
	/**
	 * 检查username是否已被使用
	 * @param rc
	 * @throws IOException
	 */
	public void checkUser(WebContext rc) throws IOException {
		User user = User.INSTANCE.getByAttr("username", rc.getParam("username"));
		if (user == null) {
			rc.print("true");
		} else {
			rc.print("false");
		}
	}
	
	public String info(WebContext rc) throws Exception {
		User user = User.getLoginUser(rc);
		
		user.updateAttrs(new String[] { "nickname", "email", "frozen", "role"}, 
				new Object[] {rc.getParam("nickname"), rc.getParam("email"), (byte)rc.getParam("frozen", 0), (byte)rc.getParam("role", 0)});
		user.setNickname(rc.getParam("nickname"));
		user.setEmail(rc.getParam("email"));
		user.setFrozen((byte)rc.getParam("frozen", 0));
		user.setRole((byte)rc.getParam("role", 0));
				
		return "redirect:" + rc.getContextPath() + "/admin/user?success=true";
	}
	
	public String pwd() {
		return PWD;
	}
	
	public String modifyPwd(WebContext rc) throws Exception {
		User user = User.getLoginUser(rc);
		
		String oldPassword = rc.getParam("oldPassword");
		String newPassword = rc.getParam("newPassword");
		
		if (user.getPassword().equals(CryptUtils.encrypt(oldPassword, user.getSalt()))) {
			String pwd = CryptUtils.encrypt(newPassword, user.getSalt());
			user.updateAttr("password", pwd);
			user.setPassword(pwd);
			
			rc.saveUserInCookie(user, false);
			
			return "redirect:" + rc.getContextPath() + "/admin/user/pwd?success=true";
		} else {
			return "redirect:" + rc.getContextPath() + "/admin/user/pwd?success=false";
		}
	}
	
}
