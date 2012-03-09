package de.yogularm.boxcontacts.utils;

import java.net.URLEncoder;

import org.apache.http.NameValuePair;

public class WebUtils {
	public static String decodeHTML(String source) {
		return source.replace("&nbsp;", " ")
				.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&amp;", "&");
	}
	
	public static String formatQuery(NameValuePair[] values) {
		String query = null;
		for (NameValuePair pair : values) {
			if (query == null)
				query = "?";
			else
				query += "&";
			query += URLEncoder.encode(pair.getName()) + "=" + URLEncoder.encode(pair.getValue());
		}
		return query == null ? "" : query;
	}
}
