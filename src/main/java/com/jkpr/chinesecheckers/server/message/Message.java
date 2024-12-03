package com.jkpr.chinesecheckers.server.message;

import java.io.Serializable;

public interface Message extends Serializable {
    MessageType getType();
}
