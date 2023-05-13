#!/bin/bash

XVFB（仮想フレームバッファ）とVNCサーバを起動
Xvfb :0 -screen 0 1024x768x24 &
# x11vnc -passwd secret -display :0 -N -forever &
x11vnc -display :0 -N -forever &

noVNCを起動
/opt/noVNC/utils/novnc_proxy --vnc localhost:5900 &

# JavaFXアプリケーションを起動
# java -jar target/puzzle-0.0.6.jar
mvn clean javafx:run
