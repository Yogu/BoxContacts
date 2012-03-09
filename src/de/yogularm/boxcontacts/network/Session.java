package de.yogularm.boxcontacts.network;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicNameValuePair;

import de.yogularm.boxcontacts.Config;
import de.yogularm.boxcontacts.model.Call;
import de.yogularm.boxcontacts.model.Foncalls;
import de.yogularm.boxcontacts.model.SessionResponse;
import de.yogularm.boxcontacts.utils.Hex;
import de.yogularm.boxcontacts.utils.UnexpectedResponseException;
import de.yogularm.boxcontacts.utils.Web;
import de.yogularm.boxcontacts.utils.WebUtils;
import de.yogularm.boxcontacts.utils.XmlUtils;

/**
 * Handles a session with the router
 * 
 * @author Yogu
 */
public class Session {
	private Config config;
	private String sessionID;
	@SuppressWarnings("unused")
	private String lastErrorMessage;
	private Web web;
	
	private static final String EMPTY_SID = "0000000000000000";
	
	public Session(Config config) {
		this.config = config;
		web = new Web();
		if (config.isRemote())
			web.setAuthentication(new UsernamePasswordCredentials(config.getRemoteUser(), config.getRemotePassword()));
	}
	
	public boolean login() throws IOException, UnexpectedResponseException {
		InputStream stream =
			web.openGetStream(getURL() + "?getpage=../html/login_sid.xml");
		SessionResponse response = XmlUtils.readAndClose(SessionResponse.class, stream);
		if (response.isWriteAccess()) {
			sessionID = response.getSessionID();
			return true;
		} else {
			String pass = response.getChallenge() + "-" + config.getPassword();
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				sessionID = "";
				throw new RuntimeException(e);
			}
			String passResponse = response.getChallenge() + "-" + Hex.toHex(md.digest(pass.getBytes("UTF-16LE")));
			
			String r = web.post(getURL(),
				new BasicNameValuePair("getpage", "../html/de/menus/menu2.html"),
				new BasicNameValuePair("login:command/response", passResponse));

			Pattern pattern = Pattern.compile(".*\\<p class\\=\\\"errorMessage\\\"\\>(.*?)\\<\\/p\\>.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher matcher = pattern.matcher(r);
			if (matcher.matches()) {
				lastErrorMessage = WebUtils.decodeHTML(matcher.group(1));
				sessionID = "";
				return false;
			}
			
			pattern = Pattern.compile(".*<input type=\"hidden\" name=\"sid\" value=\"([A-Fa-f0-9]{16})\" id=\"uiPostSid\">.*", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(r);
			if (matcher.matches()) {
				String id = matcher.group(1);
				if (!id.equals(EMPTY_SID)) {
					this.sessionID = id;
					return true;
				} else {
					sessionID = "";
					return false;
				}
			} else {
				sessionID = "";
				throw new UnexpectedResponseException("Login failed");
			}
		}
	}
	
	public String getSessionID() {
		return sessionID;
	}
	
	public List<Call> getCalls() throws IOException, UnexpectedResponseException {
	  // get the frontend-page to refresh the list
		doPOST(new BasicNameValuePair("getpage", "../html/de/menus/menu2.html"),
				new BasicNameValuePair("var:menu", "fon"),
				new BasicNameValuePair("var:pagename", "foncalls"),
				new BasicNameValuePair("var:errorpagename", "foncalls"),
				new BasicNameValuePair("var:type", "0")).close(); // close the stream
		
		InputStream stream = doGET(new BasicNameValuePair("getpage", "../html/de/home/foncallsdaten.xml"));
		Foncalls calls = XmlUtils.readAndClose(Foncalls.class, stream);
		return calls.getCalls();
	}
	
	private InputStream doGET(NameValuePair... data) throws IOException {
		if (!sessionID.equals(EMPTY_SID)) {
			data = Arrays.copyOf(data, data.length + 1);
			data[data.length - 1] = new BasicNameValuePair("sid", sessionID);
		}
		String url = getURL() + WebUtils.formatQuery(data);
		return web.openGetStream(url);
	}
	
	private InputStream doPOST(NameValuePair... data) throws IOException {
		if (!sessionID.equals(EMPTY_SID)) {
			data = Arrays.copyOf(data, data.length + 1);
			data[data.length - 1] = new BasicNameValuePair("sid", sessionID);
		}
		return web.openPostStream(getURL(), data);
	}
	
	private String getURL() {
		String protocol = config.isRemote() ? "https" : "http"; 
		return String.format("%s://%s/cgi-bin/webcm", protocol, config.getHost()); 
	}
}
