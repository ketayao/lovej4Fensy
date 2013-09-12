package com.ketayao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ketayao.fensy.mvc.IUser;

/**
 * 系统所有注释的容器
 */
public class BlogAnnotation {
	/**
	 * 用户权限注释
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface UserRoleRequired {

		/**
		 * 用户的角色
		 * @return
		 */
		public byte role() default IUser.ROLE_GENERAL;
		
	}
}
