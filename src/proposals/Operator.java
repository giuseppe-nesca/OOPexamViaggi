package proposals;

import java.util.HashMap;
import java.util.Map;

public class Operator {
	
	private String name;
	private Map<String,Destination>destinations = new HashMap<>();
	
	public Operator(String name, Map<String,Destination> destinations){
		this.name = name;
		this.destinations = destinations;
	}

	public String getName() {
		return name;
	}

	public Map<String, Destination> getDestinations() {
		return destinations;
	}
	
	public boolean checkDestination(String destinationName){
		if(destinations.containsKey(destinationName)) return true;
		else return false;
	}

}
