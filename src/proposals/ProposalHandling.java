package proposals;
import java.util.*;
import java.util.stream.Collectors;

public class ProposalHandling {
	
	private Map<String,User> users = new HashMap<>();
	private Map<String,Operator> operators = new HashMap<>();
	private Map<String,Destination> destinations = new HashMap<>();
	private Map<String,Proposal> proposte = new HashMap<>();
	
    //R1
	public int addUsers(String... userNames) {
		for (String string : userNames) {
			users.put(string, new User(string));
		}
		return users.size();
	}
	
	public void addOperator(String operatorName, String... destinationNames) throws ProposalException {
		if(operators.containsKey(operatorName)) throw new ProposalException("operatore gia' presente");
		//operators.put(operatorName, new Operator(operatorName, destinationNames));
		Map<String,Destination> opdestinations = new HashMap<>();
		for (String string : destinationNames) {
			Destination d = new Destination(string);
			this.destinations.put(string, d);
			Destination test;
			test = this.destinations.get(string);
			opdestinations.put(string, d);
		}
		operators.put(operatorName, new Operator(operatorName, opdestinations));
	}

	public List<String> getDestOperators(String destinationName) {
		List<Operator> result = new ArrayList<>();
		operators.values().forEach(o->{
			if(o.getDestinations().containsKey(destinationName)){
				result.add(o);
			}
		});
		return result.stream().sorted(Comparator.comparing(Operator::getName)).map(Operator::getName).collect(Collectors.toList());
	}
//R2
	public Proposal newProposal(String name, String destinationName) throws ProposalException {
		if (proposte.containsKey(name)) throw new ProposalException();
		if (!destinations.containsKey(destinationName)) throw new ProposalException(); 
        Proposal p = new Proposal(name,destinations.get(destinationName),this.users);
        proposte.put(name, p);
		return p;
	}
	
//R3

	public List<Quote> getUserQuotes (String userName) {
        return users.get(userName).getQuotes();
	}
	
//R5
	public SortedMap<String, Integer> totalAmountOfQuotesPerDestination() {
		
        return null;
	}
	
	public SortedMap<Integer, List<String>> operatorsPerNumberOfQuotes() {
//		proposte.values().stream()
//		.flatMap(s-> { s.getQuotes().stream(); })
//		.collect(Collectors.groupingBy(Quote::getOperatorName,TreeMap::new , Collectors.counting()));
        return null;
	}

	public SortedMap<String, Long> numberOfUsersPerDestination() {
        return null;
	}
	
	public SortedMap<Integer, List<String>> proposalsPerNumberOfQuotes() {
        return null;
	}
}
