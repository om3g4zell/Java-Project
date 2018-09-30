package lib;

import java.time.LocalTime;

/**
 * This class contain statics methods for Times
 * @author Louis Babuchon
 *
 */
public class MyLocalTime {

	/**
	 * Rounds to the quarter of an hour
	 * the given date
	 * @param hour : the hour to convert
	 * @param minutes : the minute to convert
	 * @return LocalTime : the rounded localTime
	 */
	public static LocalTime of(int hour, int minutes) {
		
		LocalTime time = LocalTime.of(hour, minutes);
		
		int mod = minutes % 15;
		if(mod > 7)  time = time.plusMinutes(15 - mod);
		else time = time.minusMinutes(mod);
		
		return time;
	}
}
