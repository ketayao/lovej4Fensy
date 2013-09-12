/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.exception.SystemInitializeException.java
 * Class:			SystemInitializeException
 * Date:			2012-4-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.exception;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @created 2012-4-5 下午3:15:37
 */

public class SystemInitializeException extends LoveJException {

	/** 描述 */
	private static final long serialVersionUID = 4274406087095667774L;

	public SystemInitializeException() {
		super();
	}

	public SystemInitializeException(String message) {
		super(message);
	}

	public SystemInitializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemInitializeException(Throwable cause) {
		super(cause);
	}
}