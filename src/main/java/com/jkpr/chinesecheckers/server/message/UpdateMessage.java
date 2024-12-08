package com.jkpr.chinesecheckers.server.message;


public class UpdateMessage extends Message {
    public final String content;

    public UpdateMessage(String content) {
        super(MessageType.UPDATE);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String  serialize() {
        return getType().name() +" " + content;
    }

    public static UpdateMessage fromContent(String content) {
        //TODO: zastanowci sie co trzeba wysylac
        //do dalszego przemyslenia
        return new UpdateMessage(content);
    }

}
