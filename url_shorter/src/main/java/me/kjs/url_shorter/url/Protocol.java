package me.kjs.url_shorter.url;

public enum Protocol {
    HTTP("http://"),
    HTTPS("https://");
    private final String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
