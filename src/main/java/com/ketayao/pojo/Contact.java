/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.entities.Comment.java
 * Class:			Comment
 * Date:			2011-11-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.pojo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.ketayao.fensy.db.POJO;
import com.ketayao.util.PageInfo;


/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-16 下午7:06:45 
 */
public class Contact extends POJO {

	/** 描述  */
	private static final long serialVersionUID = 5179755859279326481L;
	
	public static final Contact INSTANCE = new Contact();
	
	private String name;
	private String site;
	private String email;
	private String content;
	private String status = Status.NEW;
	private byte trash = 0;  
	private Timestamp postTime;
	private String postIP;
		
	public final static class Status {
		private Status() {
		}

		public static final String REPLIED = "replied";

		public static final String READED = "readed";

		public static final String NEW = "new";

	}

	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * 返回 site 的值   
	 * @return site  
	 */
	public String getSite() {
		return site;
	}

	/**  
	 * 设置 site 的值  
	 * @param site
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**  
	 * 返回 email 的值   
	 * @return email  
	 */
	public String getEmail() {
		return email;
	}

	/**  
	 * 设置 email 的值  
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**  
	 * 返回 content 的值   
	 * @return content  
	 */
	public String getContent() {
		return content;
	}

	/**  
	 * 设置 content 的值  
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**  
	 * 返回 status 的值   
	 * @return status  
	 */
	public String getStatus() {
		return status;
	}

	/**  
	 * 设置 status 的值  
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 * 返回 trash 的值   
	 * @return trash  
	 */
	public byte getTrash() {
		return trash;
	}

	/**  
	 * 设置 trash 的值  
	 * @param trash
	 */
	public void setTrash(byte trash) {
		this.trash = trash;
	}

	/**  
	 * 返回 postTime 的值   
	 * @return postTime  
	 */
	public Timestamp getPostTime() {
		return postTime;
	}

	/**  
	 * 设置 postTime 的值  
	 * @param postTime
	 */
	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

	/**  
	 * 返回 postIP 的值   
	 * @return postIP  
	 */
	public String getPostIP() {
		return postIP;
	}

	/**  
	 * 设置 postIP 的值  
	 * @param postIP
	 */
	public void setPostIP(String postIP) {
		this.postIP = postIP;
	}

	private static final String FIND_PAGE = "trash = ? ORDER BY id DESC "; 
	
	/**   
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#delete()  
	 */
	@Override
	public boolean delete() {
		boolean result = super.delete();
		if (result) {
			evictCache(FIND_PAGE + 0);
			evictCache(FIND_PAGE + 1);
		}
		
		return result; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Contact> findPage(PageInfo pageInfo, Boolean trash) {
		List<Long> ids = getIds(FIND_PAGE, BooleanUtils.toInteger(trash));
		pageInfo.setTotalRec(ids.size());
		List<Long> returnIds = ids.subList(pageInfo.getStartIndex(), pageInfo.getEndIndex());
		
		List<Contact> list = loadList(returnIds);
		return list;
	}

}
