package com.example.controllers;

import com.example.models.CounterModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterController {
    @FXML
    private Label counterView;

    // 移動回数の表示更新
    private final ChangeListener<String> onClickCountStrListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            counterView.setText(newValue);
        }
    };

    public void setUp(CounterModel counterModel) {
        counterModel.getClickCountStrProperty().addListener(onClickCountStrListener);
    }

    @FXML
    private void initialize() {
        assert counterView != null : "fx:id=\"counterView\" was not injected: check your FXML file 'CounterView.fxml'.";
    }
}
