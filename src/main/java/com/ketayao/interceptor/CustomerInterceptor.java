package com.ketayao.interceptor;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ketayao.annotation.RolePermission;
import com.ketayao.exception.PermissionException;
import com.ketayao.fensy.mvc.WebContext;
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
    public boolean preHandle(WebContext rc, Object handler) throws Exception {
        User user = User.getLoginUser(rc);

        String url = rc.getURIAndExcludeContextPath();
        if (url.startsWith("/admin")) {
            if (!url.startsWith("/admin/login") && !url.startsWith("/admin/forget")) {
                if (user == null) {
                    rc.redirect(rc.getContextPath() + "/admin/login");
                    return false;
                }

                if (user.getRole() < RolePermission.ROLE_BACKGROUND) {
                    error(rc, null);
                    return false;
                }
            }
        }

        Method methodOfAction = (Method) handler;
        if (methodOfAction.isAnnotationPresent(RolePermission.class)) {
            User loginUser = User.getLoginUser(rc);
            if (loginUser == null) {
                //String this_page = rc.getParam(THIS_PAGE, "");
                //throw rc.error(code, msg);
                //.error("user_not_login", this_page);
                error(rc, "user not login");
                return false;
            }
            if (loginUser.isBlocked()) {
                error(rc, "user is blocked");
                return false;
            }

            RolePermission urr = (RolePermission) methodOfAction
                .getAnnotation(RolePermission.class);
            if (loginUser.getRole() < urr.role()) {// role越大，等级越高。
                error(rc, "user deny");
                return false;
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

    /** 
     * @see com.ketayao.fensy.mvc.interceptor.InterceptorAdapter#afterCompletion(com.ketayao.fensy.mvc.WebContext, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(WebContext rc, Object handler, Exception ex) throws Exception {
        if (ex instanceof PermissionException) {
            error(rc, ex.getMessage());
        }
    }

    private void error(WebContext rc, String error) throws Exception {
        if (error != null) {
            rc.getContext().setAttribute("error", "(" + error + ")");
        }
        rc.error(403);
    }
}
