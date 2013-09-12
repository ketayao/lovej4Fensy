/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.lucene.ConstructLucene.java
 * Class:			ConstructLucene
 * Date:			2013年9月4日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.lucene;

import java.io.IOException;
import java.util.List;

import com.ketayao.pojo.Article;
import com.ketayao.search.IndexHolder;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月4日 下午4:57:37 
 */

public class ConstructLucene {

	private static final String LUCENE_ARTICLE = "trash = 0 AND status = '" + Article.Status.PUBLISH + "'";
	//private static final String LUCENE_ARTICLE = "id=27";
	public static final String LUCENE_PATH = "D:\\fensy_lucene"; 
	
	/**  
	 * 描述
	 * @param args  
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		IndexHolder holder = IndexHolder.init(LUCENE_PATH);
		List<Article> articles = (List<Article>)Article.INSTANCE.list(LUCENE_ARTICLE);
		
		System.out.println(holder.add(articles) + "个索引被创建。");
	}

}
