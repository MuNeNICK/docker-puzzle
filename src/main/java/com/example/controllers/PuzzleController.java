package com.example.controllers;

import java.util.HashMap;

import com.example.Constants;
import com.example.models.MainModel;
import com.example.models.PuzzleModel;
import com.example.models.HistoryModel;
import com.example.models.CounterModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class PuzzleController {
    @FXML
    private GridPane board;
    @FXML
    private AnchorPane counter, history;
    @FXML
    private CounterController counterController;
    @FXML
    private HistoryController historyController;
    @FXML
    private ImageView panel1, panel2, panel3, panel4,
            panel5, panel6, panel7, panel8,
            panel9, panel10, panel11, panel12,
            panel13, panel14, panel15, panel16;

    private PuzzleModel puzzleModel;

    private HashMap<Integer, ImageView> panels;

    // パネルの状態切り替え
    private final ChangeListener<Boolean> onPlayingListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
            switchPanelsState(oldValue);
        }
    };

    // GridPaneの表示更新
    private final ListChangeListener<Integer> onPanelsStateListener = new ListChangeListener<>() {
        @Override
        public void onChanged(Change<? extends Integer> changePanels) {
            while (changePanels.next()) {
                updateGridPane(changePanels);
            }
        }
    };

    public void setUp(MainModel mainModel, PuzzleModel puzzleModel, HistoryModel historyModel,
            CounterModel counterModel) {
        this.puzzleModel = puzzleModel;
        mainModel.getIsPlayingProperty().addListener(onPlayingListener);
        puzzleModel.getPanelsStateProperty().addListener(onPanelsStateListener);
        historyController.setUp(historyModel);
        counterController.setUp(counterModel);
    }

    public HashMap<Integer, ImageView> getPanels() {
        return panels;
    }

    @FXML
    private void clickPanel(MouseEvent event) {
        final int columnOfClick = GridPane.getColumnIndex((Node) event.getSource());
        final int rowOfClick = GridPane.getRowIndex((Node) event.getSource());
        puzzleModel.movePanel(columnOfClick, rowOfClick);
    }

    private void updateGridPane(Change<? extends Integer> changePanels) {
        final int columnIndex = changePanels.getFrom() % Constants.NUMBER_OF_COLUMNS;
        final int rowIndex = changePanels.getFrom() / Constants.NUMBER_OF_COLUMNS;
        final int changedPanelKey = changePanels.getList().get(changePanels.getFrom());
        GridPane.setConstraints(panels.get(changedPanelKey), columnIndex, rowIndex);
    }

    private void switchPanelsState(boolean isPlaying) {
        panels.forEach((panelId, panel) -> panel.setDisable(isPlaying));
        panel16.setVisible(isPlaying);
    }

    @FXML
    private void initialize() {
        assert board != null : "fx:id=\"board\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel1 != null : "fx:id=\"panel1\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel2 != null : "fx:id=\"panel2\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel3 != null : "fx:id=\"panel3\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel4 != null : "fx:id=\"panel4\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel5 != null : "fx:id=\"panel5\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel6 != null : "fx:id=\"panel6\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel7 != null : "fx:id=\"panel7\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel8 != null : "fx:id=\"panel8\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel9 != null : "fx:id=\"panel9\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel10 != null : "fx:id=\"panel10\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel11 != null : "fx:id=\"panel11\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel12 != null : "fx:id=\"panel12\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel13 != null : "fx:id=\"panel13\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel14 != null : "fx:id=\"panel14\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel15 != null : "fx:id=\"panel15\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert panel16 != null : "fx:id=\"panel16\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert counterController != null
                : "fx:id=\"counter\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        assert historyController != null
                : "fx:id=\"history\" was not injected: check your FXML file 'PuzzleView.fxml'.";
        panels = new HashMap<>() {
            {
                put(1, panel1);
                put(2, panel2);
                put(3, panel3);
                put(4, panel4);
                put(5, panel5);
                put(6, panel6);
                put(7, panel7);
                put(8, panel8);
                put(9, panel9);
                put(10, panel10);
                put(11, panel11);
                put(12, panel12);
                put(13, panel13);
                put(14, panel14);
                put(15, panel15);
                put(16, panel16);
            }
        };
    }
}
