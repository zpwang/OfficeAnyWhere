package com.office.anywher.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calander {

	/**
	 * @param args
	 */
	private static int[][] calendar = new int[6][7];
	private static int[] monthDay = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
	public static void main(String[] args) throws ParseException {
		for(int i=0;i<10;i++){
			for(int j = 0 ;j<10;j++){
				System.out.println(i+"-------"+j);
				if(j == 3){break;}
			}
			System.out.println("---->>>"+i+"-------");
			break;
			
		}
		
		/*
		Calendar calender = Calendar.getInstance();
		String dateStr  = "2012-10-01";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(dateStr);
		System.out.println(date);
		calender.setTime(date);
		//calender.set(Calendar.MONTH,10);
		System.out.println("---"+calender.get(Calendar.MONTH));
		int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
		int weekOfMonth =  calender.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		int year = calender.get(Calendar.YEAR);
		int month = calender.get(Calendar.MONTH)+1;
		int dayMonth = getMonthDay(year,month);
		int count = 1;
		int start_i = weekOfMonth-1;
		int start_j = dayOfWeek-1;
		for(;start_i< calendar.length;start_i++){
			for (; start_j < calendar[start_i].length; start_j++) {
				if(count<=dayMonth){
					calendar[start_i][start_j] = count;
					count++;
				}
			}
			start_j=0;
		}
		
		for (int i = 0; i < calendar.length; i++) {
			for (int j = 0; j < calendar[i].length; j++) {
				//System.out.println(i+"-"+j+":"+calendar[i][j]);
			}
		}
		System.out.println(weekOfMonth);
		System.out.println(getMonthDay(2011,2));
	*/}
	private static int getMonthDay(int year,int month){
		if(year%400==0||(year%4==0&&year%100!=0))
		{ 
			monthDay[1]= 29;
		} 
		return monthDay[month-1];
	}

}
