/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.CAction.java
 * Class:			CAction
 * Date:			2013年8月30日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action;

import java.sql.Timestamp;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.fensy.webutil.ImageCaptchaService;
import com.ketayao.pojo.Contact;
import com.ketayao.system.Constants;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月30日 上午10:22:11 
 */

public class ContactAction extends AbstractAction {

	private static final String CREATE = "blog/blog-contact";
	
	/**   
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception  
	 * @see com.ketayao.action.AbstractAction#process(com.ketayao.fensy.mvc.RequestContext, java.lang.String[])  
	 */
	@Override
	protected String process(RequestContext rc, String[] p) throws Exception {
		return CREATE;
	}

	public String c(RequestContext rc) throws Exception {
		boolean correct = ImageCaptchaService.validate(rc.request());
		if (!correct) {
			rc.setRequestAttr("exception", "验证码错误");
			return PAGE_500;
		}
		
		if (StringUtils.isEmpty(rc.param("content")) && ((String)rc.param("content")).length() > 500) {
			rc.setRequestAttr("exception", "留言内容不能为空，并且字数不能超过500个");
			return PAGE_500;
		}
		
		if (correct) {
			Contact contact = new Contact(); 
			BeanUtils.populate(contact, rc.getParameterMap());
			contact.setPostTime(new Timestamp(System.currentTimeMillis()));
			contact.setPostIP(rc.ip());
			
			contact.save();
			
			rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		} else {
			
			rc.setRequestAttr("exception", "验证码错误");
			return PAGE_500;
		}
		
		referenceData(rc);
		return CREATE;
	}	
}
