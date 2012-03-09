package de.yogularm.boxcontacts.utils;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

public class XmlMatcher implements Matcher {
	@SuppressWarnings("rawtypes")
	@Override
	public Transform match(Class type) throws Exception {
		if (type.equals(Instant.class))
			return new InstantTransform();
		else if (type.equals(Duration.class))
			return new DurationTransform();
		return null;
	}
}
