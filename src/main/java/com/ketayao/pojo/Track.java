/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月25日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>	
 *
 * </pre>
 **/
 
package com.ketayao.pojo;

import java.io.Serializable;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月25日 上午9:46:00 
 */

public class Track implements Serializable {

	/** 描述  */
	private static final long serialVersionUID = 3257539763798744944L;
	

	private String location;
	
	private String creator;
	
	private String title;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
