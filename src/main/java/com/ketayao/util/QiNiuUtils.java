package com.ketayao.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.fensy.util.FilenameUtils;
import com.ketayao.fensy.util.PropertiesUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛工具类
 * 
 * @author yaoqiang.yq
 * @version $Id: QiNiuUtils.java, v 0.1 2015年9月20日 下午9:47:44 yaoqiang.yq Exp $
 */
public abstract class QiNiuUtils {
    private static final Logger  LOGGER        = LoggerFactory.getLogger(QiNiuUtils.class);
    public static String         QINIU_BUCKET;
    public static String         QINIU_DOMAIN;
    private static Auth          auth;
    // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
    private static UploadManager uploadManager = new UploadManager();
    private static BucketManager bucketManager;

    public static String genKey(String filename) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss_");
        String uri = simpleDateFormat.format(new Date()) + FilenameUtils.getBaseName(filename) + "."
                     + FilenameUtils.getExtension(filename).toLowerCase();
        return uri;
    }

    public static void init(Properties properties) {
        QINIU_BUCKET = PropertiesUtils.getString(properties, "QINIU_BUCKET");
        QINIU_DOMAIN = PropertiesUtils.getString(properties, "QINIU_DOMAIN");
        String ACCESS_KEY = PropertiesUtils.getString(properties, "ACCESS_KEY");
        String SECRET_KEY = PropertiesUtils.getString(properties, "SECRET_KEY");

        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        bucketManager = new BucketManager(auth);
    }

    public static boolean isUse() {
        return auth != null;
    }

    public static boolean upload(String key, File file) {
        String token = auth.uploadToken(QINIU_BUCKET);
        Response res = null;
        try {
            res = uploadManager.put(file, key, token);
        } catch (QiniuException e) {
            try {
                LOGGER.error("Upload failure:" + e.response.bodyString(), e);
            } catch (QiniuException e1) {
            }
        }
        return res.isOK();
    }

    public static String download(String key) {
        String url = QINIU_DOMAIN + "/" + key;
        String downloadUrl = auth.privateDownloadUrl(url);

        return downloadUrl;
    }

    public static void delete(String key) {
        try {
            bucketManager.delete(QINIU_BUCKET, key);
        } catch (QiniuException e) {
            try {
                LOGGER.error("Upload failure:" + e.response.bodyString(), e);
            } catch (QiniuException e1) {
            }
        }
    }

}
