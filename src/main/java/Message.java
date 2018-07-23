public enum Message {

    CONNECTED("User connected.");

    private String messageAsString;

    Message(String messageAsString) {
        this.messageAsString = messageAsString;
    }

    public String getStringValue() {
        return messageAsString;
    }
}
