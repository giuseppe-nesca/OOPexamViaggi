package proposals;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String name;
	private List<Quote> quotes = new ArrayList<>();
	
	public User(String name){
		this.name =  name;
	}

	public String getName() {
		return name;
	}
	
	public void addQuote(Quote q){
		quotes.add(q);
	}
	
	public List<Quote> getQuotes(){
		return quotes;
	}
	
}
