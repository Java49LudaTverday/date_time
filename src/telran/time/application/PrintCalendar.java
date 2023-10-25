package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET = 10;
	static DayOfWeek[] daysOfWeek = DayOfWeek.values();

	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstWeekDay());
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstWeekDay());

	}

	private static void printDays(int month, int year, DayOfWeek firstDayOfWeek) {
		int nDays = getNumberOfDays(month, year);
		int currentWeekDay = getFirstWeekDay(month, year);
		int offsetFirstDay = getOffsetFirstDay(currentWeekDay, firstDayOfWeek);
		printOffset(offsetFirstDay);
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			offsetFirstDay++;
			if (offsetFirstDay == 7) {
				offsetFirstDay = 0;
				System.out.println();
			}
		}
	}

	private static int getOffsetFirstDay(int currentWeekDay, DayOfWeek firstDayOfWeek) {
		int weekLength = daysOfWeek.length;
		int offsetFirstDay = weekLength - (firstDayOfWeek.get(ChronoField.DAY_OF_WEEK) - 1) + currentWeekDay;
		return offsetFirstDay >= weekLength ? offsetFirstDay - weekLength : offsetFirstDay;
	}

//	private static void printDays(int month, int year) {
//		int nDays = getNumberOfDays(month, year);
//		int currentWeekDay = getFirstWeekDay(month, year);
//		printOffset(currentWeekDay);
//		for (int day = 1; day <= nDays; day++) {
//			System.out.printf("%4d", day);
//			currentWeekDay++;
//			if (currentWeekDay == 7) {
//				currentWeekDay = 0;
//				System.out.println();
//			}
//		}
//	}

	private static void printOffset(int currentWeekDay) {
		System.out.printf("%s", " ".repeat(4 * (currentWeekDay)));

	}

	private static int getFirstWeekDay(int month, int year) {
		int weekDayNumber = LocalDate.of(year, month, 1).get(ChronoField.DAY_OF_WEEK);
		return weekDayNumber - 1;
	}

	private static int getNumberOfDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek firstDayWeek) {
		System.out.print("  ");
		DayOfWeek[] week = getWeek(firstDayWeek);
		Arrays.stream(week)
				.forEach(dw -> System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, Locale.getDefault())));
		System.out.println();
	}

	private static DayOfWeek[] getWeek(DayOfWeek firstDayWeek) {
		DayOfWeek[] week = Arrays.copyOf(daysOfWeek, daysOfWeek.length);
		int day = firstDayWeek.getValue();
		for (int i = 0; i < daysOfWeek.length; i++) {
			week[i] = daysOfWeek[day - 1];
			if (day == daysOfWeek.length) {
				day = 0;
			}
			day++;
		}
		return week;
	}

	private static void printTitle(int monthNumber, int year) {
		// <year>,<month name>
		Month month = Month.of(monthNumber);
		String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
		System.out.printf("%s%s, %d\n", " ".repeat(TITLE_OFFSET), monthName, year);

	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception {
		LocalDate ld = LocalDate.now();
		int month = args.length == 0 ? ld.get(ChronoField.MONTH_OF_YEAR) : getMonth(args[0]);// if null than gets
																								// current month
		int year = args.length > 1 ? getYear(args[1]) : ld.get(ChronoField.YEAR);

		DayOfWeek firstDayWeek = args.length > 2 ? getFirstDayWeek(args[2]) : DayOfWeek.MONDAY;

		return new RecordArguments(month, year, firstDayWeek);
	}

	private static DayOfWeek getFirstDayWeek(String dayOfWeek) throws Exception {
		String message = "";
		DayOfWeek firstDayWeek = null;
		try {
			firstDayWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
		} catch (Exception e) {
			message = "first day of the week must be: monday/tuesday/wednesday/thursday/friday/saturday/sunday";
		}
		if (!message.isEmpty()) {
			throw new Exception(message);
		}

		return firstDayWeek;
	}

	private static int getYear(String yearStr) throws Exception {
		String message = "";
		int year = 0;
		try {
			year = Integer.parseInt(yearStr);
			if (year < 0) {
				message = "year must be a positive number ";
			}

		} catch (NumberFormatException e) {
			message = "year must be a number";
		}
		if (!message.isEmpty()) {
			throw new Exception(message);
		}

		return year;
	}

	private static int getMonth(String monthStr) throws Exception {
		String message = "";
		int month = 0;
		try {
			month = Integer.parseInt(monthStr);
			if (month < 1 || month > 12) {
				message = "month must be in the range [1-12]";
			}

		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if (!message.isEmpty()) {
			throw new Exception(message);
		}

		return month;
	}

}
