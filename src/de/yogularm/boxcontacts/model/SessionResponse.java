package de.yogularm.boxcontacts.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class SessionResponse {
	@Element(name="iswriteaccess")
	private int isWriteAccess;
	
	@Element(name="SID")
	private String sessionID;
	
	@Element(name="Challenge")
	private String challenge;
	
	public boolean isWriteAccess() {
		return isWriteAccess != 0;
	}
	
	public String getSessionID() {
		return sessionID;
	}
	
	public String getChallenge() {
		return challenge;
	}
}
