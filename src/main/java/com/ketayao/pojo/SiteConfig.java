/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.entities.SiteConfig.java
 * Class:			SiteConfig
 * Date:			2011-12-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.pojo;

import com.ketayao.fensy.db.POJO;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-12-5 下午12:37:33 
 */

public class SiteConfig extends POJO {

	/** 描述  */
	private static final long serialVersionUID = -4855964431310615273L;
	
	public static final SiteConfig INSTANCE = new SiteConfig();

	private String name;
	private String icp;
	private String contactDescription;
	private String url;
	private String about;
	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}
	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**  
	 * 返回 icp 的值   
	 * @return icp  
	 */
	public String getIcp() {
		return icp;
	}
	/**  
	 * 设置 icp 的值  
	 * @param icp
	 */
	public void setIcp(String icp) {
		this.icp = icp;
	}
	/**  
	 * 返回 contactDescription 的值   
	 * @return contactDescription  
	 */
	public String getContactDescription() {
		return contactDescription;
	}
	/**  
	 * 设置 contactDescription 的值  
	 * @param contactDescription
	 */
	public void setContactDescription(String contactDescription) {
		this.contactDescription = contactDescription;
	}
	/**  
	 * 返回 url 的值   
	 * @return url  
	 */
	public String getUrl() {
		return url;
	}
	/**  
	 * 设置 url 的值  
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**  
	 * 返回 about 的值   
	 * @return about  
	 */
	public String getAbout() {
		return about;
	}
	/**  
	 * 设置 about 的值  
	 * @param about
	 */
	public void setAbout(String about) {
		this.about = about;
	}

}
