package com.example;

public class Constants {
    public static final int NUMBER_OF_PANELS = 16;
    public static final int NUMBER_OF_COLUMNS = 4;
    public static final int EMPTY_PANEL_ID = 16;
    public static final int EMPTY_ROW_INDEX = (EMPTY_PANEL_ID - 1) / NUMBER_OF_COLUMNS;
    public static final int EMPTY_COLUMN_INDEX = (EMPTY_PANEL_ID - 1) % NUMBER_OF_COLUMNS;
}
