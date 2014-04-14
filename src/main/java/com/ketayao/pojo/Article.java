/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.entities.Article.java
 * Class:			Article
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.ketayao.fensy.cache.CacheManager;
import com.ketayao.fensy.db.POJO;
import com.ketayao.search.Searchable;
import com.ketayao.util.PageInfo;
import com.ketayao.util.PreviewHtml;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-16 下午6:22:30 
 */
public class Article extends POJO implements Searchable {

	/** 描述  */
	private static final long serialVersionUID = 10167527442237723L;

	public static final Article INSTANCE = new Article();
	
	private static final String QUERY_CACHE = "ArticleQueryCache";
	
	private String title;
	private String content;
	private String keywords;
	private String summary;
	private String status = Status.PUBLISH;
	private int view = 0;
	private byte trash = 0;
	private Timestamp postTime;
	private Timestamp modifyTime;
	private Timestamp topTime;// 置顶时间
	private String permalink;// 永久链接地址，类似 /2012/09/02/自定义名
	private long categoryId;
	private long userId;
	private String imgUrl;
	
	// 排除字段
	private User user;
	private Category category;
	private List<ArticleTag> articleTags;
	private List<Comment> comments;
	private List<Attach> attaches;
	private String oldStatus;// 先前状态
	
	public static final class Status {
		private Status() {
		}
		
		public static final String PUBLISH = "publish";
		
		public static final String PRIVATE = "private";
		
		public static final String DRAFT = "draft";
		
		public static final String TRASH = "trash";
	}

	/**  
	 * 返回 title 的值   
	 * @return title  
	 */
	public String getTitle() {
		return title;
	}

	/**  
	 * 设置 title 的值  
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * 返回 keywords 的值   
	 * @return keywords  
	 */
	public String getKeywords() {
		return keywords;
	}

	/**  
	 * 设置 keywords 的值  
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**  
	 * 返回 summary 的值   
	 * @return summary  
	 */
	public String getSummary() {
		return summary;
	}

	/**  
	 * 设置 summary 的值  
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**  
	 * 返回 status 的值   
	 * @return status  
	 */
	public String getStatus() {
		return status;
	}

	/**  
	 * 设置 status 的值  
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 * 返回 view 的值   
	 * @return view  
	 */
	public int getView() {
		return view;
	}

	/**  
	 * 设置 view 的值  
	 * @param view
	 */
	public void setView(int view) {
		this.view = view;
	}

	/**  
	 * 返回 trash 的值   
	 * @return trash  
	 */
	public byte getTrash() {
		return trash;
	}

