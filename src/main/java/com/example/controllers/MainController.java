package com.example.controllers;

import com.example.models.MainModel;
import com.example.models.TimerModel;
import com.example.models.PuzzleModel;
import com.example.models.HistoryModel;
import com.example.models.CounterModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainController {
    @FXML
    private Button startAndStop;
    @FXML
    private AnchorPane timer, puzzle, menu;
    @FXML
    private TimerController timerController;
    @FXML
    private PuzzleController puzzleController;
    @FXML
    private MenuController menuController;

    private static final String START_BUTTON_MSG = "開始";
    private static final String STOP_BUTTON_MSG = "終了";

    private MainModel mainModel = new MainModel();
    private TimerModel timerModel = new TimerModel();
    private PuzzleModel puzzleModel = new PuzzleModel();
    private HistoryModel historyModel = new HistoryModel();
    private CounterModel counterModel = new CounterModel();

    // 開始・終了ボタンの表示更新
    private final ChangeListener<Boolean> onPlayingListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                startAndStop.setText(STOP_BUTTON_MSG);
            } else {
                startAndStop.setText(START_BUTTON_MSG);
            }
        }
    };

    @FXML
    private void switchPlayAndStop() {
        mainModel.switchPlayAndStop();
    }

    @FXML
    private void initialize() {
        assert startAndStop != null
                : "fx:id=\"startAndStop\" was not injected: check your FXML file 'MainView.fxml'.";
        assert timerController != null
                : "fx:id=\"timer\" was not injected: check your FXML file 'MainView.fxml'.";
        assert puzzleController != null
                : "fx:id=\"puzzle\" was not injected: check your FXML file 'MainView.fxml'.";
        mainModel.getIsPlayingProperty().addListener(onPlayingListener);
        menuController.setUp(puzzleController.getPanels(), mainModel);
        timerController.setUp(timerModel);
        puzzleController.setUp(mainModel, puzzleModel, historyModel, counterModel);
        mainModel.setUp(timerModel, puzzleModel, historyModel, counterModel);
        puzzleModel.setUp(historyModel, counterModel);
    }
}