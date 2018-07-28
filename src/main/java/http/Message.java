package http;

public enum Message {

    REQUESTMADE("Request made.");

    private String messageAsString;

    Message(String messageAsString) {
        this.messageAsString = messageAsString;
    }

    public String getS() {
        return messageAsString;
    }
}
