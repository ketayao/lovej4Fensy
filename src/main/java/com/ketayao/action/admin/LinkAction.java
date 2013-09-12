/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.admin.LinkAction.java
 * Class:			LinkAction
 * Date:			2013年8月24日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action.admin;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.pojo.Link;
import com.ketayao.system.Constants;
import com.ketayao.util.PageInfo;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月24日 上午10:33:56 
 */

public class LinkAction {

	private final static String CREATE = "admin/contact/link-create";
	private final static String READ = "admin/contact/link-read";
	private final static String UPDATE = "admin/contact/link-update";
	//private final static String DELETE = "admin/contact/link-read";
	
	public String pc() {
		return CREATE;
	}
	
	public String c(RequestContext rc) throws IllegalAccessException, InvocationTargetException {
		Link link = new Link();
		BeanUtils.populate(link, rc.getParameterMap());
		
		if (link != null && link.getName() != null) {
			link.setCreateTime(new Timestamp(System.currentTimeMillis()));
			link.save();
			
			rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		}
		
		return CREATE;
	}
	
	/**
	 * 
	 * 当参数地址相同时pageInfo不用rc.setRequestAttr,但是严谨性,请将所需参数手动加入map
	 * @param map
	 * @param pageInfo
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public String r(RequestContext rc) throws IllegalAccessException, InvocationTargetException {
		PageInfo pageInfo = new PageInfo();
		BeanUtils.populate(pageInfo, rc.getParameterMap());
		
		List<Link> links = Link.INSTANCE.findPage(pageInfo, false);
		pageInfo.setUrl("readLink?pageIndex=");

		//当参数地址相同时pageInfo不用rc.setRequestAttr,但是严谨性,请将所需参数手动加入map
		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("links", links);
		return READ;
	}
	
	public String pu(RequestContext rc) {
		Link link = Link.INSTANCE.get(rc.id());
		rc.setRequestAttr("link", link);
		rc.setRequestAttr("pageIndex", rc.param("pageIndex", 1));
		return UPDATE;
	}
	
	public String u(RequestContext rc) throws IllegalAccessException, InvocationTargetException {
		Link link = Link.INSTANCE.get(rc.id());
		BeanUtils.populate(link, rc.getParameterMap());
		
		link.updateAttrs(new String[]{"description", "name", "site", "status", "trash"}, new Object[]{
				link.getDescription(), link.getName(), link.getSite(), link.getStatus(), link.getTrash()
		});
		
		rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		rc.setRequestAttr("link", link);
		rc.setRequestAttr("pageIndex", rc.param("pageIndex", 1));

		return UPDATE;
	}

	public String d(RequestContext rc) throws IllegalAccessException, InvocationTargetException {
		Link link = Link.INSTANCE.get(rc.id());
		link.delete();
		
		return r(rc);
	}
}
