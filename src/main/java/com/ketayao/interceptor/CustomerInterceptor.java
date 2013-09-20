package com.ketayao.interceptor;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ketayao.annotation.BlogAnnotation;
import com.ketayao.fensy.exception.ActionException;
import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.fensy.mvc.interceptor.InterceptorAdapter;
import com.ketayao.fensy.util.ResourceUtils;
import com.ketayao.pojo.User;
import com.ketayao.system.Constants;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年8月20日 上午10:38:54 
 */

public class CustomerInterceptor extends InterceptorAdapter {

	@Override
	public boolean preHandle(RequestContext rc, Object handler)
			throws Exception {
		User user = User.getLoginUser(rc);
		
		String url = rc.getURIAndExcludeContextPath();
		if (url.startsWith("/admin") && !url.startsWith("/admin/login")) {
			if (user == null || user.getRole() < IUser.ROLE_TOP) {
				rc.error(403);
				return false;
			}
		}
		
		Method methodOfAction = (Method)handler;
		if (methodOfAction.isAnnotationPresent(BlogAnnotation.UserRoleRequired.class)) {
			User loginUser = User.getLoginUser(rc); 
			if (loginUser == null) {
				//String this_page = rc.getParam(THIS_PAGE, "");
				//throw rc.error(code, msg);
				//.error("user_not_login", this_page);
				throw new ActionException("user not login");
			}
			if (loginUser.isBlocked()) {
				throw new ActionException("user is blocked");
			}

			BlogAnnotation.UserRoleRequired urr = (BlogAnnotation.UserRoleRequired) methodOfAction
					.getAnnotation(BlogAnnotation.UserRoleRequired.class);
			if (loginUser.getRole() < urr.role()) {// role越大，等级越高。
				throw new ActionException("user deny");
			}
		}
		
		Locale locale = rc.getLocale();
		ResourceBundle resourceBundle = ResourceUtils.getResourceBundle(locale, "messages");
		ResourceBundleModel rsbm = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		rc.setRequestAttr("bundle", rsbm);
		rc.setRequestAttr("rc", rc);
		
		rc.setRequestAttr(Constants.LOGIN_USER, user);
		return true;
	}
}
