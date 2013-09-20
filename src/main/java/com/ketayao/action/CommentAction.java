/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.CmtAction.java
 * Class:			CmtAction
 * Date:			2013年8月27日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.fensy.webutil.ImageCaptchaService;
import com.ketayao.pojo.Comment;
import com.ketayao.system.Constants;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月27日 下午5:11:16 
 */

public class CommentAction extends AbstractAction {

	/**   
	 * @param rc
	 * @param p
	 * @return  
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @see com.ketayao.action.AbstractAction#process(com.ketayao.fensy.mvc.RequestContext, java.lang.String[])  
	 */
	@Override
	protected String process(RequestContext rc, String[] p) throws IllegalAccessException, InvocationTargetException {
		boolean correct = ImageCaptchaService.validate(rc.getRequest());
		if (!correct) {
			rc.setRequestAttr("exception", "验证码错误");
			return PAGE_500;
		}
		
		if (StringUtils.isEmpty(rc.getParam("content")) && ((String)rc.getParam("content")).length() > 250) {
			rc.setRequestAttr("exception", "评论内容不能为空，并且字数不能超过250个");
			return PAGE_500;
		}
		
		Comment comment = new Comment();
		rc.populate(comment);
		
		if (comment.getParentId() != null && comment.getParentId().longValue() == 0) {
			comment.setParentId(null);
		}
		
		comment.setPostTime(new Timestamp(System.currentTimeMillis()));
		comment.setPostIP(rc.getIp());
		
		IUser iUser = rc.getUserFromCookie();
		if (iUser != null && iUser.getId() == comment.getArticle().getUserId()) {
			comment.setUserId(iUser.getId());
		}
		
		//清除不安全代码
		comment.setContent(Jsoup.clean(comment.getContent(), Whitelist.basic()));
		comment.save();
		
		saveCommentUserInCookie(rc, comment);
		
		int pageIndex = 1;
		if (p.length > 0) {
			pageIndex = NumberUtils.toInt(p[0], 1) + 1;//加一，超过最大页数，默认为最后一页
		}
		
		return "redirect:" + rc.getContextPath() + "/view/" + comment.getArticleId() + "/" + pageIndex + "#comments";
	}
	
	private void saveCommentUserInCookie(RequestContext rc, Comment comment) {
		StringBuilder sb = new StringBuilder();
		sb.append(comment.getName());
		sb.append('|');
		sb.append(comment.getEmail());
		sb.append('|');
		sb.append(comment.getSite());
		String uuid = RequestContext._encrypt(sb.toString());
		rc.setCookie(Constants.COMMENT_USER, uuid, RequestContext.MAX_AGE, true);
	}
	
}
