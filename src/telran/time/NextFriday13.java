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
		
		temporal = temporalAdjuster(temporal);
		
		while(temporal.get(ChronoField.DAY_OF_WEEK)!= FRIDAY) {			
			temporal = temporal.plus(1, ChronoUnit.MONTHS);			
		}	
		return temporal;				
	}

	private Temporal temporalAdjuster(Temporal temporal) {
		int  DAY13 = 13;
		int day = temporal.get(ChronoField.DAY_OF_MONTH);
		if(day >= DAY13) {
			temporal = temporal.plus(1, ChronoUnit.MONTHS);
		}
		return temporal.with(ChronoField.DAY_OF_MONTH, 13);
	}
	

}
