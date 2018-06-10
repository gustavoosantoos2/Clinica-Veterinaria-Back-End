package model.utils;

public class StringUtils {
	public static boolean isNullOrWhiteSpace(String str) {
		if (str == null  || str.isEmpty() || str.chars().allMatch(e -> (char)e == ' '))
			return true;
		return false;
	}
}
