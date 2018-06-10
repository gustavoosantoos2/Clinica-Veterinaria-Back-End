package model.utils;

import java.util.Date;

public class DateUtils {
	public static boolean isAfterToday(Date date) {
		return date != null && date.after(new Date());
	}
}
