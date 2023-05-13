package com.example.models;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TimerModel {
    private static final int ONE_SECOND = 1000;
    private static final int SECONDS_IN_AN_HOUR = 3600;
    private static final int SECONDS_IN_A_MINUTE = 60;
    private static final int LIMIT_TIME = 99 * 3600 + 59 * 60 + 59;
    private static final String INIT_TIMER_STR = "00:00:00 ";
    private static final String TIMER_STR = "%02d:%02d:%02d ";

    private StringProperty timeCountStr = new SimpleStringProperty(INIT_TIMER_STR);
    private int timeCount;
    private Timeline timeline = null;

    public StringProperty getTimeCountStrProperty() {
        return timeCountStr;
    }

    public void playTimer() {
        timeCount = 0;
        updateTimeCountStr();
        if (timeline == null) {
            createTimer();
        }
        if (timeline.getStatus() != Animation.Status.RUNNING) {
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    private void createTimer() {
        Duration duration = new Duration(ONE_SECOND);
        EventHandler<ActionEvent> handler = new EventHandler<>() {
            public void handle(ActionEvent event) {
                timeCount++;
                updateTimeCountStr();
            }
        };
        KeyFrame keyFrame = new KeyFrame(duration, handler);
        timeline = new Timeline(keyFrame);
    }

    public void stopTimer() {
        if (timeline.getStatus() != Animation.Status.STOPPED) {
            timeline.stop();
        }
    }

    private void updateTimeCountStr() {
        if (timeCount >= LIMIT_TIME) {
            timeline.stop();
        }
        final int hours = timeCount / SECONDS_IN_AN_HOUR;
        final int minutes = (timeCount % SECONDS_IN_AN_HOUR) / SECONDS_IN_A_MINUTE;
        final int seconds = timeCount % SECONDS_IN_A_MINUTE;

        timeCountStr.set(String.format(TIMER_STR, hours, minutes, seconds));
    }
}
