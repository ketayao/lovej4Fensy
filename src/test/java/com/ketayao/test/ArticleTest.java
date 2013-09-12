/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.ArticleTest.java
 * Class:			ArticleTest
 * Date:			2013年9月5日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.ketayao.pojo.Article;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月5日 上午11:20:16 
 */

public class ArticleTest {
	private static int scheduleTime =  1000*60*5; // 五分钟
	@Test
	public void test() {
		long now = System.currentTimeMillis();
		long before = now - scheduleTime;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "modifyTime > '" + dateFormat.format(new Date(before)) + "'";
		List<Article> addArticles = (List<Article>)Article.INSTANCE
				.list(sql);
		System.out.println(addArticles.size());
	}
}
