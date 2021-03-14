package me.kjs.url_shorter.url.dto;

import me.kjs.url_shorter.url.type.Protocol;

public interface ShortUrlCreator {
    String getHost();
    Integer getPort();
    String getResource();
    Protocol getProtocol();
}
