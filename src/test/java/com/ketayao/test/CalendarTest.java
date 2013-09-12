/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.CalendarTest.java
 * Class:			CalendarTest
 * Date:			2013年8月30日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年8月30日 下午2:57:01 
 */

public class CalendarTest {
	@Test
	public void test() throws ParseException {
		String s = "2013年08月";
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月");
		Date date = f.parse(s);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.MONTH, 1);
		
		calendar.add(Calendar.SECOND, -1);
		
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		System.out.println("startTime=" + f2.format(date) + "\nendTime=" + f2.format(calendar.getTime()));
	}
	
	@Test
	public void testMoth() throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
		String string = "2013-08";
		
		Date startMonth = f.parse(string);
		Date now = new Date();
		List<Date> mothes = new ArrayList<Date>();
		while (startMonth.compareTo(now) < 0) {
			mothes.add(new Date(startMonth.getTime()));
			
			startMonth = DateUtils.addMonths(startMonth, 1);
			
			System.out.println(f.format(startMonth));
		}
		System.out.println(mothes.size());
	}
}
