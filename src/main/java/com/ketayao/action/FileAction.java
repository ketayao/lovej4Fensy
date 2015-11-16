package com.ketayao.action;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.util.QiniuUtils;

/**
 * 
 * 
 * @author yaoqiang.yq
 * @version $Id: FileAction.java, v 0.1 2015年9月20日 下午9:51:32 yaoqiang.yq Exp $
 */
public class FileAction {
    public String images(WebContext rc, String[] p) throws Exception {
        String img = rc.getParam("p");
        return "redirect:" + QiniuUtils.download(QiniuUtils.QINIU_DOMAIN, img);
    }
}
