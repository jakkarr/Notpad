package zimmermann.application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BuzzwordCounter {

	private StringProperty text= new SimpleStringProperty() ;

	private IntegerProperty counter = new SimpleIntegerProperty();
	
	private String[] buzzwords;

	
	
	
	public BuzzwordCounter(String... buzzwords) {
		super();
		this.buzzwords = buzzwords;
		text.addListener((obj,oldV,newV)->setCnt(newV));
	}

	public StringProperty getTextProperty() {
		return text;
	}
	
	public String getText() {
		return text.get(); 
	}

	private void setCnt(String s) {
		int cnt = 0;
		for (String buzzword:buzzwords) {
			if (s.contains(buzzword)) cnt++;
		}
		counter.set(cnt);
	}
	
	public IntegerProperty getCounterProperty() {
		return counter;
	}

	public int getCounter() {
		return counter.get();
	}
	
	public int getTotalBuzzwords() {
		return buzzwords.length;
	}


}
