/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.AbstractAction.java
 * Class:			AbstractAction
 * Date:			2013年8月26日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.pojo.Article;
import com.ketayao.pojo.Category;
import com.ketayao.pojo.Comment;
import com.ketayao.pojo.Link;
import com.ketayao.system.SystemConfig;
import com.ketayao.util.PageInfo;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月26日 下午2:14:20 
 */

public abstract class AbstractAction {
	
	protected static final String PAGE_404 = "blog/404";
	protected static final String PAGE_500 = "blog/500";
	protected static final String READ = "blog/blog-read";
	
	public String index(RequestContext rc, String[] p) throws Exception {
		referenceData(rc);
		return process(rc, p); 
	}
	
	protected abstract String process(RequestContext rc, String[] p) throws Exception;
	
	protected void referenceData(RequestContext rc) throws ParseException {
		List<Category> categories = Category.INSTANCE.findTree(false);
		List<Link> newestLinks = Link.INSTANCE.findNewest(new PageInfo(), Link.Status.SHOW);
		List<Comment> newestComments = Comment.INSTANCE.findNewest(new PageInfo());
		List<Article> newestArticles = Article.INSTANCE.findNewest(new PageInfo());
		List<Article> hotestArticles = Article.INSTANCE.findHotest(new PageInfo());

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
		String string = SystemConfig.getConfig().get("blog.article.startMonth");
		
		Date startMonth = f.parse(string);
		Date now = new Date();
		List<Date> mothes = new ArrayList<Date>();
		while (startMonth.compareTo(now) < 0) {
			mothes.add(new Date(startMonth.getTime()));
			
			startMonth = DateUtils.addMonths(startMonth, 1);
		}
		Collections.reverse(mothes);
		
		rc.setRequestAttr("categories", categories);
		rc.setRequestAttr("newestLinks", newestLinks);
		rc.setRequestAttr("newestComments", newestComments);
		rc.setRequestAttr("newestArticles", newestArticles);
		rc.setRequestAttr("hotestArticles", hotestArticles);
		rc.setRequestAttr("mothes", mothes);
	}
}
