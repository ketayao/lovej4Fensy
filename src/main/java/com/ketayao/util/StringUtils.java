/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.util.StringUtils.java
 * Class:			StringUtils
 * Date:			2013年8月21日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.util;

import java.util.ArrayList;
import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年8月21日 上午11:25:28 
 */

public class StringUtils {
	
    public static String[] split(String str, String regex) {
    	String[] temp = str.split(regex);
    	List<String> list = new ArrayList<String>(temp.length);
    	for (int i = 0; i < temp.length; i++) {
    		String s = trimToNull(temp[i]);
    		if (s != null) {
    			list.add(s);
    		}
		}
    	
        return list.toArray(new String[list.size()]);
    }
    
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }
    
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
    
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
