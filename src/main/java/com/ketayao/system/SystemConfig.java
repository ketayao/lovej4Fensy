/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.system.SystemConfig.java
 * Class:			SystemConfig
 * Date:			2011-11-14
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.system;

import java.util.HashMap;
import java.util.Map;

import com.ketayao.pojo.SiteConfig;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-14 下午3:07:51 
 */

public class SystemConfig {	
	private static SiteConfig siteConfig;
	
	private static final Map<String, String> config = new HashMap<String, String>();
	
	public static Map<String, String> EXTEND_TYPE = new HashMap<String, String>(); 
	
	public static String ROOT_DIR = "";
	
	static {
		config.put("lovej.md5.salt", "LoveJ");
		config.put("lovej.site.name", "LoveJ");
	}
	
	private SystemConfig(){}
	
	public static Map<String, String> getConfig() {
		return SystemConfig.config;
	}
	
	public static SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public static void setSiteConfig(SiteConfig siteConfig) {
		SystemConfig.siteConfig = siteConfig;
	}
}
