package zimmermann.application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class BuzzwordView extends GridPane {

	
	
	private IntegerProperty counter= new SimpleIntegerProperty();
	private int max;
	
	
	private ProgressBar progressBar= new ProgressBar(0);
	private Label bingoLabel = new Label();
	private Label buzzwordLabel = new Label();

	private TextArea textArea=new TextArea();

	public BuzzwordView(int max) {
		super();
		this.max = max;
		initWidgets();
		layoutWidgets();
		internalWireing();
	}


	private void internalWireing() {
		counterProperty().addListener((e,o,n)->buzzwordLabel.setText(n.toString()));
		counterProperty().addListener((e,o,n)->progressBar.setProgress(n.doubleValue()/max));
		counterProperty().addListener((e,o,n)->bingoLabel.setText(n.intValue()>=max?"Bingo":""));
	}


	private void layoutWidgets() {
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setHgrow(Priority.ALWAYS);
		this.getColumnConstraints().addAll(c1, c1);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		progressBar.setPrefWidth(3000);
	}


	private void initWidgets() {
		this.add(progressBar,0,0,2,1);
		this.add(textArea,0,1,2,1);
		this.add(bingoLabel, 1, 2);
		this.add(buzzwordLabel, 0, 2);
	}

	
	public StringProperty textProperty() {
		return textArea.textProperty();
	}
	
	public IntegerProperty counterProperty() {
		return counter;
	}
}
