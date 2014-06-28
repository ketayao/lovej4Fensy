/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.ArchiveAction.java
 * Class:			ArchiveAction
 * Date:			2013年8月31日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.Article;
import com.ketayao.pojo.Category;
import com.ketayao.pojo.Tag;
import com.ketayao.pojo.User;
import com.ketayao.search.SearchHelper;
import com.ketayao.system.SystemInitServlet;
import com.ketayao.util.PageInfo;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 3.1.0
 * @since 2013年8月31日 上午10:05:10
 */

public class ArchiveAction extends AbstractAction {

	/**
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 * @see com.ketayao.action.AbstractAction#process(com.ketayao.fensy.mvc.WebContext,
	 *      java.lang.String[])
	 */
	@Override
	protected String process(WebContext rc, String[] p) throws Exception {
		return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
	}

	/**
	 * 根据页面目录归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String expages(WebContext rc, String[] p) throws Exception {
		if (p == null || p.length < 1) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		Category category = Category.INSTANCE.get(NumberUtils.toLong(p[0], 0));

		if (category == null) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		PageInfo pageInfo = new PageInfo();
		if (p.length > 1) {
			pageInfo.setPageIndex(NumberUtils.toInt(p[1], 1));
		}
		pageInfo.setUrl(rc.getContextPath() + "/archive/expages/"
				+ category.getId() + "/");

		List<Category> categories = Category.INSTANCE.findTree(false);

		List<Article> articles = null;
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < categories.size(); i++) {
			Category c = categories.get(i);
			if (category.getId() == c.getId()) {
				category = c;
				List<Category> children = c.getChildren();
				idList.add(c.getId());
				for (int j = 0; j < children.size(); j++) {
					idList.add(children.get(j).getId());
				}

				articles = Article.INSTANCE.findExtendPage(idList, pageInfo);

				// just is a simple page.
				if (idList.size() == 1 && articles.size() == 1) {
					Article article = articles.get(0);

					// rc.setRequestAttr("category", category);
					// rc.setRequestAttr("article", article);
					return "redirect:" + rc.getContextPath() + "/view/"
							+ article.getId();
				}
				break;
			}
		}

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("category", category);
		rc.setRequestAttr("articles", articles);

		referenceData(rc);
		return READ;
	}

	/**
	 * 根据搜索结果归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String search(WebContext rc, String[] p) throws Exception {
		String s = rc.getParam("s");
		// if (StringUtils.isNotBlank(s)) {
		// s = new String(s.getBytes("iso-8859-1"), "utf-8");
		// }

		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageIndex(NumberUtils.toInt(rc.getParam("p"), 1));
		pageInfo.setUrl(rc.getContextPath() + "/archive/search?s=" + s + "&p=");

		List<Article> articles = Article.INSTANCE.search(s, pageInfo);

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("articles", articles);
		rc.setRequestAttr("who", rc.getParam("s") + "的搜索结果");

		referenceData(rc);
		return READ;
	}

	/**
	 * 根据搜索结果归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String searchText(WebContext rc, String[] p) throws Exception {
		String s = rc.getParam("s");
		// if (StringUtils.isNotBlank(s)) {
		// s = new String(s.getBytes("iso-8859-1"), "utf-8");
		// }

		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageIndex(NumberUtils.toInt(rc.getParam("p"), 1));
		pageInfo.setUrl(rc.getContextPath() + "/archive/searchText?s=" + s
				+ "&p=");

		String q = "title:" + s + " OR content:" + s;
		Query query = SearchHelper.makeQuery("title", q, 1.0f);

		List<Long> ids = SystemInitServlet.indexHolder.find(Article.class,
				query, null, new Sort(new SortField("postTime",
						SortField.Type.LONG, true)), pageInfo.getPageIndex(),
				pageInfo.getPageSize());

		List<Article> articles = (List<Article>) Article.INSTANCE.loadList(ids);

		List<Article> list = new ArrayList<Article>(articles.size());
		for (Article source : articles) {
			Article dest = (Article) BeanUtils.cloneBean(source);
			dest.setTitle(SearchHelper.highlight(dest.getTitle(), s));
			// dest.setSummary(SearchHelper.highlight(Jsoup.clean(dest.getSummary(),
			// Whitelist.none()), s));
			String tmp = Jsoup.clean(dest.getPreview(), Whitelist.simpleText());
			if (tmp.length() > 200) {
				tmp = tmp.substring(0, 200) + "...";
			}
			dest.setPreview(SearchHelper.highlight(tmp, s));
			list.add(dest);
		}

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("articles", list);
		rc.setRequestAttr("who", rc.getParam("s") + "的搜索结果");

		referenceData(rc);
		return READ;
	}

	/**
	 * 根据category归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String category(WebContext rc, String[] p) throws Exception {
		if (p == null || p.length < 1) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		Category category = Category.INSTANCE.get(NumberUtils.toLong(p[0], 0));

		if (category == null) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		PageInfo pageInfo = new PageInfo();
		if (p.length > 1) {
			pageInfo.setPageIndex(NumberUtils.toInt(p[1], 1));
		}
		pageInfo.setUrl(rc.getContextPath() + "/archive/category/"
				+ category.getId() + "/");

		List<Article> articles = Article.INSTANCE.findExtendCategory(
				category.getId(), pageInfo);

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("category", category);
		rc.setRequestAttr("articles", articles);
		rc.setRequestAttr("who", "分类目录归档：" + category.getName());

		referenceData(rc);
		return READ;
	}

	/**
	 * 根据user归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String user(WebContext rc, String[] p) throws Exception {
		if (p == null || p.length < 1) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		User user = User.INSTANCE.get(NumberUtils.toLong(p[0], 0));
		if (user == null) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		PageInfo pageInfo = new PageInfo();
		if (p.length > 1) {
			pageInfo.setPageIndex(NumberUtils.toInt(p[1], 1));
		}
		pageInfo.setUrl(rc.getContextPath() + "/archive/user/" + user.getId()
				+ "/");

		List<Article> articles = Article.INSTANCE.findByUserId(user.getId(),
				pageInfo);

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("articles", articles);
		rc.setRequestAttr("who", "用户归档：" + user.getNickname());

		referenceData(rc);
		return READ;
	}

	/**
	 * 根据tag归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String tag(WebContext rc, String[] p) throws Exception {
		if (p == null || p.length < 1) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		Tag tag = Tag.INSTANCE.get(NumberUtils.toLong(p[0], 0));
		if (tag == null) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		PageInfo pageInfo = new PageInfo();
		if (p.length > 1) {
			pageInfo.setPageIndex(NumberUtils.toInt(p[1], 1));
		}
		pageInfo.setUrl(rc.getContextPath() + "/archive/tag/" + tag.getId()
				+ "/");

		List<Article> articles = Article.INSTANCE.findByTagId(tag.getId(),
				pageInfo);

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("articles", articles);
		rc.setRequestAttr("who", "标签归档：" + tag.getTitle());

		referenceData(rc);
		return READ;
	}

	/**
	 * 根据月份归档 描述
	 * 
	 * @param rc
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String month(WebContext rc, String[] p) throws Exception {
		if (p == null || p.length < 1) {
			return "redirect:" + rc.getContextPath() + "/" + PAGE_404;
		}

		Date date = null;
		try {
			date = DateUtils.parseDate(p[0], "yyyy-MM");
		} catch (Exception e) {
			rc.setRequestAttr("exception", p[0] + "日期格式不对");
			return "redirect:" + rc.getContextPath() + "/" + PAGE_500;
		}

		PageInfo pageInfo = new PageInfo();
		if (p.length > 1) {
			pageInfo.setPageIndex(NumberUtils.toInt(p[1], 1));
		}
		pageInfo.setUrl(rc.getContextPath() + "/archive/month/" + p[0] + "/");

		List<Article> articles = Article.INSTANCE.findByMonth(new Timestamp(
				date.getTime()), pageInfo);

		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月");

		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("articles", articles);
		rc.setRequestAttr("who", "月度归档：" + f.format(date));

		referenceData(rc);
		return READ;
	}
}
