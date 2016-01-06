/**
 * <pre>
 * Date:			2013年10月30日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Description:
 * </pre>
 **/

package com.ketayao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ketayao.fensy.util.Base64;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since 2013年10月30日 下午4:39:38
 */

public class LinkUtils {

    /**
     * 生成安全的url链接，类似 /2012/09/02/自定义名，假如name不是标准的base64字符，则自动生成时间串。 描述
     * 
     * @param name
     * @return
     */
    public static String genPermalink(String name) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd-");
        String timeString = sdf.format(now);
        if (StringUtils.isNotBlank(name) && Base64.isBase64(name)) {
            return timeString + name;
        }
        return timeString + now.getTime();
    }

    public static String genPermalink() {
        return genPermalink(null);
    }

    public static boolean validatePermalink(String link) {
        // TODO
        return false;
    }

    public static void main(String[] args) {
        System.out.println(genPermalink());
        System.out.println(genPermalink(""));
        System.out.println(genPermalink("bb-&#%#$%$^%7"));
        System.out.println(genPermalink("bb-sadv-asdsf-bbbbbb-"));
        System.out.println(genPermalink("是个否撒旦法"));
    }
}
