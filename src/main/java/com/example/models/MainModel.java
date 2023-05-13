package com.example.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MainModel {
    private TimerModel timerModel;
    private PuzzleModel puzzleModel;
    private HistoryModel historyModel;
    private CounterModel counterModel;

    private BooleanProperty isPlaying = new SimpleBooleanProperty(false);

    private final ChangeListener<Boolean> onClearListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                switchPlayAndStop();
            }
        }
    };

    public void setUp(TimerModel timerModel, PuzzleModel puzzleModel, HistoryModel historyModel,
            CounterModel counterModel) {
        this.timerModel = timerModel;
        this.puzzleModel = puzzleModel;
        this.historyModel = historyModel;
        this.counterModel = counterModel;
        puzzleModel.getIsClearProperty().addListener(onClearListener);
    }

    public BooleanProperty getIsPlayingProperty() {
        return isPlaying;
    }

    public void switchPlayAndStop() {
        if (isPlaying.get()) {
            isPlaying.set(false);
            timerModel.stopTimer();
        } else {
            isPlaying.set(true);
            timerModel.playTimer();
            puzzleModel.startPuzzle();
            historyModel.resetHistory();
            counterModel.resetClickCount();
        }
    }
}