package com.example.controllers;

import com.example.models.TimerModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TimerController {
    @FXML
    private Label timerView;

    // 経過時間の表示更新
    private final ChangeListener<String> onTimeCountStrListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            timerView.setText(newValue);
        }
    };

    public void setUp(TimerModel timerModel) {
        timerModel.getTimeCountStrProperty().addListener(onTimeCountStrListener);
    }

    @FXML
    private void initialize() {
        assert timerView != null : "fx:id=\"timerView\" was not injected: check your FXML file 'TimerView.fxml'.";
    }
}
