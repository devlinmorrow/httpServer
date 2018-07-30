package http;

public enum Header {

    CONTENTTYPE("Content-Type"),
    ALLOW("Allow");

    private String label;

    Header(String label) {
        this.label = label;
    }

    public byte[] getLabel() {
        return label.getBytes();
    }
}
