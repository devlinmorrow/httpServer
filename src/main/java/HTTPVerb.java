public enum HTTPVerb {

    GET("GET");

    private String StringRep;

    HTTPVerb(String StringRep) {
        this.StringRep = StringRep;
    }

    public String getStringRep() {
        return StringRep;
    }

}
