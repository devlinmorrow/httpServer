public enum RequestMethod {

    GET("GET");

    private String StringRep;

    RequestMethod(String StringRep) {
        this.StringRep = StringRep;
    }

    public String getStringRep() {
        return StringRep;
    }

}
