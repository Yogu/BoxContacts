package de.yogularm.boxcontacts.utils;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.simpleframework.xml.transform.Transform;

public class DurationTransform implements Transform<Duration> {
	private static final PeriodFormatter formatter = new PeriodFormatterBuilder()
		.appendMinutes()
		.appendSeparator(":")
		.appendSeconds()
		.toFormatter();
	
    @Override
    public Duration read(String value) throws Exception {
    	return Period.parse(value, formatter).toStandardDuration();
    }
    @Override
    public String write(Duration value) throws Exception {
    	return value.toPeriod().toString(formatter);
	}
}
