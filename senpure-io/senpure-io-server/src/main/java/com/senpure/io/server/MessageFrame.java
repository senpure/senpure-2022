package com.senpure.io.server;


public interface MessageFrame {
    int MESSAGE_TYPE_CS = 1;
    int MESSAGE_TYPE_SC = 2;

    int messageType();

}
