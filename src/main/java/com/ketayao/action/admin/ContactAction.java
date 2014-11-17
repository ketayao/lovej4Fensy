/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月25日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>	
 *
 * </pre>
 **/

package com.ketayao.action.admin;

import java.util.List;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.Contact;
import com.ketayao.util.PageInfo;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月25日 上午8:51:00 
 */

public class ContactAction {
    private static final String READ = "admin/contact/contact-read";

    public String r(WebContext rc) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(rc.getParam("pageIndex", 1));

        List<Contact> contacts = Contact.INSTANCE.findPage(pageInfo, false);
        pageInfo.setUrl("r?pageIndex=");
        rc.setRequestAttr("contacts", contacts);
        rc.setRequestAttr("pageInfo", pageInfo);

        return READ;
    }

    public String u(WebContext rc) {
        Contact contact = Contact.INSTANCE.get(rc.getId());
        contact.setStatus(rc.getParam("status", Contact.Status.NEW));

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(rc.getParam("pageIndex", 1));

        contact.updateAttr("status", contact.getStatus());
        return r(rc);
    }

    public String d(WebContext rc) {
        Contact contact = Contact.INSTANCE.get(rc.getId());
        contact.delete();

        return r(rc);
    }
}
