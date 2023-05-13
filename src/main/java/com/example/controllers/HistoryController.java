package com.example.controllers;

import com.example.models.HistoryModel;

import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;

public class HistoryController {
    @FXML
    private ListView<String> historyList;

    private static final int HISTORY_DESCRIPTION_FONT_SIZE = 16;
    private static final String HISTORY_DESCRIPTION_FONT = "System Regular";
    private static final Label HISTORY_DESCRIPTION = new Label("操作履歴が表示されます");

    // 履歴の表示更新
    private final ListChangeListener<String> onHistoryListListener = new ListChangeListener<>() {
        @Override
        public void onChanged(Change<? extends String> changeHistoryList) {
            while (changeHistoryList.next()) {
                updateHistoryList(changeHistoryList);
            }
        }
    };

    public void setUp(HistoryModel historyModel) {
        historyModel.getHistoryListProperty().addListener(onHistoryListListener);
    }

    private void updateHistoryList(Change<? extends String> changeHistoryList) {
        if (changeHistoryList.wasRemoved()) {
            historyList.getItems().setAll(changeHistoryList.getList());
            historyList.setPlaceholder(HISTORY_DESCRIPTION);
        } else {
            historyList.getItems().setAll(changeHistoryList.getList());
            historyList.scrollTo(changeHistoryList.getList().size());
        }
    }

    @FXML
    private void initialize() {
        assert historyList != null
                : "fx:id=\"historyList2\" was not injected: check your FXML file 'HistoryView.fxml'.";
        HISTORY_DESCRIPTION.setFont(new Font(HISTORY_DESCRIPTION_FONT, HISTORY_DESCRIPTION_FONT_SIZE));
        historyList.setPlaceholder(HISTORY_DESCRIPTION);
    }
}
