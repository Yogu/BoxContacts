package de.yogularm.boxcontacts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class Web {
	public final static int TIMEOUT_MILLISEC = 10000; // = 10 seconds
	private final static String USER_AGENT = "Mozilla/5.0";

	private HttpContext context;
	private CookieStore cookieStore;
	private HttpParams httpParams;
	private Object lock = new Object();
	private UsernamePasswordCredentials credentials;

	public Web() {
		
	}

	public String inputStreamToString(InputStream is) throws IOException {
		String line = "";
		StringBuilder total = new StringBuilder();

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		// Read response until the end
		while ((line = rd.readLine()) != null) {
			total.append(line);
		}

		// Return full string
		return total.toString();
	}

	private HttpResponse execute(HttpUriRequest request) throws IOException {
		if (httpParams == null) {
			httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpProtocolParams.setUserAgent(httpParams, USER_AGENT);
		}

		createContext();

		HttpClient client = new DefaultHttpClient(httpParams);
		return client.execute(request, context);
	}

	private void createContext() {
		synchronized (lock) {
			if (context == null) {
				context = new BasicHttpContext();
				cookieStore = new BasicCookieStore();
				context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			}
		}
	}
	
	public InputStream openPostStream(String url, NameValuePair... postData)
			throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);
		if (credentials != null)
			request.addHeader(BasicScheme.authenticate(credentials, "UTF-8", false));
		try {
			request.setEntity(new UrlEncodedFormEntity(Arrays.asList(postData)));
		} catch (UnsupportedEncodingException e) {
		}
		HttpResponse response = execute(request);
		return response.getEntity().getContent();
	}
	
	public String post(String url, NameValuePair... postData) throws IOException {
		return inputStreamToString(openPostStream(url, postData));
	}

	public InputStream openGetStream(String url) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		if (credentials != null)
			request.addHeader(BasicScheme.authenticate(credentials, "UTF-8", false));

		HttpResponse response = execute(request);
		return response.getEntity().getContent();
	}

	public String get(String url) throws IOException {
		return inputStreamToString(openGetStream(url));
	}

	public String get(String url, NameValuePair... getData) throws IOException {
		return inputStreamToString(openGetStream(url));
	}
	
	public void setAuthentication(UsernamePasswordCredentials credentials) {
		this.credentials = credentials;
	}
}
