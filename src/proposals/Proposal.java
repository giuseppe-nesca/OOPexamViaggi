package proposals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;



public class Proposal {
	
	private Map<String,Operator> operators;
	private  Map<String,User> users;
	
	private String name;
	private Destination destination;
	private Map<String,User> interestedUsers = new HashMap<>();
	private Map<String,String> interestedStrangers = new HashMap<>();
	private Map<String,Operator> workingOperators = new HashMap<>();
	private Map<String,Quote> quotes = new HashMap<>();

	
	public Proposal(String name, Destination destination,  Map<String,User> users, Map<String,Operator> operators){
		this.users = users;
		this.name = name;
		this.destination = destination;
		this.users = users;
		this.operators = operators;
	}
	
	public List<String> setUsers(String... userNames) {
		for (String string : userNames) {
			if (users.containsKey(string)) {
				interestedUsers.put(string, users.get(string));
			}else {
				interestedStrangers.put(string, string);
			}
		}
        return interestedStrangers.values().stream().sorted().collect(Collectors.toList()); //natural order
	}

	public SortedSet<String> getUsers() {
		
        return null;
	}
	
	public List<String> setOperators(String... operatorNames) {
		List<String> nonCoinvoltiOp = new ArrayList<>();
		
		operators.values().forEach(o->{
			for (String name : nonCoinvoltiOp) {
				if ( o.getName().equals(name) ) {
					workingOperators.put(o.getName(), o);
					return;
				}
			}
			nonCoinvoltiOp.add(o.getName());
		});
        return nonCoinvoltiOp.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
	}
	
	public SortedSet<String> getOperators() {
        return null;
	}
	
	public void addQuote(String operatorName, int amount) throws ProposalException {
		if(!workingOperators.containsKey(operatorName)) throw new ProposalException();
		
		quotes.put(operatorName, new Quote(workingOperators.get(operatorName),amount));
		
	}
	public List<Quote> getQuotes() {		
        return quotes.values().stream().sorted(Comparator.comparing(Quote::getAmount,Comparator.reverseOrder())).collect(Collectors.toList());
	}
	
	//R4
		public void makeChoice (String userName, String operatorName) throws ProposalException {
			if (!interestedUsers.containsKey(userName)) throw new ProposalException();
			if (!quotes.containsKey(operatorName)) throw new ProposalException();
			
			quotes.get(operatorName).makeChoice(interestedUsers.get(userName));
		}
		
		public Quote getWinningQuote () {
			Optional<Quote> bestQuote =
			quotes.values().stream()
			.max(Comparator.comparing(Quote::getNChoices).thenComparing(Quote::getAmount,Comparator.reverseOrder()));
	        try{
	        	return bestQuote.get();
	        }catch(NoSuchElementException e){
	        	return null;
	        }
		}	
}
