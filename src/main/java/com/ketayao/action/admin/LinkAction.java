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

import com.ketayao.fensy.mvc.WebContext;
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
    private final static String READ   = "admin/contact/link-read";
    private final static String UPDATE = "admin/contact/link-update";

    //private final static String DELETE = "admin/contact/link-read";

    public String pc() {
        return CREATE;
    }

    public String c(WebContext rc) throws IllegalAccessException, InvocationTargetException {
        Link link = new Link();
        rc.populate(link);

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
    public String r(WebContext rc) throws IllegalAccessException, InvocationTargetException {
        PageInfo pageInfo = new PageInfo();
        rc.populate(pageInfo);

        List<Link> links = Link.INSTANCE.findPage(pageInfo, false);
        pageInfo.setUrl("r?pageIndex=");

        //当参数地址相同时pageInfo不用rc.setRequestAttr,但是严谨性,请将所需参数手动加入map
        rc.setRequestAttr("pageInfo", pageInfo);
        rc.setRequestAttr("links", links);
        return READ;
    }

    public String pu(WebContext rc) {
        Link link = Link.INSTANCE.get(rc.getId());
        rc.setRequestAttr("link", link);
        rc.setRequestAttr("pageIndex", rc.getParam("pageIndex", 1));
        return UPDATE;
    }

    public String u(WebContext rc) throws IllegalAccessException, InvocationTargetException {
        Link link = Link.INSTANCE.get(rc.getId());
        rc.populate(link);

        link.updateAttrs(new String[] { "description", "name", "site", "status", "trash" },
            new Object[] { link.getDescription(), link.getName(), link.getSite(), link.getStatus(),
                    link.getTrash() });

        rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
        rc.setRequestAttr("link", link);
        rc.setRequestAttr("pageIndex", rc.getParam("pageIndex", 1));

        return UPDATE;
    }

    public String d(WebContext rc) throws IllegalAccessException, InvocationTargetException {
        Link link = Link.INSTANCE.get(rc.getId());
        link.delete();

        return r(rc);
    }
}
