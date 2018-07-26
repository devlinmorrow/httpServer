public enum Message {

    BLANKLINE("\n\n"),
    REQUESTMADE("Request made.");

    private String messageAsString;

    Message(String messageAsString) {
        this.messageAsString = messageAsString;
    }

    public String getS() {
        return messageAsString;
    }
}
