package de.yogularm.boxcontacts.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Foncalls {
	@ElementList(entry="Calls", inline=true, required=true)
	private List<Call> calls;
	
	public List<Call> getCalls() {
		return calls;
	}
}
