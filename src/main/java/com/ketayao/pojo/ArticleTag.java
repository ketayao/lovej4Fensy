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

import java.util.List;

import com.ketayao.fensy.db.POJO;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月6日 下午11:02:23 
 */
public class ArticleTag extends POJO {

	/** 描述 */
	private static final long serialVersionUID = 1188130192728306225L;

	public static final ArticleTag INSTANCE = new ArticleTag();

	private long articleId;
	private long tagId;

	private Article article;
	private Tag tag;

	/**
	 * 返回 articleId 的值
	 * 
	 * @return articleId
	 */
	public long getArticleId() {
		return articleId;
	}

	/**
	 * 设置 articleId 的值
	 * 
	 * @param articleId
	 */
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	/**
	 * 返回 tagId 的值
	 * 
	 * @return tagId
	 */
	public long getTagId() {
		return tagId;
	}

	/**
	 * 设置 tagId 的值
	 * 
	 * @param tagId
	 */
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	/**
	 * 返回 article 的值
	 * 
	 * @return article
	 */
	public Article getArticle() {
		if (articleId > 0) {
			article = Article.INSTANCE.get(articleId);
		}
		return article;
	}

	/**
	 * 返回 tag 的值
	 * 
	 * @return tag
	 */
	public Tag getTag() {
		if (tagId > 0) {
			tag = Tag.INSTANCE.get(tagId);
		}
		return tag;
	}

	private static final String ARTICLEIDS = "articleId=? ORDER BY id DESC";
	private static final String BY_TAGID = "tagId=? ORDER BY id DESC";
	
	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#save()  
	 */
	@Override
	public long save() {
		long id = super.save();
		evictCache(ARTICLEIDS + this.getArticleId());
		return id;
	}
	
	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#delete()  
	 */
	@Override
	public boolean delete() {
		boolean b = super.delete();
		if (b) {
			if (articleId < 0) {
				Attach attach = get(getId());
				
				evictCache(ARTICLEIDS + attach.getArticleId());
			} else {
				evictCache(ARTICLEIDS + this.getArticleId());
			}
		}
		
		return b;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleTag> find(long articleId) {
		List<Long> ids = getIds(ARTICLEIDS, articleId);
		return (List<ArticleTag>)loadList(ids);
	}
	
	public List<Long> findByTagId(long tagId) {
		List<Long> articleIds = getKeys("articleId", BY_TAGID, tagId);
		return articleIds;
	}
}
