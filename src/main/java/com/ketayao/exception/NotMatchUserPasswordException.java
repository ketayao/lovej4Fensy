/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.exception.NotMatchUserPasswordException.java
 * Class:			NotMatchUserPasswordException
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
 * @created 2012-4-5 下午3:14:59
 */

public class NotMatchUserPasswordException extends LoveJException {

	/** 描述 */
	private static final long serialVersionUID = 1539551340390341013L;

	public NotMatchUserPasswordException() {
		super();
	}

	public NotMatchUserPasswordException(String message) {
		super(message);
	}

	public NotMatchUserPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotMatchUserPasswordException(Throwable cause) {
		super(cause);
	}
}
