/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年7月26日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.pojo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ketayao.fensy.db.POJO;
import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.system.Constants;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.0.0
 * @since 2013年7月26日 下午3:24:42
 */

public class User extends POJO implements IUser {

	/** 描述 */
	private static final long serialVersionUID = 3224309058267477565L;
	
	public static final User INSTANCE = new User();

	private String username;// 登录名
	private String nickname;// 昵称
	private String password;// 密码
	private String salt;//密钥
	//使用0来表示False，1表示True
	private byte frozen = 0;//是否冻结
	private String email;
	
	private boolean blocked = false;
	
	private byte role = IUser.ROLE_GENERAL;
	
	/**  
	 * 返回 username 的值   
	 * @return username  
	 */
	public String getUsername() {
		return username;
	}

	/**  
	 * 设置 username 的值  
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**  
	 * 返回 nickname 的值   
	 * @return nickname  
	 */
	public String getNickname() {
		return nickname;
	}

	/**  
	 * 设置 nickname 的值  
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**  
	 * 返回 password 的值   
	 * @return password  
	 */
	public String getPassword() {
		return password;
	}

	/**  
	 * 设置 password 的值  
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**  
	 * 返回 salt 的值   
	 * @return salt  
	 */
	public String getSalt() {
		return salt;
	}

	/**  
	 * 设置 salt 的值  
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**  
	 * 返回 frozen 的值   
	 * @return frozen  
	 */
	public byte getFrozen() {
		return frozen;
	}

	/**  
	 * 设置 frozen 的值  
	 * @param frozen
	 */
	public void setFrozen(byte frozen) {
		this.frozen = frozen;
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
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(byte role) {
		this.role = role;
	}

	/**  
	 * @param request
	 * @return  
	 */
	public static User getLoginUser(RequestContext rc) {
		Object loginUser = rc.requestAttr(Constants.LOGIN_USER);
		if (loginUser == null) {
			// get user id from cookie
			IUser cookie_user = rc.getUserFromCookie();
			if (cookie_user == null) {
				return null;
			}
			User user = User.INSTANCE.get(cookie_user.getId());
			if (user != null
					&& StringUtils.equals(user.getPassword(),
							cookie_user.getPassword())) {
				rc.setRequestAttr(Constants.LOGIN_USER, user);
				return user;
			}
		}
		return (User) loginUser;
	}
	
	/**  
	 * 返回 blocked 的值   
	 * @return blocked  
	 */
	public boolean isBlocked() {
		if (frozen == 1) {
			return true;
		} 
		return false;
	}

	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public byte getRole() {
		return role;
	}

	/**   
	 * @return  
	 * @see com.ketayao.fensy.bean.POJO#listInsertableFields()  
	 */
	@Override
	protected Map<String, Object> listInsertableFields() {
		Map<String, Object> map = super.listInsertableFields();
		map.remove("blocked");
		return map;
	}
}
