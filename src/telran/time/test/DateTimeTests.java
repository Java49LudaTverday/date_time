package telran.time.test;
import telran.time.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Set;
class DateTimeTests {

	@Test
	void test() {
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		//Example:
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/YYYY");// 6/6/1799 adding E - day of week
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("d/MMMM/YYYY");// 6/June/1799
		System.out.println(birthAS.format(dtf));
		
		System.out.println(birthAS);//by default 1799-06-06
		System.out.printf("Bar mizva of AS %s", birthAS.plusYears(13).format(dtf1));
		System.out.println();
		LocalDate barMizva = birthAS.plusYears(13);
		assertEquals(barMizva, birthAS.with(new BarMizvaAdjuster()));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
				()-> LocalTime.now().with(new BarMizvaAdjuster()));	
		
	}
	
	@Test
	void nextFriday13Test() {
		TemporalAdjuster fr13 = new NextFriday13();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime fr13Expected = ZonedDateTime.of(2023, 10, 13, 0, 0, 0, 0, ZoneId.systemDefault());
		assertEquals(fr13Expected.toLocalDate(), zdt.with(fr13).toLocalDate());
		LocalDate fr13Expected2 = LocalDate.of(2024, 9, 13);
		LocalDate ld = LocalDate.of(2023, 10, 13);
		assertEquals(fr13Expected2, ld.with(fr13));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
				()-> LocalTime.now().with(new NextFriday13()));
		
	}
	
	@Test
	void canadaCurrentTime() {
		//display current day and time 
		//in all timeZones related to Canada
		//Data / Time (HH:mm)/ Time Zone Name		
		
		displayCurrentTime("Canada/Mountain");
	}
	void displayCurrentTime(String zoneName) {
		String CANADA_ZONE = "Canada";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/YYYY HH:mm VV ");
		
		Set<String> zonesId = ZoneId.getAvailableZoneIds(); 
		zonesId.stream().filter((zone)-> zone.contains(CANADA_ZONE)).
		map((z) -> ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(z)).format(dtf)).
		forEach(System.out::println);
		
	}

}
