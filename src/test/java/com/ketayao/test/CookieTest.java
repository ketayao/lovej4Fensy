/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.CookieTest.java
 * Class:			CookieTest
 * Date:			2013年8月29日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.ketayao.pojo.Comment;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月29日 下午4:38:11 
 */

public class CookieTest {
	@Test
	public void test() {
		Comment comment = new Comment();
		String cookieValue = "||";
		String[] items = cookieValue.split("\\|"); 
				//StringUtils.split(cookieValue, '|');
		
		
		
		//if (items.length == 3) {
			String temp = StringUtils.substringBefore(cookieValue, "|");
			temp = StringUtils.substringBetween(cookieValue, "|");
			temp = StringUtils.substringAfterLast(cookieValue, "|");
			comment.setName(temp);
			comment.setEmail(items[1]);
			comment.setSite(items[2]);
		//}
	}
}
