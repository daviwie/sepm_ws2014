package util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * A number of tools commonly used when switching into and out of java.sql.Date objects. 
 * 
 * @author David Wietstruk 0706376
 *
 */
public class SQLDateTools {
	public static Date getNow() {
		String now = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
		return Date.valueOf(now);
	}

	public static Date getSQLDateFromLocalDate(LocalDate in) {
		String out = in.getYear() + "-" + in.getMonthValue() + "-" + in.getDayOfMonth();
		return Date.valueOf(out);
	}
	
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		java.util.Date uDate = cal.getTime();
		return new Date(uDate.getTime());
	}

}
