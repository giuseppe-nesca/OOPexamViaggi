package proposals;

import java.util.HashMap;
import java.util.Map;

public class Quote {
	
	private int amount;
	private Operator operator;
	private int nChoices = 0;
	private Map<String,User>happyUsers = new HashMap<>();

    
	public Quote(Operator operator, int amount){
		this.operator = operator;
		this.amount = amount;
	}
	
	public int getAmount() {
	    return amount;
	}
	
	public String getProposalName() {
	       return null;
	}
	public String getOperatorName() {
	       return operator.getName();
	}
	public int getNChoices() {
	       return nChoices;
	}
	
	public void makeChoice(User u){
		happyUsers.put(u.getName(), u);
		u.addQuote(this);
		nChoices++;
	}

}
