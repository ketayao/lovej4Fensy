/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.entities.Category.java
 * Class:			Category
 * Date:			2011-11-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

import com.ketayao.fensy.db.POJO;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-16 下午7:26:15 
 */
public class Category extends POJO {

	/** 描述  */
	private static final long serialVersionUID = -2092367630196021947L;
	
	public static final Category INSTANCE = new Category();

	private String name;
	private String description;
	private Integer priority = 99;
	private Timestamp createTime;
	private byte trash = 0;
	private String type;
	private Long parentId;	// 冗余属性，方便树形查找
	
	private Category parent;
	private List<Category> children = new ArrayList<Category>(0);
	private List<Article> articles = new ArrayList<Article>(0);
	
	public static final class Type {
		private Type() {
		}
		
		public static final String POST = "post";
		
		public static final String PAGE = "page";
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
	 * 返回 priority 的值   
	 * @return priority  
	 */
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
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
	 * 返回 type 的值   
	 * @return type  
	 */
	public String getType() {
		return type;
	}

	/**  
	 * 设置 type 的值  
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public Category getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 parentId 的值   
	 * @return parentId  
	 */
	public Long getParentId() {
		return parentId;
	}

	/**  
	 * 设置 parentId 的值  
	 * @param parentId
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<Category> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<Category> children) {
		this.children = children;
	}

	/**  
	 * 返回 articles 的值   
	 * @return articles  
	 */
	public List<Article> getArticles() {
		return articles;
	}

	/**  
	 * 设置 articles 的值  
	 * @param articles
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
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
	 * @see com.ketayao.fensy.bean.POJO#delete()  
	 */
	@Override
	public boolean delete() {
		boolean result = updateAttr("trash", 1);
		if (result) {
			evictCache(true);
			evictCache(OBJ_COUNT_CACHE_KEY);
			evictQueryCache();
		}
		
		return result; 
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

	/**   
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#listInsertableFields()  
	 */
	@Override
	protected Map<String, Object> listInsertableFields() {
		Map<String, Object> props = super.listInsertableFields();
		props.remove("articles");
		props.remove("parent");
		props.remove("children");
		return props;
	}
	
	public void evictQueryCache() {
		evictCache("findParent(true)");
		evictCache("findParent(false)");
		evictCache("findTree(true)");
		evictCache("findTree(false)");
	}

	@SuppressWarnings("unchecked")
	public List<Category> findParent(Boolean trash) {
		String key = "findParent(" + trash + ")";
		List<Category> list = (List<Category>)getCache(key);
		if (list != null) {
			return list;
		}
		list = (List<Category>)this.list("parentId IS NULL AND trash=" + BooleanUtils.toInteger(trash));
		Collections.sort(list, new CategorySort());
		
		putCache(key, (Serializable)list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findByType(String type) {
		String key = "findParent(" + trash + ")";
		List<Category> list = (List<Category>)getCache(key);
		if (list != null) {
			return list;
		}
		list = (List<Category>)this.list("parentId IS NULL AND trash=" + trash);
		Collections.sort(list, new CategorySort());
		
		putCache(key, (Serializable)list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findTree(Boolean trash) {
		String key = "findTree(" + trash + ")";
		List<Category> list = (List<Category>)getCache(key);
		if (list != null) {
			return list;
		}
		
		list = (List<Category>)this.list("trash=" + BooleanUtils.toInteger(trash));

		List<Category> parents = new ArrayList<Category>();
		List<Category> children = new ArrayList<Category>();
		for (Iterator<Category> iterator = list.iterator(); iterator.hasNext();) {
			Category category = iterator.next();
			if (category.getParentId() == null) {
				parents.add(category);
			} else {
				children.add(category);
			}
		}

		for (Iterator<Category> iterator = parents.iterator(); iterator
				.hasNext();) {
			Category p = iterator.next();
			for (Iterator<Category> iterator2 = children.iterator(); iterator2
					.hasNext();) {
				Category c = iterator2.next();
				if (p.getId() == c.getParentId().longValue()) {
					p.getChildren().add(c);
				}
			}
			Collections.sort(p.getChildren(), new CategorySort());
		}
		Collections.sort(parents, new CategorySort());

		putCache(key, (Serializable)parents);
		return parents;
	}
	
	/**
	 * category排序类
	 * 
	 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.0.0
	 * @created 2011-11-25 下午2:02:08
	 */
	private class CategorySort implements Comparator<Category> {

		/**
		 * @param o1
		 * @param o2
		 * @return
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Category o1, Category o2) {
			if (o1 == o2) {
				return 0;
			}

			if (o1.getPriority() == o2.getPriority()) {
				if (o1.getId() > o2.getId()) {
					return 1;
				}
			} else {
				if (o1.getPriority() > o2.getPriority()) {
					return 1;
				}
			}

			return -1;
		}
	}
}
