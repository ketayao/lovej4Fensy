package com.ketayao.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.fensy.util.PropertiesUtils;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.BatchCallRet;
import com.qiniu.api.rs.EntryPath;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;

/**
 * 七牛工具类
 * 
 * @author yaoqiang.yq
 * @version $Id: QiniuUtils.java, v 0.1 2015年9月20日 下午9:47:44 yaoqiang.yq Exp $
 */
public abstract class QiniuUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuUtils.class);
    public static String        QINIU_BUCKET;
    public static String        QINIU_DOMAIN;
    private static Mac          mac;

    public static void init(Properties properties) {
        QINIU_BUCKET = PropertiesUtils.getString(properties, "QINIU_BUCKET");
        QINIU_DOMAIN = PropertiesUtils.getString(properties, "QINIU_DOMAIN");
        Config.ACCESS_KEY = PropertiesUtils.getString(properties, "ACCESS_KEY");
        Config.SECRET_KEY = PropertiesUtils.getString(properties, "SECRET_KEY");
        if ((StringUtils.isNotBlank(QINIU_BUCKET)) || (StringUtils.isNotBlank(QINIU_DOMAIN))
            || (StringUtils.isNotBlank(Config.ACCESS_KEY))
            || (StringUtils.isNotBlank(Config.SECRET_KEY))) {
            mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        }
    }

    public static boolean isUse() {
        return mac != null;
    }

    public static boolean upload(String bucket, String key, File file) throws AuthException,
                                                                      JSONException {
        PutPolicy putPolicy = new PutPolicy(bucket);
        String uptoken = putPolicy.token(mac);
        PutExtra extra = new PutExtra();

        PutRet ret = IoApi.putFile(uptoken, key, file, extra);
        return ret.ok();
    }

    public static String download(String domain, String key) throws EncoderException, AuthException {
        String baseUrl = URLUtils.makeBaseUrl(domain, key);
        GetPolicy getPolicy = new GetPolicy();
        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("from qiniu's url is:" + downloadUrl);
        }
        return downloadUrl;
    }

    public static boolean delete(String bucket, String key) {
        RSClient client = new RSClient(mac);
        return client.delete(bucket, key).ok();
    }

    public static boolean batchDelete(String bucket, Set<String> keys) {
        if ((keys == null) || (keys.size() == 0)) {
            return false;
        }
        RSClient rs = new RSClient(mac);
        List<EntryPath> entries = new ArrayList<EntryPath>();
        for (String key : keys) {
            EntryPath e = new EntryPath();
            e.bucket = bucket;
            e.key = key;
            entries.add(e);
        }
        BatchCallRet bret = rs.batchDelete(entries);
        return bret.ok();
    }
}
