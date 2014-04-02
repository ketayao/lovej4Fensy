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

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.mail.HtmlEmail;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.webutil.ImageCaptchaService;
import com.ketayao.pojo.Article;
import com.ketayao.pojo.Comment;
import com.ketayao.pojo.SiteConfig;
import com.ketayao.system.Constants;
import com.ketayao.system.SystemConfig;

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
	 * @throws Exception 
	 * @see com.ketayao.action.AbstractAction#process(com.ketayao.fensy.mvc.WebContext, java.lang.String[])  
	 */
	@Override
	protected String process(WebContext rc, String[] p) throws Exception {
		boolean correct = ImageCaptchaService.validate(rc.getRequest());
		if (!correct) {
			rc.setRequestAttr("exception", "验证码错误");
			return PAGE_500;
		}
		
		if (StringUtils.isNotBlank(rc.getParam("content")) && ((String)rc.getParam("content")).length() > 250) {
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
		
		String view ="/view/" + comment.getArticleId() + "/" + pageIndex + "#comments";
		
		sendMail(comment, view, rc);
		
		return "redirect:" + rc.getContextPath() + view;
	}
	
	private void saveCommentUserInCookie(WebContext rc, Comment comment) {
		StringBuilder sb = new StringBuilder();
		sb.append(comment.getName());
		sb.append('|');
		sb.append(comment.getEmail());
		sb.append('|');
		sb.append(comment.getSite());
		String uuid = WebContext.encrypt(sb.toString());
		rc.setCookie(Constants.COMMENT_USER, uuid, WebContext.MAX_AGE, true);
	}
	
	private void sendMail(Comment comment, String view, WebContext rc) throws Exception {
		SiteConfig siteConfig = (SiteConfig)rc.getContext().getAttribute(Constants.SITE_CONFIG);
		
		HtmlEmail email = new HtmlEmail();
		email.setStartTLSEnabled(true);
		email.setHostName(SystemConfig.getConfig().get("blog.exception.email.hotname"));
		email.setAuthentication(SystemConfig.getConfig().get("blog.exception.email.name"), 
				SystemConfig.getConfig().get("blog.exception.email.password"));
		email.setFrom(SystemConfig.getConfig().get("blog.exception.email.name"), siteConfig.getName());
		email.setCharset("utf-8");
		
		Article article = comment.getArticle();
		
		StringBuilder content = new StringBuilder();
		content.append("<table width=\"100%\"><tbody>");
		content.append("<tr><td><h2>原文地址：<a href=\"" + SystemConfig.getConfig().get("lovej.site.url") + view 
				+ "\" target=\"_blank\">" + article.getTitle() + "</a></h2></td></tr>");
		if (comment.getParentId() != null) {
			// 回复
			Comment parentComment = comment.getParent();
			content.append("<tr><td>" + parentComment.getContent() + "</td></tr>");
			email.setSubject("你对《" + article.getTitle() +  "》的评论得到回复");
			
			email.addTo(parentComment.getEmail());
		} else {
		    // 新评论
			email.setSubject("《" + article.getTitle() +  "》有新评论");
			
			email.addTo(article.getUser().getEmail());
		}
		content.append("<tr><td style=\"font-style:italic;\">&nbsp;&nbsp;&nbsp;&nbsp;" + comment.getContent() + "</td></tr>");
		content.append("</tbody></table>");
		
		// set the html message
		email.setHtmlMsg(content.toString());
		
		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");
		email.send();
	}
}
