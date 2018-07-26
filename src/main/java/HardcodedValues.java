public enum HardcodedValues {

    RESOURCEPATH("/Users/devlin/cob_spec/public"),
    HTTPVERSION("HTTP/1.1"),
    BLANKLINE("\n\n");

    private String s;

    HardcodedValues(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

}
