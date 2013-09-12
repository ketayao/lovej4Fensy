/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.context.Constants.java
 * Class:			Constants
 * Date:			2011-11-12
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.system;

import com.ketayao.fensy.util.CryptUtils;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-12 下午4:56:32 
 */

public interface Constants {
	public final static String ENCRYPT_MD5 = "MD5";
	
	public final static int SALT_LENGTH = 2 * CryptUtils.UNIT_LENGTH; 
	
	/**
	 * 站点配置
	 */
	public final static String SITE_CONFIG = "siteConfig";
	
	/**
	 * 系统配置
	 */
	public final static String SYSTEM_CONFIG = "systemConfig";
	
	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "user";

	/**
	 * Cookie
	 */
    public static final String COOKIE_REMEMBERME_USER_USERNAME = "lovej.cookie.rememberme";  
    
    public static final String COOKIE_REMEMBERME_USER_PASSWORD = "lovej.cookie.rememberme";
    
    //7天
    public static final int COOKIE_REMEMBERME_TIME = 60 * 60 * 24 * 7;
    
	/**
	 * 留言的访问者
	 */
    public static final String COOKIE_COMMENT_VISITOR_KEY = "lovej.cookie.visitor";
    
    /**
     * operation
     */
    public static final String OPERATION = "operation";
    
    public static final String OPERATION_SUCCESS = "success";
    
    public static final String OPERATION_FAILURE = "failure";
    
    /**
     * 评论用户
     */
    public static final String COMMENT_USER = "commentUser";
    
}
