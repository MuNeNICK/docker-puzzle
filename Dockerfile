FROM maven:3.8.3-openjdk-11

WORKDIR /usr/src/app

# 必要なパッケージをインストール
RUN apt-get update && apt-get install -y \
    openjfx x11vnc xvfb firefox-esr wget unzip locales \
    && rm -rf /var/lib/apt/lists/*

# ロケールを設定
RUN echo "ja_JP.UTF-8 UTF-8" > /etc/locale.gen && \
    DEBIAN_FRONTEND=noninteractive dpkg-reconfigure locales && \
    locale-gen ja_JP.UTF-8 && \
    /usr/sbin/update-locale LANG=ja_JP.UTF-8
ENV LANG ja_JP.UTF-8

# 日本語フォントの設定
RUN curl -L https://moji.or.jp/wp-content/ipafont/IPAexfont/IPAexfont00401.zip  > IPAexfont00401.zip \
    && unzip IPAexfont00401.zip && rm -rf IPAexfont00401.zip \
    && mkdir /usr/share/fonts/ipa \
    && mv IPAexfont00401/*.ttf /usr/share/fonts/ipa/ \
    && fc-cache -fv

# noVNCをダウンロード
RUN wget -qO- "https://github.com/novnc/noVNC/archive/v1.4.0.tar.gz" | tar xz --directory /opt \
    && mv /opt/noVNC-1.4.0 /opt/noVNC \
    && ln -s /opt/noVNC/vnc.html /opt/noVNC/index.html \
    && wget -qO- "https://github.com/novnc/websockify/archive/v0.11.0.tar.gz" | tar xz --directory /opt \
    && mv /opt/websockify-0.11.0 /opt/noVNC/utils/websockify

# JavaFXアプリケーションの設定
COPY ./pom.xml ./pom.xml
COPY ./src ./src
COPY ./target ./target
# RUN mvn package

# VNCサーバの設定
ENV DISPLAY=:0
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
