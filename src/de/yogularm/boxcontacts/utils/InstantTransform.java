package de.yogularm.boxcontacts.utils;

import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.simpleframework.xml.transform.Transform;

public class InstantTransform implements Transform<Instant> {
	private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		.appendDayOfMonth(2)
		.appendLiteral('.')
		.appendMonthOfYear(2)
		.appendLiteral('.')
		.appendYear(4, 4)
		.appendLiteral(' ')
		.appendHourOfDay(2)
		.appendLiteral(':')
		.appendMinuteOfHour(2)
		.toFormatter();
	
    @Override
    public Instant read(String value) throws Exception {
        return Instant.parse(value, formatter);
    }
    @Override
    public String write(Instant value) throws Exception {
    	return value.toString(formatter);
	}
}
