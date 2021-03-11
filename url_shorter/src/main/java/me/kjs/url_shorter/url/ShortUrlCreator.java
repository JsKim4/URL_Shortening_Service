package me.kjs.url_shorter.url;

public interface ShortUrlCreator {
    String getHost();
    Integer getPort();
    String getResource();
    Protocol getProtocol();
}
