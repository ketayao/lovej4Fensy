/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月14日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action.admin;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.pojo.Article;
import com.ketayao.pojo.ArticleTag;
import com.ketayao.pojo.Category;
import com.ketayao.pojo.Tag;
import com.ketayao.pojo.User;
import com.ketayao.system.Constants;
import com.ketayao.util.PageInfo;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月14日 下午4:13:39 
 */
public class ArticleAction {
	private static final String CREATE = "admin/content/article-create";
	private static final String READ = "admin/content/article-read";
	private static final String UPDATE = "admin/content/article-update";
	private static final String VIEW = "admin/content/article-view";
	
	public String preCreate(RequestContext rc) {
		List<Category> parents = Category.INSTANCE.findTree(false);
		List<Tag> tags = (List<Tag>)Tag.INSTANCE.queryCacheList();
		
		rc.setRequestAttr("parents", parents);
		rc.setRequestAttr("tags", getTags(tags));
		return CREATE;
	}

	public String create(RequestContext rc) throws IllegalAccessException, InvocationTargetException {
		User user = User.getLoginUser(rc);
		
		Article article = new Article();
		BeanUtils.populate(article, rc.getParameterMap());
		
		article.setUserId(user.getId());
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		// 更新置顶时间
		boolean top = BooleanUtils.toBoolean(rc.param("top", "false"));
		if (top == true) {
			article.setTopTime(now);
		}
		
		article.setPostTime(now);
		article.setModifyTime(now);
		
		article.save();
		
		String tags = rc.param("tags");
		String[] tagArray = null;
		if (StringUtils.isNotBlank(tags)) {
			tagArray = com.ketayao.util.StringUtils.split(tags, ",");
		}
		if (tagArray != null) {
			saveTag(tagArray, article);	
		}
		
		rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		return preCreate(rc);
	}
	
	public String read(RequestContext rc) {
		User user = User.getLoginUser(rc);
		
		long categoryId = rc.param("categoryId", 0L);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageIndex(rc.param("pageIndex", 1));
		
		List<Article> articles = null;
		if (categoryId > 0) {
			articles = Article.INSTANCE.findPage(user.getId(), categoryId, pageInfo);
			pageInfo.setUrl("read?categoryId=" + categoryId + "&pageIndex=");
		} else {
			articles = Article.INSTANCE.findPage(user.getId(), false, pageInfo);
			pageInfo.setUrl("read?pageIndex=");
		}
		
		List<Category> parents = Category.INSTANCE.findTree(false);
		
		rc.setRequestAttr("pageInfo", pageInfo);
		rc.setRequestAttr("articles", articles);
		rc.setRequestAttr("parents", parents);
		rc.setRequestAttr("categoryId", categoryId);
		
		return READ;
	}
	
	public String preUpdate(RequestContext rc) throws Exception {
		long id = rc.param("id", 0L);
		long categoryId = rc.param("categoryId", 0L);
		int pageIndex = rc.param("pageIndex", 1);
		
		List<Category> parents = Category.INSTANCE.findTree(false);
		Article article = Article.INSTANCE.get(id);
		
		//List<Long> tagIds = ArticleTag.INSTANCE.otherIds("tagId", " articleId=?", article.getId());
		//List<Tag> arTags = (List<Tag>)Tag.INSTANCE.loadList(tagIds);
		
		//List<Attach> attaches = Attach.INSTANCE.find(article.getId()); 
		List<Tag> tags = (List<Tag>)Tag.INSTANCE.queryCacheList();
		
		String tmp = getArTags(article.getArticleTags());
		
		rc.setRequestAttr("arTags", tmp);
		rc.setRequestAttr("article", article);
		rc.setRequestAttr("parents", parents);
		rc.setRequestAttr("pageIndex", pageIndex);
		rc.setRequestAttr("categoryId", categoryId);
		rc.setRequestAttr("attaches", article.getAttaches());
		rc.setRequestAttr("tags", getTags(tags));
		
		return UPDATE;
	}
	
