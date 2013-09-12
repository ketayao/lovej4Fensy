/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.VAction.java
 * Class:			VAction
 * Date:			2013年8月27日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.pojo.Article;
import com.ketayao.pojo.Comment;
import com.ketayao.pojo.User;
import com.ketayao.system.Constants;
import com.ketayao.util.PageInfo;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月27日 上午11:36:44 
 */

public class ViewAction extends AbstractAction {
	private static final String VIEW = "blog/blog-view";

	/**   
	 * @param rc
	 * @param p
	 * @return  
	 * @see com.ketayao.action.AbstractAction#process(com.ketayao.fensy.mvc.RequestContext, java.lang.String[])  
	 */
	@Override
	protected String process(RequestContext rc, String[] p) throws Exception {
		// 初始化所需参数
		if (p.length < 1) {
			return "redirect:" +PAGE_404;
		}
		Article article = Article.INSTANCE.get(NumberUtils.toLong(p[0], 0));
		
		// 判断是否被删除
		if (BooleanUtils.toBoolean(article.getTrash()) || !article.getStatus().equals(Article.Status.PUBLISH)) {
			return "redirect:" +PAGE_404;
		}
		
		// 更新当前article的view浏览次数和评论数
		article.setView(article.getView() + 1);
		
		article.updateAttr("view", article.getView());
		
		PageInfo pageInfo = new PageInfo();
		if (p.length > 1) {
			pageInfo.setPageIndex(NumberUtils.toInt(p[1], 1));
		}
		pageInfo.setUrl(rc.contextPath() + "/view/" + article.getId() + "/");
		
		rc.setRequestAttr("comments", Comment.INSTANCE.findNoParent(article.getId(), pageInfo));
		
		rc.setRequestAttr("cookieUser", getCommentUserFromCookie(rc));
		rc.setRequestAttr("cp", pageInfo);
		rc.setRequestAttr("article", article);
		rc.setRequestAttr("pa", article.pre());
		rc.setRequestAttr("na", article.next());
		
		return VIEW;
	}

	private Comment getCommentUserFromCookie(RequestContext rc) {
		Comment comment = new Comment();
		
		User user = User.getLoginUser(rc);
		if (user != null) {
			comment.setName(user.getNickname());
			comment.setEmail(user.getEmail());
			
			return comment;
		}
		
		Cookie cookie = rc.cookie(Constants.COMMENT_USER);
		if (cookie != null) {
			String cookieValue = RequestContext._decrypt(cookie.getValue());
			comment.setName(StringUtils.substringBefore(cookieValue, "|"));
			comment.setEmail(StringUtils.substringBetween(cookieValue, "|"));
			comment.setSite(StringUtils.substringAfterLast(cookieValue, "|"));
		}
		
		return comment;
	}
}
