package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if(!temporal.isSupported(ChronoUnit.DAYS)) {
			throw new UnsupportedTemporalTypeException("Temporal must support DAYS");
		}
		
		int FRIDAY = DayOfWeek.FRIDAY.getValue();
		int weekLength = DayOfWeek.values().length;
		int day = temporal.get(ChronoField.DAY_OF_WEEK);
		if(FRIDAY > day) {
			temporal =  temporal.plus(FRIDAY - day, ChronoUnit.DAYS);
		} else {
			 temporal  =  temporal.plus(weekLength - (day - FRIDAY), ChronoUnit.DAYS);
		}
		while(temporal.get(ChronoField.DAY_OF_MONTH)!= 13) {			
			temporal = temporal.plus(weekLength, ChronoUnit.DAYS);
		}
	
		return temporal;
				
	}
	

}
