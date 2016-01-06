package com.ketayao.action;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.util.QiNiuUtils;

/**
 * 
 * 
 * @author yaoqiang.yq
 * @version $Id: FileAction.java, v 0.1 2015年9月20日 下午9:51:32 yaoqiang.yq Exp $
 */
public class FileAction {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(FileAction.class);

    public void images(WebContext rc, String[] p) throws Exception {
        ServletOutputStream outputStream = rc.getResponse().getOutputStream();
        String img = rc.getParam("p");
        InputStream input = null;
        try {
            String downloadUrl = QiNiuUtils.download(img);
            URL url = new URL(downloadUrl);
            //连接网络图片地址
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            //获取连接的输入流
            input = uc.getInputStream();

            IOUtils.copy(input, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("获取图片出错：" + img);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(outputStream);
        }

        //return "redirect:" + QiNiuUtils.download(QiNiuUtils.QINIU_DOMAIN, img);
    }
}
