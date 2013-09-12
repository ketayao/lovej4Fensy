/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月16日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.fensy.util.Exceptions;
import com.ketayao.fensy.webutil.ServletUtils;
import com.ketayao.fensy.webutil.StorageService;
import com.ketayao.pojo.Attach;
import com.ketayao.system.SystemConfig;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月16日 下午2:26:08 
 */
public class DownloadAction {
	private static final Logger logger = LoggerFactory.getLogger(DownloadAction.class);
	
	public void index(RequestContext rc) throws IOException {
		if (rc.isRobot()) {
			rc.forbidden();
			return;
		}
		
		String file = rc.param("file");
		if (StringUtils.isNotBlank(file)) {
			Attach attach = Attach.INSTANCE.getByAttr("url", file);
			if (attach != null) {
				StorageService ss = new StorageService(SystemConfig.getConfig().get("lovej.upload.file"));
				String filePath = ss.getBasePath() + attach.getUrl();
				
				InputStream input = null;
				try {
					input = new FileInputStream(filePath);
					
					ServletUtils.setFileDownloadHeader(rc.request(), rc.response(), attach.getDescription());
					IOUtils.copy(input, rc.response().getOutputStream());
					
					attach.setDownload(attach.getDownload() + 1);
					attach.updateAttr("download", attach.getDownload());
				} catch (FileNotFoundException e) {
					logger.error(attach.getDescription() + "文件没找到或已被删除：" + Exceptions.getStackTraceAsString(e));
					rc.print(attach.getDescription() + "文件没找到或已被删除。");
				} catch (Exception e) {
					logger.error(attach.getDescription() + "文件下载出错：" + Exceptions.getStackTraceAsString(e));
					rc.print(attach.getDescription() + "文件下载出错。");
				} finally {
					IOUtils.closeQuietly(input);
				}
			}
		} else {
			rc.print(file + "文件没找到或已被删除。");
		}
	}
}
