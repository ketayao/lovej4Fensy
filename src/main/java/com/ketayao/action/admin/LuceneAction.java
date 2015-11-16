/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.admin.LuceneAction.java
 * Class:			LuceneAction
 * Date:			2013年9月5日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.action.admin;

import com.ketayao.annotation.RolePermission;
import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.Article;
import com.ketayao.search.IndexRebuilder;
import com.ketayao.system.Constants;
import com.ketayao.system.SystemConfig;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月5日 上午11:44:29 
 */

public class LuceneAction {
    private final static String REBUILDER = "admin/site/rebuilder";

    @RolePermission(role = IUser.ROLE_TOP)
    public String index(WebContext rc) throws Exception {
        return REBUILDER;
    }

    @RolePermission(role = IUser.ROLE_TOP)
    public String rebuilder(WebContext rc) throws Exception {
        String s = rc.getParam("-s", "200");
        String p = rc.getParam("-p", SystemConfig.getConfig().get("blog.lucene.path"));
        String b = rc.getParam("-b", Article.class.getName());

        long start = System.currentTimeMillis();
        IndexRebuilder.execute(new String[] { "-s", s, "-p", p, "-b", b });
        long useTime = System.currentTimeMillis() - start;

        rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
        rc.setRequestAttr("useTime", useTime);
        return REBUILDER;
    }
}
