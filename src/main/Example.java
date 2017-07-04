package main;
import java.util.*;
import proposals.*;

public class Example {
	public static void printQuote(Quote q) {
		System.out.println(String.format("winning quote: proposal %s, operator %s, amount %d, n. of choices %d",	
			q.getProposalName(), q.getOperatorName(), q.getAmount(), q.getNChoices()));
	}
	
public static void main(String[] args) throws ProposalException {
	ProposalHandling ph = new ProposalHandling();	
//R1
	
	ph.addUsers("john","mary","ann","bob","ted","lucy");
	ph.addOperator("op3","london","rome");
	ph.addOperator("op2","london","berlin");
	ph.addOperator("op5","berlin","rome","madrid");
	ph.addOperator("op1","london","madrid","berlin");
	ph.addOperator("op10","rome");
	ph.addOperator("op4","madrid","berlin");
	
	try{ph.addOperator("op4");}
	catch(ProposalException ex) {System.out.println(ex.getMessage());}
	System.out.println(ph.getDestOperators("madrid")); 	//[op1, op4, op5]
//R2
	Proposal p1 = ph.newProposal("pro1", "london");
	Proposal p2 = ph.newProposal("pro2", "berlin");
	Proposal p3 = ph.newProposal("pro3", "madrid");
	ph.newProposal("pro4", "rome");
	try{ph.newProposal("pro5","prague");}
	catch(ProposalException ex) {System.out.println(ex.getMessage());}
	List<String> list = p1.setUsers("john","mary","ann","bob", "tom", "linda");
		System.out.println(list); //[linda, tom]
	list = p2.setUsers("ann","bob");
		System.out.println(list); //[]
	list = p3.setUsers("ann","ted","lucy");
		System.out.println(list); //[]
//R3	
	list = p1.setOperators("op1","op2", "op3", "op20", "op4"); 
		System.out.println(list); //[op20, op4]
	list = p3.setOperators("op5","op4", "op1"); 
		System.out.println(list); //[]
	p1.addQuote("op3", 1000);
	p1.addQuote("op1", 1200);
	p1.addQuote("op2", 2000);
	try{p1.addQuote("op4",3000);}
	catch(ProposalException ex) {System.out.println(ex.getMessage());}
	List<Quote> quotes = p1.getQuotes();
		System.out.println(quotes.get(0).getOperatorName()); //op2
//R4
	p1.makeChoice("john", "op1"); p1.makeChoice("ann", "op1");
	p1.makeChoice("bob", "op2"); p1.makeChoice("mary", "op3");
	try{p3.makeChoice("ted","op5");}
	catch(ProposalException ex) {System.out.println(ex.getMessage());}	
	Quote q = p1.getWinningQuote();
	printQuote(q); //winning quote: proposal pro1, operator op1, amount 1200, n. of choices 2
	p3.addQuote("op1", 1500);
	p3.addQuote("op4", 1800);
	p3.makeChoice("ann", "op1"); p3.makeChoice("lucy", "op1");
	p3.makeChoice("ted", "op4");
	q = p3.getWinningQuote();
	printQuote(q); //winning quote: proposal pro3, operator op1, amount 1500, n. of choices 2
	p2.setOperators("op2", "op4");
	p2.addQuote("op4", 2000); p2.addQuote("op2", 2200);
//R5
	SortedMap<String, Integer> quotesPerDest = ph.totalAmountOfQuotesPerDestination();
	System.out.println("quotesPerDest " + quotesPerDest); //quotesPerDest {berlin=4200, london=4200, madrid=3300}
	SortedMap<Integer, List<String>> oprPerNQuotes = ph.operatorsPerNumberOfQuotes();
	System.out.println("oprPerNQuotes " + oprPerNQuotes);  //oprPerNQuotes {2=[op1, op2, op4], 1=[op3]}
	SortedMap<String, Long> nUsersPerDest = ph.numberOfUsersPerDestination();
	System.out.println("nUsersPerDest " + nUsersPerDest);  //nUsersPerDest {berlin=2, london=4, madrid=3}
	SortedMap<Integer, List<String>> proPerNQuotes = ph.proposalsPerNumberOfQuotes();
	System.out.println("proPerNQuotes " + proPerNQuotes); //proPerNQuotes {3=[pro1], 2=[pro2, pro3]}
}

}