	public String update(RequestContext rc) throws Exception {
		Article article = Article.INSTANCE.get(rc.id());
		article.setOldStatus(article.getStatus());
		BeanUtils.populate(article, rc.getParameterMap());
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// 更新置顶时间
		boolean top = BooleanUtils.toBoolean(rc.param("top", "false"));
		if (top == true) {
			article.setTopTime(timestamp);
		}
		
		article.setModifyTime(timestamp);
		article.updateAttrs(new String[]{"title", "content", "keywords", "summary", "status", "trash", 
				"modifyTime", "topTime", "categoryId"}, 
				new Object[]{article.getTitle(), article.getContent(), article.getKeywords(), article.getSummary(),
				article.getStatus(), article.getTrash(), article.getModifyTime(), article.getTopTime(), article.getCategoryId()});
		
		String tags = rc.param("tags");
		String[] tagArray = null;
		if (StringUtils.isNotBlank(tags)) {
			tagArray = com.ketayao.util.StringUtils.split(tags, ",");
		}
		if (tagArray != null) {
			updateTag(tagArray, article);	
		}
		
		rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		preUpdate(rc);

		return UPDATE;
	}
	
	public String delete(RequestContext rc) {
		long id = rc.param("id", 0L);	
		Article article = Article.INSTANCE.get(id);
		article.delete();
		
		return "redirect:" + rc.contextPath() + "/admin/article/read?pageIndex=" 
					+ rc.param("pageIndex", 1L) + "&categoryId=" + rc.param("categoryId", "");
	}
	
	public String view(RequestContext rc, Long id) {
		Article article = Article.INSTANCE.get(id);
		rc.setRequestAttr("article", article);
		return VIEW;
	}
	
	private String getTags(List<Tag> tags) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		
		if (tags != null) {
			for (Tag tag : tags) {
				builder.append("'" + tag.getTitle() + "',");
			}
		}
		
		if (builder.length() > 1) {
			builder.substring(0, builder.length() - 1);
		}
		
		builder.append("]");
		return builder.toString();
	}
	
	private String getArTags(List<ArticleTag> arTags) {
		if (arTags == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (ArticleTag articleTag : arTags) {
			builder.append(articleTag.getTag().getTitle() + ",");
		}
		
		if (builder.length() > 1) {
			builder.substring(0, builder.length() - 1);
		}
		
		return builder.toString();
	}
	
    private void saveTag(String[] tags, Article article) {
    	
    	for (String tagTitle : tags) {
    		ArticleTag articleTag = new ArticleTag();
    		articleTag.setArticleId(article.getId());
    		
            Tag tag = Tag.INSTANCE.getByAttr("title", tagTitle);
            if (tag == null) {
                tag = new Tag();
                tag.setTitle(tagTitle);

                long tagId = tag.save();

                articleTag.setTagId(tagId);
            } else {
                articleTag.setTagId(tag.getId());
            }

            articleTag.save();
            
            article.getArticleTags().add(articleTag);
    	}
    }
    
    private void updateTag(String[] tags, Article article) {
 	
    	for (String tagTitle : tags) {
    		ArticleTag articleTag = new ArticleTag();
    		articleTag.setArticleId(article.getId());
    		
            Tag tag = Tag.INSTANCE.getByAttr("title", tagTitle);
            if (tag == null) {
                tag = new Tag();
                tag.setTitle(tagTitle);
                long tagId = tag.save();

                articleTag.setTagId(tagId);
                articleTag.save();
                
                article.getArticleTags().add(articleTag);
            } else {
            	boolean isExist = false;
            	List<ArticleTag> articleTags = article.getArticleTags();
            	for (ArticleTag at : articleTags) {
					if (at.getTagId() == tag.getId()) {
						isExist = true;
						break;
					}
				}
            	
            	articleTag.setTagId(tag.getId());
            	
            	if (!isExist) {
            		articleTag.save();
            		article.getArticleTags().add(articleTag);
            	}
            }
    	}
    }
}