/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.exception.LoveJException.java
 * Class:			LoveJException
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
 * @created 2012-4-5 下午3:13:37
 */

public class LoveJException extends RuntimeException {
	/** 描述 */
	private static final long serialVersionUID = -6537593572489712532L;

	public LoveJException() {
		super();
	}

	public LoveJException(String message) {
		super(message);
	}

	public LoveJException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoveJException(Throwable cause) {
		super(cause);
	}
}
