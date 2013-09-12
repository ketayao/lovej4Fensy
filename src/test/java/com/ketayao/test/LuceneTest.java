/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.LuceneTest.java
 * Class:			LuceneTest
 * Date:			2013年9月4日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.test;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.junit.Test;

import com.ketayao.pojo.Article;
import com.ketayao.search.IndexHolder;
import com.ketayao.search.SearchHelper;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月4日 下午5:15:18 
 */

public class LuceneTest {
	private static final String LUCENE_PATH = "D:\\fensy_lucene"; 
	private static final String LUCENE_KEYWORD = "DWZ";
	private static final String LUCENE_FIELD = "title";
	
	@Test
	public void test() throws IOException {
		IndexHolder indexHolder = IndexHolder.init(LUCENE_PATH);
		
		Query query = SearchHelper.makeQuery(LUCENE_FIELD, LUCENE_KEYWORD, 1.0f);
		
		List<Long> articleIds = indexHolder.find(Article.class, query, null, new Sort(), 1, 10);
		
		
		List<Article> articles = Article.INSTANCE.loadList(articleIds);
		System.out.println("articles.size=" + articles.size());
		for (Article article : articles) {
			System.out.println(SearchHelper.highlight(article.getTitle(), LUCENE_KEYWORD));
			System.out.println(SearchHelper.highlight(article.getContent(), LUCENE_KEYWORD));
		}
	}
	
	@Test
	public void testSearch() throws IOException {
		IndexHolder indexHolder = IndexHolder.init(LUCENE_PATH);
		
		//TermQuery query = new TermQuery(new Term("title", "DWZ"));
		
		Query query = SearchHelper.makeQuery(LUCENE_FIELD, LUCENE_KEYWORD, 1.0f);
		
		List<Long> articleIds = indexHolder.find(Article.class, query, null,
				new Sort(new SortField("postTime", SortField.Type.LONG, true)), 1, 10);
		
		
		List<Article> articles = Article.INSTANCE.loadList(articleIds);
		System.out.println("articles.size=" + articles.size());
		for (Article article : articles) {
			System.out.println(SearchHelper.highlight(article.getTitle(), LUCENE_KEYWORD));
			//System.out.println(SearchHelper.highlight(article.getContent(), LUCENE_KEYWORD));
		}
	}
}
