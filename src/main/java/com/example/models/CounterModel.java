package com.example.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CounterModel {
    private static final String INIT_COUNTER_STR = "0 ";
    private static final String COUNTER_STR = "%d ";

    private StringProperty clickCountStr = new SimpleStringProperty(INIT_COUNTER_STR);
    private int clickCount = 0;

    public StringProperty getClickCountStrProperty() {
        return clickCountStr;
    }

    public void increseClickCount() {
        clickCount++;
        updateClickCountStr();
    }

    public void resetClickCount() {
        clickCount = 0;
        updateClickCountStr();
    }

    public int getClickCount() {
        return clickCount;
    }

    private void updateClickCountStr() {
        clickCountStr.set(String.format(COUNTER_STR, clickCount));
    }
}
