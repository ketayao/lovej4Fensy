/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.admin.TagAction.java
 * Class:			TagAction
 * Date:			2013年8月21日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action.admin;

import java.io.IOException;
import java.util.List;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.pojo.Tag;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月21日 上午10:45:25 
 */

public class TagAction {
	
	@SuppressWarnings("unchecked")
	public void tags(RequestContext rc) throws IOException {
		List<Tag> tags = (List<Tag>)Tag.INSTANCE.list();
		
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (Tag tag : tags) {
			builder.append("'" + tag.getTitle() + "',");
		}
		
		if (builder.length() > 1) {
			builder.substring(0, builder.length() - 1);
		}
		
		builder.append("]");
		rc.print(builder.toString());
	}
}
