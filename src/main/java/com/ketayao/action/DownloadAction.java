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
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.action.admin.AttachAction;
import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.util.Exceptions;
import com.ketayao.fensy.webutil.ServletUtils;
import com.ketayao.fensy.webutil.StorageService;
import com.ketayao.pojo.Attach;
import com.ketayao.system.SystemConfig;
import com.ketayao.util.QiNiuUtils;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月16日 下午2:26:08 
 */
public class DownloadAction {
    private static final Logger logger = LoggerFactory.getLogger(DownloadAction.class);

    public void index(WebContext rc) throws IOException {
        if (rc.isRobot()) {
            rc.forbidden();
            return;
        }

        String file = rc.getParam("file");
        if (StringUtils.isNotBlank(file)) {
            Attach attach = Attach.INSTANCE.getByAttr("url", file);
            if (attach != null) {
                InputStream input = null;
                ServletOutputStream outputStream = rc.getResponse().getOutputStream();

                try {
                    if (StringUtils.equals(AttachAction.QINIU_TYPE, attach.getType())) {
                        String downloadUrl = QiNiuUtils.download(attach.getUrl());

                        URL url = new URL(downloadUrl);
                        //连接网络图片地址
                        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                        //获取连接的输入流
                        input = uc.getInputStream();
                    } else {
                        StorageService ss = new StorageService(
                            SystemConfig.getConfig().get("lovej.upload.file"));
                        String filePath = ss.getBasePath() + attach.getUrl();
                        input = new FileInputStream(filePath);
                    }

                    ServletUtils.setFileDownloadHeader(rc.getRequest(), rc.getResponse(),
                        attach.getDescription());
                    IOUtils.copy(input, outputStream);

                    attach.setDownload(attach.getDownload() + 1);
                    attach.updateAttr("download", attach.getDownload());

                    outputStream.flush();
                } catch (FileNotFoundException e) {
                    logger.error(attach.getDescription() + "文件没找到或已被删除："
                                 + Exceptions.getStackTraceAsString(e));
                    rc.print(attach.getDescription() + "文件没找到或已被删除。");
                } catch (Exception e) {
                    logger.error(
                        attach.getDescription() + "文件下载出错：" + Exceptions.getStackTraceAsString(e));
                    rc.print(attach.getDescription() + "文件下载出错。");
                } finally {
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(outputStream);
                }
            }
        } else {
            rc.print(file + "文件没找到或已被删除。");
        }
    }
}
