package com.ketayao.action.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.fensy.webutil.StorageService;
import com.ketayao.util.QiniuUtils;

public class FileAction {
    private static final long             MAX_IMG_SIZE = 1048576L;

    private static final SimpleDateFormat FMT_FN       = new SimpleDateFormat("yyyy/MM/dd_HHmmss_");

    public void upload(WebContext rc) throws IOException {
        boolean result = true;
        try {
            File imgFile = rc.getImage("imgFile");
            if (imgFile.length() > MAX_IMG_SIZE) {
                rc.printJson(new String[] { "error", "message" }, new Object[] {
                        Integer.valueOf(1), "File is too large" });
                return;
            }

            String url;
            if (QiniuUtils.isUse()) {
                url = FMT_FN.format(new Date()) + RandomStringUtils.randomAlphanumeric(4) + '.'
                      + FilenameUtils.getExtension(imgFile.getName()).toLowerCase();

                result = QiniuUtils.upload(QiniuUtils.QINIU_BUCKET, url, imgFile);
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
            rc.printJson(new String[] { "error", "message" }, new Object[] { Integer.valueOf(1),
                    "图片上传出错！" });
        }
    }
}
