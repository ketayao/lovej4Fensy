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
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.ketayao.fensy.db.POJO;
import com.ketayao.util.PageInfo;


/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-16 下午7:06:45 
 */
public class Link extends POJO {
	
	/** 描述  */
	private static final long serialVersionUID = -3042635639190903667L;

	public static final Link INSTANCE = new Link();
	
	private String name;
	private String site;
	private String description;
	private String status = Status.SHOW;
	private byte trash = 0;
	private Timestamp createTime;
	
	public final static class Status {
		private Status() {
		}

		public static final String SHOW = "show";

		public static final String HIDDEN = "hidden";
	}

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
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 返回 createTime 的值   
	 * @return createTime  
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**  
	 * 设置 createTime 的值  
	 * @param createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	 * @param attrName
	 * @param attrValue
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#updateAttr(java.lang.String, java.lang.Object)  
	 */
	@Override
	public boolean updateAttr(String attrName, Object attrValue) {
		boolean result = super.updateAttr(attrName, attrValue);
		if (result) {
			evictQueryCache();
		}
		return result;
	}

	/**   
	 * @param attrNames
	 * @param attrValues
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#updateAttrs(java.lang.String[], java.lang.Object[])  
	 */
	@Override
	public boolean updateAttrs(String[] attrNames, Object[] attrValues) {
		boolean result = super.updateAttrs(attrNames, attrValues);
		if (result) {
			evictQueryCache();
		}
		return result;
	}
	
	public void evictQueryCache() {
		evictCache(FIND_PAGE + 0);
		evictCache(FIND_PAGE + 1);
		evictCache(FIND_NEWEST + Link.Status.SHOW);
		evictCache(FIND_NEWEST + Link.Status.HIDDEN);
	}
	
	private static final String FIND_PAGE = "trash = ? ORDER BY id DESC"; 
	
	/**   
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#delete()  
	 */
	@Override
	public boolean delete() {
		boolean result = updateAttr("trash", 1);
		if (result) {
			evictQueryCache();
		}
		
		return result; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Link> findPage(PageInfo pageInfo, Boolean trash) {
		List<Long> ids = Link.INSTANCE.ids(FIND_PAGE, BooleanUtils.toInteger(trash));
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Link> list = Link.INSTANCE.loadList(returnIds);
		return list;
	}
	
	private static final String FIND_NEWEST = "trash = 0 AND status = ? ORDER BY id DESC"; 
	
	@SuppressWarnings("unchecked")
	public List<Link> findNewest(PageInfo pageInfo, String status) {
		List<Long> ids = Link.INSTANCE.ids(FIND_NEWEST, status);
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Link> list = Link.INSTANCE.loadList(returnIds);
		return list;
	}
}
