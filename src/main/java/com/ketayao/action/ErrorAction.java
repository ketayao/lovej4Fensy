/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.PageErrorAction.java
 * Class:			PageErrorAction
 * Date:			2013年8月31日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.fensy.mvc.WebContext;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月31日 下午12:09:09 
 */

public class ErrorAction extends AbstractAction {
	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorAction.class);

	/**   
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception  
	 * @see com.ketayao.action.AbstractAction#process(com.ketayao.fensy.mvc.WebContext, java.lang.String[])  
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected String process(WebContext rc, String[] p) throws Exception {
		Integer statusCode = (Integer) rc.getRequest().getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}
		String message = (String) rc.getRequest().getAttribute("javax.servlet.error.message");
		String servletName = (String) rc.getRequest().getAttribute("javax.servlet.error.servlet_name");
		String uri = (String) rc.getRequest().getAttribute("javax.servlet.error.request_uri");
		Throwable t = (Throwable) rc.getRequest().getAttribute("javax.servlet.error.exception");
		Class exception = (Class) rc.getRequest().getAttribute("javax.servlet.error.exception_type");
		
		if(statusCode == 500) {
			String queryString = rc.getRequest().getQueryString();
			String url = uri + (queryString == null || queryString.length() == 0 ? "" : "?" + queryString);
			url = url.replaceAll("&amp;", "&").replaceAll("&", "&amp;");
			
		    LOGGER.error("from " + url + ":" + statusCode + "|" + message + "|" + servletName + "|" + uri + "|" + exception.getName(), t);
		    rc.setRequestAttr("exception", message);
		}
		else if(statusCode == 404) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;  
		}
		
		return "redirect:" + rc.getContextPath() + "/" + PAGE_500;
	}

}
