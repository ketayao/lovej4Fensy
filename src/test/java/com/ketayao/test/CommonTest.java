/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.CommonTest.java
 * Class:			CommonTest
 * Date:			2013年8月21日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.junit.Test;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月21日 上午11:22:04 
 */

public class CommonTest {
	@Test
	public void testSplit() {
		
		//String temp = "java, kkfd, asdasd   ,sf";
		//String temp = "java";
		//String temp = "java, kkfd, ,sf";
		String temp = "java, kkfd, ,sf";
		System.out.println(Arrays.toString(com.ketayao.util.StringUtils.split(temp, ",")));
	}
	
	@Test
	public void testutf8() throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("?","utf-8"));//%3F
		System.out.println(URLEncoder.encode("&","utf-8"));//%26
	}
}
