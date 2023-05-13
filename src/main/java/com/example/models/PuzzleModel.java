package com.example.models;

import com.example.Constants;

import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PuzzleModel {
    private static final String DOWN_STR = "下";
    private static final String UP_STR = "上";
    private static final String LEFT_STR = "左";
    private static final String RIGHT_STR = "右";

    private HistoryModel historyModel;
    private CounterModel counterModel;

    private BooleanProperty isClear = new SimpleBooleanProperty(false);
    private ObservableList<Integer> panelsState = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16);

    public void setUp(HistoryModel historyModel, CounterModel counterModel) {
        this.historyModel = historyModel;
        this.counterModel = counterModel;
    }

    public BooleanProperty getIsClearProperty() {
        return isClear;
    }

    public ObservableList<Integer> getPanelsStateProperty() {
        return panelsState;
    }

    public void startPuzzle() {
        isClear.set(false);
        shufflePuzzle();
    }

    public void movePanel(int columnOfClick, int rowOfClick) {
        final int columnOfEmpty = getEmptyIndex() % Constants.NUMBER_OF_COLUMNS;
        final int rowOfEmpty = getEmptyIndex() / Constants.NUMBER_OF_COLUMNS;

        final boolean shouldMoveDown = (columnOfClick == columnOfEmpty) && (rowOfClick < rowOfEmpty);
        final boolean shouldMoveUp = (columnOfClick == columnOfEmpty) && (rowOfClick > rowOfEmpty);
        final boolean shouldMoveLeft = (rowOfClick == rowOfEmpty) && (columnOfClick > columnOfEmpty);
        final boolean shouldMoveRight = (rowOfClick == rowOfEmpty) && (columnOfClick < columnOfEmpty);

        final int panelIndex = rowOfClick * Constants.NUMBER_OF_COLUMNS + columnOfClick;
        final int panelId = panelsState.get(panelIndex);

        if (shouldMoveDown) {
            moveDownPanels(columnOfEmpty, rowOfClick, rowOfEmpty);
            counterModel.increseClickCount();
            historyModel.addHistory(counterModel.getClickCount(), panelId, DOWN_STR);
        } else if (shouldMoveUp) {
            moveUpPanels(columnOfEmpty, rowOfClick, rowOfEmpty);
            counterModel.increseClickCount();
            historyModel.addHistory(counterModel.getClickCount(), panelId, UP_STR);
        } else if (shouldMoveLeft) {
            moveLeftPanels(columnOfClick, columnOfEmpty, rowOfEmpty);
            counterModel.increseClickCount();
            historyModel.addHistory(counterModel.getClickCount(), panelId, LEFT_STR);
        } else if (shouldMoveRight) {
            moveRightPanels(columnOfClick, columnOfEmpty, rowOfEmpty);
            counterModel.increseClickCount();
            historyModel.addHistory(counterModel.getClickCount(), panelId, RIGHT_STR);
        }
        checkClear();
    }

    private void moveDownPanels(int columnOfEmpty, int rowOfClick, int rowOfEmpty) {
        for (int i = rowOfEmpty; i > rowOfClick; i--) {
            final int movePanelIndex = (i - 1) * Constants.NUMBER_OF_COLUMNS + columnOfEmpty;
            swapPanelsState(getEmptyIndex(), movePanelIndex);
        }
    }

    private void moveUpPanels(int columnOfEmpty, int rowOfClick, int rowOfEmpty) {
        for (int i = rowOfEmpty; i < rowOfClick; i++) {
            final int movePanelIndex = (i + 1) * Constants.NUMBER_OF_COLUMNS + columnOfEmpty;
            swapPanelsState(getEmptyIndex(), movePanelIndex);
        }
    }

    private void moveLeftPanels(int columnOfClick, int columnOfEmpty, int rowOfEmpty) {
        for (int i = columnOfEmpty; i < columnOfClick; i++) {
            final int movePanelIndex = rowOfEmpty * Constants.NUMBER_OF_COLUMNS + (i + 1);
            swapPanelsState(getEmptyIndex(), movePanelIndex);
        }
    }

    private void moveRightPanels(int columnOfClick, int columnOfEmpty, int rowOfEmpty) {
        for (int i = columnOfEmpty; i > columnOfClick; i--) {
            final int movePanelIndex = rowOfEmpty * Constants.NUMBER_OF_COLUMNS + (i - 1);
            swapPanelsState(getEmptyIndex(), movePanelIndex);
        }
    }

    private int getEmptyIndex() {
        for (int i = 0; i < Constants.NUMBER_OF_PANELS; i++) {
            if (panelsState.get(i) == Constants.EMPTY_PANEL_ID) {
                return i;
            }
        }
        return 0;
    }

    private void swapPanelsState(int index1, int index2) {
        final int tmp = panelsState.get(index1);
        panelsState.set(index1, panelsState.get(index2));
        panelsState.set(index2, tmp);
    }

    private void checkClear() {
        for (int i = 0; i < Constants.NUMBER_OF_PANELS; i++) {
            final int checkNum = panelsState.get(i);
            if (checkNum != i + 1) {
                return;
            }
        }
        isClear.set(true);
        historyModel.setClearMessage();
    }

    private void shufflePuzzle() {
        boolean canClear = false;
        // ランダムにシャッフル
        while (!canClear) {
            for (int i = Constants.NUMBER_OF_PANELS - 1; i >= 1; i--) {
                final Random rand = new Random();
                final int changeIndex = rand.nextInt(i);
                swapPanelsState(changeIndex, i);
            }
            canClear = parityCheck();
        }
    }

    private boolean parityCheck() {
        int[] checkArray = new int[Constants.NUMBER_OF_PANELS];
        for (int i = 0; i < Constants.NUMBER_OF_PANELS; i++) {
            checkArray[i] = panelsState.get(i);
        }
        // 2値の偶奇が一緒なら解けるパズル
        return getCountByMoveEmpty(checkArray) % 2 == getCountBySort(checkArray) % 2;
    }

    // 15パネルの盤面で考えた際に、空白を右下に持っていくのにかかる回数
    private int getCountByMoveEmpty(int[] checkArray) {
        int emptyIndex = 0;
        for (int i = 0; i < Constants.NUMBER_OF_PANELS; i++) {
            if (checkArray[i] == Constants.EMPTY_PANEL_ID) {
                emptyIndex = i;
            }
        }
        final int columnDistance = Constants.EMPTY_COLUMN_INDEX - emptyIndex % Constants.NUMBER_OF_COLUMNS;
        final int rowDistance = Constants.EMPTY_ROW_INDEX - emptyIndex / Constants.NUMBER_OF_COLUMNS;

        return columnDistance + rowDistance;
    }

    // 昇順にソートするのにかかる入れ替えの回数
    private int getCountBySort(int[] checkArray) {
        int count = 0;
        for (int i = 0; i < Constants.NUMBER_OF_PANELS; i++) {
            for (int j = Constants.NUMBER_OF_PANELS - 1; j > i; j--) {
                if (checkArray[j] == i + 1) {
                    final int tmp = checkArray[i];
                    checkArray[i] = checkArray[j];
                    checkArray[j] = tmp;
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
