package com.example.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HistoryModel {
    private static final String HISTORY_STR = " %d回目 パネル:%d 方向:%s";
    private static final String CLEAR_MSG = "クリア";
    private static final int HISTORY_LIMIT = 80;

    private ObservableList<String> historyList = FXCollections.observableArrayList();

    public ObservableList<String> getHistoryListProperty() {
        return historyList;
    }

    public void addHistory(int clickCount, int panelId, String direction) {
        final String historyStr = String.format(HISTORY_STR, clickCount, panelId, direction);
        historyList.add(historyStr);
        final int numOfHistories = historyList.size();
        if (numOfHistories >= HISTORY_LIMIT) {
            historyList.remove(0, numOfHistories - HISTORY_LIMIT);
        }
    }

    public void resetHistory() {
        final int numOfHistory = historyList.size();
        historyList.remove(0, numOfHistory);
    }

    public void setClearMessage() {
        historyList.add(CLEAR_MSG);
    }
}
