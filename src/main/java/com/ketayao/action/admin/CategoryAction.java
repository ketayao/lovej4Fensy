/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月13日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action.admin;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.Category;
import com.ketayao.system.Constants;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月13日 上午11:37:12 
 */
public class CategoryAction {
	
	private static final String CREATE = "admin/content/category-create";
	private static final String READ = "admin/content/category-read";
	private static final String UPDATE = "admin/content/category-update";
	private static final String VIEW = "admin/content/category-view";
	
	public String read(WebContext rc) {
		List<Category> parents = Category.INSTANCE.findTree(false);
		rc.getRequest().setAttribute("parents", parents);
		return READ;
	}
	
	public String preCreate(WebContext rc) {
		List<Category> parents = Category.INSTANCE.findParent(false);
		rc.getRequest().setAttribute("parents", parents);
		return CREATE;
	}
	
	public String create(WebContext rc) throws Exception {
		Category category = new Category();
		BeanUtils.populate(category, rc.getRequest().getParameterMap());
		
		// 避免页面目录传递父id
		if (category.getType().equals(Category.Type.PAGE)) {
			category.setParentId(null);
		}

		category.setCreateTime(new Timestamp(System.currentTimeMillis()));
		category.save();
		
		rc.getRequest().setAttribute(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		return preCreate(rc);
	}
	
	public String delete(WebContext rc, String[] args) throws Exception {
		Category category = new Category();
		category.setId(NumberUtils.toLong(args[0]));
		category.delete();
		
		return read(rc);
	}
	
	public String view(WebContext rc, String[] args) {
		Category category = Category.INSTANCE.get(NumberUtils.toLong(args[0]));
		rc.getRequest().setAttribute("category", category);
		return VIEW;
	}
	
	public String preUpdate(WebContext rc, String[] args) {
		view(rc, args);
		return UPDATE;
	}
	
	public String update(WebContext rc) throws IllegalAccessException, InvocationTargetException {
		Category category = Category.INSTANCE.get(rc.getId());
		BeanUtils.populate(category, rc.getRequest().getParameterMap());
		
		category.updateAttrs(new String[]{"name", "priority", "description"}, 
				new Object[]{category.getName(), category.getPriority(), category.getDescription()});
		rc.getRequest().setAttribute(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		rc.getRequest().setAttribute("category", category);
		return UPDATE;
	}
}
