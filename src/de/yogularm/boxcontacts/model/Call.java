package de.yogularm.boxcontacts.model;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Call {
	@Attribute(name="id")
	private int id;
	
	@Element(name="Type")
	private int type;
	
	@Element(name="Date")
	private Instant time;
	
	@Element(name="Number", required=false)
	private String number;
	
	@Element(name="Port", required=false)
	private int port;
	
	@Element(name="Duration")
	private Duration duration;
	
	@Element(name="Route")
	private int route;
	
	@Element(name="RouteType")
	private int routeType;
	
	@Element(name="Name", required=false)
	private String name;
	
	@Element(name="FonbookType", required=false)
	private String fonbookType;
	
	@Element(name="PortName", required=false)
	private String portName;
	
	public int getID() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public String getNumber() {
		return number != null ? name : "";
	}
	
	public String getName() {
		return name != null ? name : "";
	}
	
	public Instant getTime() {
		return time;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getPortName() {
		return portName != null ? name : "";
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public int getRoute() {
		return route;
	}
	
	public int getRouteType() {
		return routeType;
	}
	
	public String getFonbookType() {
		return fonbookType != null ? name : "";
	}
}
