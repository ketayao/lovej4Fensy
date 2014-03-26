/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.entities.Comment.java
 * Class:			Comment
 * Date:			2011-11-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ketayao.fensy.db.POJO;
import com.ketayao.util.PageInfo;


/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-16 下午7:06:45 
 */
public class Comment extends POJO {

	/** 描述  */
	private static final long serialVersionUID = 5179755859279326481L;
	
	public static final Comment INSTANCE = new Comment();
	
	private String name;
	private String site;
	private String email;
	private String content;
	private Timestamp postTime;
	private String postIP;
	private long articleId;
	private Long userId;// 是否是作者发布
	private Long parentId;
	
	private Article article;
	private Comment parent;
	private List<Comment> children = new ArrayList<Comment>(0);
		
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
	 * 返回 site 的值   
	 * @return site  
	 */
	public String getSite() {
		return site;
	}

	/**  
	 * 设置 site 的值  
	 * @param site
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**  
	 * 返回 email 的值   
	 * @return email  
	 */
	public String getEmail() {
		return email;
	}

	/**  
	 * 设置 email 的值  
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**  
	 * 返回 content 的值   
	 * @return content  
	 */
	public String getContent() {
		return content;
	}

	/**  
	 * 设置 content 的值  
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**  
	 * 返回 postTime 的值   
	 * @return postTime  
	 */
	public Timestamp getPostTime() {
		return postTime;
	}

	/**  
	 * 设置 postTime 的值  
	 * @param postTime
	 */
	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

	/**  
	 * 返回 postIP 的值   
	 * @return postIP  
	 */
	public String getPostIP() {
		return postIP;
	}

	/**  
	 * 设置 postIP 的值  
	 * @param postIP
	 */
	public void setPostIP(String postIP) {
		this.postIP = postIP;
	}

	/**  
	 * 返回 articleId 的值   
	 * @return articleId  
	 */
	public long getArticleId() {
		return articleId;
	}
	
	/**  
	 * 返回 parentId 的值   
	 * @return parentId  
	 */
	public Long getParentId() {
		return parentId;
	}
	
	/**  
	 * 返回 userId 的值   
	 * @return userId  
	 */
	public Long getUserId() {
		return userId;
	}

	/**  
	 * 设置 userId 的值  
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**  
	 * 设置 parentId 的值  
	 * @param parentId
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**  
	 * 设置 articleId 的值  
	 * @param articleId
	 */
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	/**  
	 * 返回 article 的值   
	 * @return article  
	 */
	public Article getArticle() {
		if (articleId > 0) {
			article = Article.INSTANCE.get(articleId);
		}
		return article;
	}
	
	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public Comment getParent() {
		if (parentId != null && parentId > 0) {
			parent = get(parentId);
		}
		return parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<Comment> getChildren() {
		if (getId() < 1) {
			return new ArrayList<Comment>(0);
		}
		children = findByParentId(getId());
		return children;
	}
	
	public void evictQueryCache() {
		evictCache(ARTICLEIDS + this.getArticleId());
		evictCache(FIND_NEWEST);
		evictCache(CHILDREN_COMMENTS + parentId);
		evictCache(NO_PARENT_COMMENTS + this.getArticleId());
	}

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
	
	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#delete()  
	 */
	@Override
	public boolean delete() {
		boolean b = super.delete();
		if (b) {
			evictQueryCache();
		}
		
		return b;
	}
	
	private static final String ARTICLEIDS = "articleId = ? ORDER BY id ASC";
	private static final String FIND_NEWEST = "1=1 ORDER BY id DESC";
	private static final String CHILDREN_COMMENTS = "parentId = ? ORDER BY id ASC";
	private static final String NO_PARENT_COMMENTS = "parentId is null AND articleId = ? ORDER BY id ASC";
	
	@SuppressWarnings("unchecked")
	public List<Comment> find(long articleId) {
		List<Long> ids = getIds(ARTICLEIDS, articleId);
		return (List<Comment>)loadList(ids);
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findNewest(PageInfo pageInfo) {
		List<Long> ids = getIds(FIND_NEWEST);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Comment> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findPage(long articleId, PageInfo pageInfo) {
		List<Long> ids = getIds(ARTICLEIDS, articleId);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Comment> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findByParentId(long parentId) {
		List<Long> ids = getIds(CHILDREN_COMMENTS, parentId);
		
		List<Comment> list = loadList(ids);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findNoParent(long articleId, PageInfo pageInfo) {
		List<Long> ids = getIds(NO_PARENT_COMMENTS, articleId);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Comment> list = loadList(returnIds);
		return list;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#listInsertableFields()  
	 */
	@Override
	protected Map<String, Object> listInsertableFields() {
		Map<String, Object> map = super.listInsertableFields();
		//map.remove("");
		return map; 
	}
}
