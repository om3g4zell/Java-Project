package lib;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class contains static methods for dates
 * @author Louis Babuchon
 *
 */
public class MyLocalDate {
	
	/**
	 * Convert the actual into : 
	 * if the time is superior of 23h52 we return d + 1 localDate
	 * @return LocalDate : the rounded localDate
	 */
	public static LocalDate now() {
		LocalDateTime today = LocalDateTime.now();
		if(today.getHour() == 23 && today.getMinute() > 52) {
			today = today.plusMinutes(15 - (today.getMinute() % 15));
		}
		LocalDate myDate = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth());
		return myDate;
	}
	
	/**
	 * Convert the given date time into : 
	 * if the time is superior of 23h52 we return d + 1 localDate
	 * @param dateTime : the date time to convert
	 * @return LocalDate : the rounded localDate
	 */
	public static LocalDate of(LocalDateTime dateTime) {
		if(dateTime.getHour() == 23 && dateTime.getMinute() > 52) {
			dateTime = dateTime.plusMinutes(15 - (dateTime.getMinute() % 15));
		}
		LocalDate myDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
		return myDate;
	}
}