	/**  
	 * 设置 trash 的值  
	 * @param trash
	 */
	public void setTrash(byte trash) {
		this.trash = trash;
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
	 * 返回 modifyTime 的值   
	 * @return modifyTime  
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	/**  
	 * 设置 modifyTime 的值  
	 * @param modifyTime
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**  
	 * 返回 category 的值   
	 * @return category  
	 */
	public Category getCategory() {
		if (categoryId > 0) {
			category = Category.INSTANCE.get(categoryId);
		}
		return category;
	}

	/**  
	 * 返回 categoryId 的值   
	 * @return categoryId  
	 */
	public long getCategoryId() {
		return categoryId;
	}

	/**  
	 * 设置 categoryId 的值  
	 * @param categoryId
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	/**  
	 * 返回 topTime 的值   
	 * @return topTime  
	 */
	public Timestamp getTopTime() {
		return topTime;
	}

	/**  
	 * 设置 topTime 的值  
	 * @param topTime
	 */
	public void setTopTime(Timestamp topTime) {
		this.topTime = topTime;
	}

	/**  
	 * 返回 comments 的值   
	 * @return comments  
	 */
	public List<Comment> getComments() {
		comments = Comment.INSTANCE.find(getId());
		return comments;
	}

	/**  
	 * 返回 attachs 的值   
	 * @return attachs  
	 */
	public List<Attach> getAttaches() {
		attaches = Attach.INSTANCE.find(getId());
		return attaches;
	}

	/**  
	 * 返回 permalink 的值   
	 * @return permalink  
	 */
	public String getPermalink() {
		return permalink;
	}

	/**  
	 * 设置 permalink 的值  
	 * @param permalink
	 */
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	/**  
	 * 返回 userId 的值   
	 * @return userId  
	 */
	public long getUserId() {
		return userId;
	}

	/**  
	 * 设置 userId 的值  
	 * @param userId
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**  
	 * 返回 oldStatus 的值   
	 * @return oldStatus  
	 */
	public String getOldStatus() {
		return oldStatus;
	}

	/**  
	 * 设置 oldStatus 的值  
	 * @param oldStatus
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public User getUser() {
		if (userId > 0) {
			user = User.INSTANCE.get(userId);
		}
		return user;
	}
	
	/**  
	 * 返回 articleTags 的值   
	 * @return articleTags  
	 */
	public List<ArticleTag> getArticleTags() {
		if (articleTags == null) {
			articleTags = ArticleTag.INSTANCE.find(getId());
		}
		return articleTags;
	}

	private static final String ARTICLEIDS = "userId = ? AND categoryId = ? AND trash = 0 ORDER BY topTime DESC, id DESC";
	private static final String ARTICLEIDS2 = "userId = ? AND trash = ? ORDER BY topTime DESC, id DESC";
	private static final String HOME_ARTICLEIDS = "trash = 0 AND status = '" + Article.Status.PUBLISH + "' ORDER BY topTime DESC, id DESC";
	private static final String FIND_NEWEST = "trash = 0 AND status = '" + Article.Status.PUBLISH + "' ORDER BY id DESC";

	/**   
	 * @param attrName
	 * @param attrValue
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#getByAttr(java.lang.String, java.lang.Object)  
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T extends POJO> T getByAttr(String attrName, Object attrValue) {
//		Object object = CacheManager.get(queryCacheRegion(), attrName + ":" + attrName);
//		if (object == null) {
//			object = super.getByAttr(attrName, attrValue);
//			CacheManager.set(queryCacheRegion(), (Serializable)(attrName + ":" + attrName), (Serializable)object);
//		}
//		
//		return (T) object;
//	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#save()  
	 */
	@Override
	public long save() {
		long id = super.save();
		CacheManager.clear(QUERY_CACHE);// 清除所有的查询缓存
		return id;
	}

	/**   
	 * @param attrName
	 * @param attrValue
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#updateAttr(java.lang.String, java.lang.Object)  
	 */
	@Override
	public boolean updateAttr(String attrName, Object attrValue) {
		boolean b = super.updateAttr(attrName, attrValue);
		if (b && oldStatus != null &&!oldStatus.equals(status)) {
			CacheManager.clear(QUERY_CACHE);// 清除所有的查询缓存
		}
		return b; 
	}

	/**   
	 * @param attrNames
	 * @param attrValues
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#updateAttrs(java.lang.String[], java.lang.Object[])  
	 */
	@Override
	public boolean updateAttrs(String[] attrNames, Object[] attrValues) {
		boolean b = super.updateAttrs(attrNames, attrValues);
		if (b && oldStatus != null &&!oldStatus.equals(status)) {
			CacheManager.clear(QUERY_CACHE);// 清除所有的查询缓存
		}
		return b; 
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#delete()  
	 */
	@Override
	public boolean delete() {
		boolean result = updateAttr("trash", 1);
		if (result) {
			evictCache(true);
			evictCache(OBJ_COUNT_CACHE_KEY);
			
			CacheManager.clear(QUERY_CACHE);// 清除所有的查询缓存
		}
		
		return result; 
	}

	/**
	 * 查询Article
	 * 描述
	 * @param userId
	 * @param categoryId
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Article> findPage(long userId, long categoryId, PageInfo pageInfo) {
		List<Long> ids = getIds(ARTICLEIDS, 
				userId, categoryId);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	/**
	 * 查询Article
	 * 描述
	 * @param userId
	 * @param trash
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Article> findPage(long userId, Boolean trash, PageInfo pageInfo) {
		List<Long> ids = getIds(ARTICLEIDS2, userId, BooleanUtils.toInteger(trash));
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	/**
	 * 首页显示的Article
	 * 描述
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Article> findHome(PageInfo pageInfo) {
		List<Long> ids = getIds(HOME_ARTICLEIDS);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	/**
	 * 查询最新的Article
	 * 描述
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Article> findNewest(PageInfo pageInfo) {
		List<Long> ids = getIds(FIND_NEWEST);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findHotest(PageInfo pageInfo) {
		List<Long> ids = getIds("trash = 0 AND status = '" + Article.Status.PUBLISH + "' ORDER BY view DESC");
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findTitleImages(PageInfo pageInfo) {
		List<Long> ids = getIds("imgUrl is not null AND trash = 0 AND status = '" + Article.Status.PUBLISH + "' ORDER BY id DESC");
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findByTagId(long tagId, PageInfo pageInfo) {
		List<Long> articleIds = ArticleTag.INSTANCE.findByTagId(tagId);
		
		StringBuilder sb = new StringBuilder();
		for (Long id : articleIds) {
			sb.append(id + ",");
		}
		String idString = null;
		if (sb.length() > 1) {
			idString = sb.substring(0, sb.length() - 1).toString();
		}  else {
			return new ArrayList<Article>(0);
		}
		
		List<Long> ids = getIds("trash = 0 AND status = '" + Article.Status.PUBLISH + "' AND id in (?) ORDER BY id DESC", idString);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findByUserId(long userId, PageInfo pageInfo) {
		List<Long> ids = getIds("trash = 0 AND status = '" + Article.Status.PUBLISH + "' AND userId = ? ORDER BY id DESC", userId);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findByMonth(Timestamp startTime, PageInfo pageInfo) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);

		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		
		Timestamp endTime = new Timestamp(calendar.getTime().getTime());
		
		List<Long> ids = getIds("trash = 0 AND status = '" + Article.Status.PUBLISH + "' AND postTime BETWEEN ? AND ? ORDER BY id DESC", startTime, endTime);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	/**
	 * 
	 * 描述
	 * @param categoryIds
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Article> findExtendCategory(long categoryId, PageInfo pageInfo) {
		String sql = "categoryId = ? AND trash = 0 AND status = '" + Article.Status.PUBLISH + "' ORDER BY topTime DESC, id DESC";
		
		List<Long> ids = getIds(sql, categoryId);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findExtendPage(List<Long> categoryIds, PageInfo pageInfo) {
		String ids = "";
		for (int i = 0; i < categoryIds.size(); i++) {
			ids += categoryIds.get(i) + ",";
		}
		ids = ids.substring(0, ids.length() - 1);

		String sql = "categoryId in (" + ids + ") AND trash = 0 AND status = '" + Article.Status.PUBLISH + "' ORDER BY topTime DESC, id DESC";

		List<Long> idList = getIds(sql);
		pageInfo.setTotalRec(idList.size());
		List<Long> returnIds = idList.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> search(String title, PageInfo pageInfo) {
		List<Long> ids = getIds("trash = 0 AND status = '" + Article.Status.PUBLISH + "' AND title like ? ORDER BY id DESC", "%" + title + "%");
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Article> list = loadList(returnIds);
		return list;
	}
	
	/*
	 * 前一页article
	 * @return
	 */
	public Article pre() {
		List<Long> ids = getIds(" id < ? AND trash = ? AND status = ? ORDER BY id DESC LIMIT 1", getId(), 0, Article.Status.PUBLISH);
		if (ids.isEmpty()) {
			return null;
		}
		Long id = ids.get(0);
		return get(id);
	}
	
	/**
	 * 后一页article
	 * @return
	 */
	public Article next() {
		List<Long> ids = getIds(" id > ? AND trash = ? AND status = ? ORDER BY id LIMIT 1", getId(), 0, Article.Status.PUBLISH);
		if (ids.isEmpty()) {
			return null;
		}
		Long id = ids.get(0);
		return get(id);
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.db.POJO#listInsertableFields()  
	 */
	@Override
	protected Map<String, Object> listInsertableFields() {
		Map<String, Object> map = super.listInsertableFields();
		map.remove("oldStatus");
		map.remove("preview");
		return map;
	}

	@Override
	protected String getQueryCacheRegion() {
		return QUERY_CACHE;
	}

	/**   
	 * @param o
	 * @return  
	 * @see java.lang.Comparable#compareTo(java.lang.Object)  
	 */
	@Override
	public int compareTo(Searchable o) {
		return 0;
	}

	/**   
	 * @return  
	 * @see net.oschina.common.search.Searchable#storeFields()  
	 */
	@Override
	public List<String> storeFields() {
		return Arrays.asList("postTime");//用作排序
	}

	/**   
	 * @return  
	 * @see net.oschina.common.search.Searchable#indexFields()  
	 */
	@Override
	public List<String> indexFields() {
		return Arrays.asList("title","content");
	}

	/**   
	 * @return  
	 * @see net.oschina.common.search.Searchable#boost()  
	 */
	@Override
	public float boost() {
		return 1.0f;
	}

	/**   
	 * @return  
	 * @see net.oschina.common.search.Searchable#extendStoreDatas()  
	 */
	@Override
	public Map<String, String> extendStoreDatas() {
		return null;
	}

	/**   
	 * @return  
	 * @see net.oschina.common.search.Searchable#extendIndexDatas()  
	 */
	@Override
	public Map<String, String> extendIndexDatas() {
		return null;
	}

	/**   
	 * @param id
	 * @param count
	 * @return  
	 * @see net.oschina.common.search.Searchable#listAfter(long, int)  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends Searchable> listAfter(long id, int count) {
		return (List<Article>)Article.INSTANCE.list("trash = 0 AND status = '" + Article.Status.PUBLISH + "' AND id > " + id);
	}
	
	private String preview; 
	
	public void setPreview(String preview) {
		this.preview = preview;
	}
	
	public String getPreview() {
		if (StringUtils.isNotBlank(summary)) {
			this.preview = summary;
		}
		if (preview == null) {
			this.preview = PreviewHtml.truncateHTML(content, 200); 
		}
		return preview;
	}
	
}
