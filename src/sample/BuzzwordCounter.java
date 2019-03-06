package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Set;
import java.util.SortedSet;

public class BuzzwordCounter {
    private StringProperty text = new SimpleStringProperty();
    private IntegerProperty counter = new SimpleIntegerProperty();
    private String[] buzzwords;

    public BuzzwordCounter(String... buzzwords) {
        super();
        this.buzzwords = buzzwords;
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setCount(String s) {
        int cnt = 0;
        for(String buzzword: buzzwords) {
            if (s.contains(buzzword)) {
                cnt++;
            }
        }
        counter.set(cnt);
    }

    public int getCounter() {
        return counter.get();
    }

    public IntegerProperty counterProperty() {
        return counter;
    }

    public int getTotalBuzzwords() {
        return buzzwords.length;
    }

    public void setText(String text) {
        this.text.set(text);
        setCount(text);
    }
}
