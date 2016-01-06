/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月14日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.action.admin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.util.Exceptions;
import com.ketayao.fensy.webutil.StorageService;
import com.ketayao.pojo.Article;
import com.ketayao.pojo.Attach;
import com.ketayao.system.SystemConfig;
import com.ketayao.util.QiNiuUtils;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月14日 下午5:17:26 
 */
public class AttachAction {
    protected final Log        logger      = LogFactory.getLog(getClass());

    public final static String NATIVE_TYPE = "native";
    public final static String QINIU_TYPE  = "qiniu";

    public String delete(WebContext rc, long id) throws IOException {
        Attach attach = Attach.INSTANCE.get(id);
        if (attach != null) {
            if (StringUtils.equals(QINIU_TYPE, attach.getType())) {
                QiNiuUtils.delete(attach.getUrl());
            } else {
                StorageService ss = new StorageService(
                    SystemConfig.getConfig().get("lovej.upload.file"));
                ss.delete(attach.getUrl());
            }

            attach.delete();
        }

        String view = "redirect:" + rc.getContextPath() + "/admin/article/preUpdate?id="
                      + rc.getParam("id") + "&categoryId=" + rc.getParam("categoryId")
                      + "&pageIndex=" + rc.getParam("pageIndex");

        return view;
    }

    public String upload(WebContext rc) throws UnsupportedEncodingException {
        String view = "redirect:" + rc.getContextPath() + "/admin/article/preUpdate?id="
                      + rc.getParam("id") + "&categoryId=" + rc.getParam("categoryId")
                      + "&pageIndex=" + rc.getParam("pageIndex") + "&message=";

        //  返回信息
        String message = "";

        // 定义允许上传的文件扩展名
        Map<String, String> extMap = SystemConfig.EXTEND_TYPE;

        // 允许最大上传文件大小
        long maxSize = WebContext.getMaxSize();

        String fn = "";
        try {
            // 检查文件大小
            File file = rc.getFile("file");

            if (file == null || file.length() > maxSize) {
                message = "上传文件大小超过限制。";
                return view + URLEncoder.encode(message, "utf-8");
            }

            // 检查扩展名
            String fileName = file.getName();
            fn = fileName;
            String baseName = FilenameUtils.getBaseName(fileName);
            String fileExt = FilenameUtils.getExtension(fileName);
            if (!Arrays.<String> asList(extMap.get("file").split(",")).contains(fileExt)) {
                message = "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("file") + "格式。";
                return view + URLEncoder.encode(message, "utf-8");
            }

            Attach attach = new Attach();
            String path = null;
            if (QiNiuUtils.isUse()) {
                path = QiNiuUtils.genKey(file.getName());
                QiNiuUtils.upload(path, file);
                attach.setType(QINIU_TYPE);
            } else {
                StorageService ss = new StorageService(
                    SystemConfig.getConfig().get("lovej.upload.file"));
                path = ss.save(file);
                attach.setType(NATIVE_TYPE);
            }

            Article article = Article.INSTANCE.get(rc.getId());
            attach.setArticleId(article.getId());
            if (StringUtils.isNotBlank(rc.getParam("description"))) {
                attach.setDescription(rc.getParam("description") + "." + fileExt);
            } else {
                attach.setDescription(baseName + "." + fileExt);
            }
            attach.setUrl(path);

            attach.save();
            article.getAttaches().add(attach);

            message = fileName + "文件上传成功";
            return view + URLEncoder.encode(message, "utf-8");
        } catch (Exception e) {
            message = fn + "文件上传失败," + e.getMessage();
            rc.setRequestAttr("message", message);
            logger.error(message + Exceptions.getStackTraceAsString(e), e);
            return view + URLEncoder.encode(message, "utf-8");
        }
    }
}
