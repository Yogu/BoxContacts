package de.yogularm.boxcontacts.utils;

import java.io.IOException;
import java.io.InputStream;

import org.simpleframework.xml.core.Persister;

public class XmlUtils {
	private XmlUtils() {
		
	}
	
	private static Persister persister;
	
	public static Persister getPersister() {
		if (persister == null)
			persister = new Persister(new XmlMatcher());
		return persister;
	}
	
	public static <T> T readAndClose(Class<T> type, InputStream input) throws UnexpectedResponseException, IOException {
		try {
			try {
				return getPersister().read(type, input);
			} catch (Exception e) {
				throw new UnexpectedResponseException(e);
			}
		} finally {
			input.close();
		}
	}
}
