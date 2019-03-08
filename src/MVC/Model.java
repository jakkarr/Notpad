package MVC;

import javafx.beans.property.*;

public class Model {
    private StringProperty text = new SimpleStringProperty();
    private IntegerProperty totalBuzzwordCount = new SimpleIntegerProperty();
    private DoubleProperty buzzwordRatio = new SimpleDoubleProperty();
    private String[] buzzwords;

    public Model(String... buzzwords) {
        super();
        this.buzzwords = buzzwords;
        textProperty().addListener((observable, oldValue, newValue) -> textChanged(newValue));
    }

    @SuppressWarnings("Duplicates")
    private void textChanged(String text) {
        int i = 0;
        for (String s  : buzzwords) {
            if (text.contains(s)){
                i++;
            }
        }
        buzzwordRatio.set((double)i/(double)buzzwords.length);
        totalBuzzwordCount.set(i);
    }

    public StringProperty textProperty() {
        return text;
    }

    public IntegerProperty totalBuzzwordCountProperty() {
        return totalBuzzwordCount;
    }

    public double getBuzzwordRatio() {
        return buzzwordRatio.get();
    }

    public DoubleProperty buzzwordRatioProperty() {
        return buzzwordRatio;
    }
}
