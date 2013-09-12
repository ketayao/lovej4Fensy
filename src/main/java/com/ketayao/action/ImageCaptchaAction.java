/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.ImageCaptchaAction.java
 * Class:			ImageCaptchaAction
 * Date:			2013年8月30日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.action;

import java.io.IOException;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.fensy.webutil.ImageCaptchaService;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月30日 上午10:26:49 
 */

public class ImageCaptchaAction {
	public void index(RequestContext rc) throws IOException {
		ImageCaptchaService.get(rc);
	}
}
