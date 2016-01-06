package com.ketayao.action.admin;

import java.io.File;
import java.io.IOException;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.webutil.StorageService;
import com.ketayao.util.QiNiuUtils;

public class FileAction {
    private static final long MAX_IMG_SIZE = 1048576L;

    public void upload(WebContext rc) throws IOException {
        boolean result = true;
        try {
            File imgFile = rc.getImage("imgFile");
            if (imgFile.length() > MAX_IMG_SIZE) {
                rc.printJson(new String[] { "error", "message" },
                    new Object[] { Integer.valueOf(1), "File is too large" });
                return;
            }

            String url;
            if (QiNiuUtils.isUse()) {
                url = QiNiuUtils.genKey(imgFile.getName());
                result = QiNiuUtils.upload(url, imgFile);
                url = rc.getContextPath() + "/file/images?p=" + url;
            } else {
                StorageService ss = StorageService.IMAGE;
                String path = ss.save(imgFile);
                url = rc.getContextPath() + ss.getReadPath() + path;
            }
            rc.printJson(new String[] { "error", "url" }, new Object[] { Integer.valueOf(0), url });
        } catch (Exception e) {
            result = false;
        }

        if (!result) {
            rc.printJson(new String[] { "error", "message" },
                new Object[] { Integer.valueOf(1), "图片上传出错！" });
        }
    }
}
