package http;

public enum Header {

    CONTENTTYPE("Content-Type:");

    private String label;

    Header(String label) {
        this.label = label;
    }

    public byte[] getLabel() {
        return label.getBytes();
    }
}
