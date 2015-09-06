package org.bidtime.utils.basic;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTest {

	private int weeks = 0;
	// private int MaxDate;
	private int MaxYear;

	// public static void main(String[] args) {
	// TimeTest tt = new TimeTest();
	// System.out.println("getNowTime:"+tt.getNowTime("yyyy-MM-dd"));
	// System.out.println("getMondayOFWeek:"+tt.getMondayOFWeek());
	// System.out.println("getCurrentWeekday:"+tt.getCurrentWeekday());
	// System.out.println("getPreviousWeekday:"+tt.getPreviousWeekday());
	// System.out.println("getPreviousWeekSunday:"+tt.getPreviousWeekSunday());
	// System.out.println("getNextMonday:"+tt.getNextMonday());
	// System.out.println("getNextSunday:"+tt.getNextSunday());
	// System.out.println("getNowTime:"+tt.getNowTime("yyyy-MM-dd"));
	// System.out.println("getFirstDayOfMonth:"+tt.getFirstDayOfMonth());
	// System.out.println("getDefaultDay:"+tt.getDefaultDay());
	// System.out.println("getPreviousMonthFirst:"+tt.getPreviousMonthFirst());
	// System.out.println("getPreviousMonthEnd:"+tt.getPreviousMonthEnd());
	// System.out.println("getNextMonthFirst:"+tt.getNextMonthFirst());
	// System.out.println("getNextMonthEnd:"+tt.getNextMonthEnd());
	// System.out.println("getCurrentYearFirst:"+tt.getCurrentYearFirst());
	// System.out.println("getCurrentYearEnd:"+tt.getCurrentYearEnd());
	// System.out.println("getPreviousYearFirst:"+tt.getPreviousYearFirst());
	// System.out.println("getPreviousYearEnd:"+tt.getPreviousYearEnd());
	// System.out.println("getNextYearFirst:"+tt.getNextYearFirst());
	// System.out.println("getNextYearEnd:"+tt.getNextYearEnd());
	// System.out.println("getThisSeasonTime:"+tt.getThisSeasonTime(11));
	// System.out.println("getTwoDay:2008-12-1 -- 2008-9-29"+TimeTest.getTwoDay("2008-12-1","2008-9-29"));

	// }

	/**
	 * @param sj1
	 * @param sj2
	 * @return
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	public static String getWeek(String sdate) {
		Date date = TimeTest.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, 1);
		lastDate.add(Calendar.DATE, -1);

		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, -1);
		// lastDate.add(Calendar.DATE,-1);

		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		String hehe = dateFormat.format(now);
		return hehe;
	}

	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// private int getMonthPlus(){
	// Calendar cd = Calendar.getInstance();
	// int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
	// cd.set(Calendar.DATE, 1);
	// cd.roll(Calendar.DATE, -1);
	// MaxDate=cd.get(Calendar.DATE);
	// if(monthOfNumber == 1){
	// return -MaxDate;
	// }else{
	// return 1-monthOfNumber;
	// }
	// }

	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);
		lastDate.set(Calendar.DATE, 1);
		lastDate.roll(Calendar.DATE, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);
		lastDate.set(Calendar.DATE, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);
		lastDate.set(Calendar.DATE, 1);
		lastDate.roll(Calendar.DATE, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// ��һ����
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// private int getMaxYear(){
	// Calendar cd = Calendar.getInstance();
	// cd.set(Calendar.DAY_OF_YEAR,1);//��������Ϊ�����һ��
	// cd.roll(Calendar.DAY_OF_YEAR,-1);//�����ڻع�һ�졣
	// int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
	// return MaxYear;
	// }

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// ��õ�����һ���еĵڼ���
		cd.set(Calendar.DAY_OF_YEAR, 1);// ��������Ϊ�����һ��
		cd.roll(Calendar.DAY_OF_YEAR, -1);// �����ڻع�һ�졣
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	public String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

}
