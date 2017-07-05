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
		Map<String,Destination> opdestinations = new HashMap<>();
		for (String string : destinationNames) {
			Destination d = new Destination(string);
			this.destinations.put(string, d);
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
        Proposal p = new Proposal(name,destinations.get(destinationName), this.users, this.operators);
        proposte.put(name, p);
		return p;
	}
	
//R3

	public List<Quote> getUserQuotes (String userName) {
        return users.get(userName).getQuotes();
	}
	
//R5
	public SortedMap<String, Integer> totalAmountOfQuotesPerDestination() {
		
        return 
        		proposte.values().stream()
        		.flatMap(s -> s.getQuotes().stream())
        		.sorted(Comparator.comparing(q -> q.getProposal().getDestinationName()))
        		.collect(Collectors.groupingBy(
        				q -> q.getProposal().getDestinationName(),
        				TreeMap::new, 
        				Collectors.summingInt(Quote::getAmount)))
        		;
	}
	
	public SortedMap<Integer, List<String>> operatorsPerNumberOfQuotes() {

        return 
        		proposte.values().stream()
        		.flatMap( p -> p.getQuotes().stream() )
        		.map ( q -> q.getOperatorName() )
        		.sorted()
        		.collect(Collectors.groupingBy(
        				q->q,
        				TreeMap::new,
        				Collectors.summingInt(s->1)))		//conto nQuotes
				.entrySet().stream()						
				.collect(Collectors.groupingBy(
						Map.Entry::getValue,
						TreeMap::new,
						Collectors.mapping(
								Map.Entry::getKey,
								Collectors.toList())))		
				.descendingMap();							//ottengo il risultato di tipo richiesto
	}

	public SortedMap<String, Long> numberOfUsersPerDestination() {
        return 
        		proposte.values().stream()
        		.filter( p -> p.getUsers().size() > 0 )
        		.sorted(Comparator.comparing(Proposal::getDestinationName))
        		.collect(Collectors.groupingBy(Proposal::getDestinationName, 
        				TreeMap::new,
        				Collectors.summingLong( p -> ((Proposal) p).getUsers().size() )))
        		;
	}
	
	public SortedMap<Integer, List<String>> proposalsPerNumberOfQuotes() {
        return 
        		proposte.values().stream()
        		.sorted(Comparator.comparing(Proposal::getDestinationName))
        		.collect(Collectors.groupingBy(
        				p -> p.getQuotes().size(),
        				TreeMap::new,
        				Collectors.mapping(
        						Proposal::getName,
        						Collectors.toList())))
        		.descendingMap();
	}
}
