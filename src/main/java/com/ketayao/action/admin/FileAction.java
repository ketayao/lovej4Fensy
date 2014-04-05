/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月7日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action.admin;

import java.io.File;
import java.io.IOException;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.webutil.StorageService;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月7日 下午2:36:29 
 */
public class FileAction {
	private static final long MAX_IMG_SIZE = 1 * 1024 * 1024;

	public void upload(WebContext rc) throws IOException {
		try {
			File imgFile = rc.getImage("imgFile");
			if (imgFile.length() > MAX_IMG_SIZE) {
				rc.printJson(new String[] { "error", "message" }, new Object[] {
						1, "File is too large" });
				return;
			}
			StorageService ss = StorageService.IMAGE;
			String path = ss.save(imgFile);
			String url = rc.getContextPath() + ss.getReadPath() + path;
			rc.printJson(new String[] { "error", "url" },
					new Object[] { 0, url });
		} catch (Exception e) {
			rc.printJson(new String[] {"error", "message"}, new Object[] {
					1, "图片上传出错！"});
		}
	}
}
