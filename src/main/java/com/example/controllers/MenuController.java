package com.example.controllers;

import java.util.HashMap;

import com.example.Constants;
import com.example.models.MainModel;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController {
    @FXML
    Menu selectMenu;

    private HashMap<Integer, ImageView> panels;

    // パネルの状態切り替え
    private final ChangeListener<Boolean> onPlayingListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
            switchMenuState(newValue);
        }
    };

    public void setUp(HashMap<Integer, ImageView> panels, MainModel mainmModel) {
        this.panels = panels;
        mainmModel.getIsPlayingProperty().addListener(onPlayingListener);
    }

    @FXML
    private void exitPuzzle(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void changePuzzle1(ActionEvent event) {
        for (int i = 1; i <= Constants.NUMBER_OF_PANELS; i++) {
            final String imageUrl = "\\com\\example\\image1\\" + i + ".png";
            panels.get(i).setImage(new Image(imageUrl));
        }
    }

    @FXML
    private void changePuzzle2(ActionEvent event) {
        for (int i = 1; i <= Constants.NUMBER_OF_PANELS; i++) {
            final String imageUrl = "\\com\\example\\image2\\" + i + ".png";
            panels.get(i).setImage(new Image(imageUrl));
        }
    }

    @FXML
    private void changePuzzle3(ActionEvent event) {
        for (int i = 1; i <= Constants.NUMBER_OF_PANELS; i++) {
            final String imageUrl = "\\com\\example\\image3\\" + i + ".png";
            panels.get(i).setImage(new Image(imageUrl));
        }
    }

    @FXML
    private void changePuzzle4(ActionEvent event) {
        for (int i = 1; i <= Constants.NUMBER_OF_PANELS; i++) {
            final String imageUrl = "\\com\\example\\image4\\" + i + ".png";
            panels.get(i).setImage(new Image(imageUrl));
        }
    }

    private void switchMenuState(boolean isPlaying) {
        selectMenu.setDisable(isPlaying);
    }
}
