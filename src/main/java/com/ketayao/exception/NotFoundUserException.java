/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.exception.NotFoundUserException.java
 * Class:			NotFoundUserException
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
 * @created 2012-4-5 下午3:13:08
 */

public class NotFoundUserException extends LoveJException {
	/** 描述 */
	private static final long serialVersionUID = -6841809440806011544L;

	public NotFoundUserException() {
		super();
	}

	public NotFoundUserException(String message) {
		super(message);
	}

	public NotFoundUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundUserException(Throwable cause) {
		super(cause);
	}
}
