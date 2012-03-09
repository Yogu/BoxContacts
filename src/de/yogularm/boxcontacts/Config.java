package de.yogularm.boxcontacts;

/**
 * Contains a set of configuration values for connecting with the router
 * 
 * @author Yogu
 */
public class Config {
	private String host;
	private String password;
	private boolean isRemote;
	private String remoteUser;
	private String remotePassword;
	
	public Config() {
		
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String value) {
		host = value;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		password = value;
	}
	
	public boolean isRemote() {
		return isRemote;
	}

	public void setIsRemote(boolean value) {
		isRemote = value;
	}
	
	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String value) {
		remoteUser = value;
	}
	
	public String getRemotePassword() {
		return remotePassword;
	}

	public void setRemotePassword(String value) {
		remotePassword = value;
	}
}
