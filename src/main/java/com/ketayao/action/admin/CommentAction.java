/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.action.admin.CommentAction.java
 * Class:			CommentAction
 * Date:			2013年8月30日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.action.admin;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.Comment;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月30日 上午10:03:47 
 */

public class CommentAction {
    public String d(WebContext rc, Long id) {
        Comment comment = Comment.INSTANCE.get(id);
        comment.delete();

        return "redirect:" + rc.getContextPath() + "/view/" + comment.getArticleId() + "#comments";
    }
}
