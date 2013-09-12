/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月6日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ketayao.fensy.db.POJO;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月6日 下午10:53:10 
 */
public class Tag extends POJO {

	/** 描述  */
	private static final long serialVersionUID = 7174592883621032970L;
	
	public static final Tag INSTANCE = new Tag();
	
	private String title;
	
	private List<ArticleTag> articleTags = new ArrayList<ArticleTag>(0);

	/**
	 * 返回 title 的值
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置 title 的值
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**  
	 * 返回 articleTags 的值   
	 * @return articleTags  
	 */
	public List<ArticleTag> getArticleTags() {
		return articleTags;
	}

	/**  
	 * 设置 articleTags 的值  
	 * @param articleTags
	 */
	public void setArticleTags(List<ArticleTag> articleTags) {
		this.articleTags = articleTags;
	}
	
	private final static String queryCacheListKey = "queryCacheList()";
	
	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#save()  
	 */
	@Override
	public long save() {
		long id = super.save();
		evictQueryCache();
		return id;
	}

	@SuppressWarnings("unchecked")
	public List<Tag> queryCacheList() {
		List<Tag> tags  = (List<Tag>)getCache(queryCacheListKey);
		if (tags != null) {
			return tags;
		}
		
		tags = (List<Tag>)list();
		setCache(queryCacheListKey, (Serializable)tags);
		return tags; 
	}
	
	public void evictQueryCache() {
		evictCache(queryCacheListKey);
	}
}
