/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.pojo.Attach.java
 * Class:			Attach
 * Date:			2012-4-15
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.pojo;

import java.util.List;

import com.ketayao.fensy.db.POJO;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @since 2012-4-15 下午1:54:52
 */

public class Attach extends POJO {

    /** 描述  */
    private static final long  serialVersionUID = -3157324395582452318L;

    public static final Attach INSTANCE         = new Attach();

    private String             url;
    private String             description;
    private long               articleId;
    private int                download         = 0;
    private String             type;
    private Article            article;

    /**
     * Getter method for property <tt>type</tt>.
     * 
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * 
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**  
     * 返回 url 的值   
     * @return url  
     */
    public String getUrl() {
        return url;
    }

    /**  
     * 设置 url 的值  
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**  
     * 返回 description 的值   
     * @return description  
     */
    public String getDescription() {
        return description;
    }

    /**  
     * 设置 description 的值  
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**  
     * 返回 articleId 的值   
     * @return articleId  
     */
    public long getArticleId() {
        return articleId;
    }

    /**  
     * 设置 articleId 的值  
     * @param articleId
     */
    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    /**  
     * 返回 download 的值   
     * @return download  
     */
    public int getDownload() {
        return download;
    }

    /**  
     * 设置 download 的值  
     * @param download
     */
    public void setDownload(int download) {
        this.download = download;
    }

    /**  
     * 返回 article 的值   
     * @return article  
     */
    public Article getArticle() {
        if (articleId > 0) {
            article = Article.INSTANCE.get(articleId);
        }
        return article;
    }

    private static final String ARTICLEIDS = "articleId=? ORDER BY id DESC";

    /**   
     * @return  
     * @see com.ketayao.fensy.db.POJO#save()  
     */
    @Override
    public long save() {
        long id = super.save();
        evictCache(ARTICLEIDS + this.getArticleId());
        return id;
    }

    /**   
     * @return  
     * @see com.ketayao.fensy.db.POJO#delete()  
     */
    @Override
    public boolean delete() {
        boolean b = super.delete();
        if (b) {
            if (articleId <= 0) {
                Attach attach = get(getId());

                evictCache(ARTICLEIDS + attach.getArticleId());
            } else {
                evictCache(ARTICLEIDS + this.getArticleId());
            }
        }

        return b;
    }

    @SuppressWarnings("unchecked")
    public List<Attach> find(long articleId) {
        List<Long> ids = getIds(ARTICLEIDS, articleId);
        return (List<Attach>) loadList(ids);
    }
}
