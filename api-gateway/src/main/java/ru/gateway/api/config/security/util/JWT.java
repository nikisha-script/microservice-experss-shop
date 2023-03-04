package ru.gateway.api.config.security.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JWT {

    KEY("jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4"),
    HEADER("Authorization"),
    ACCESS_TOKEN_EXPIRATION("6000000"),
    REFRESH_TOKEN_EXPIRATION("18000000"),
    UUID("uuid");

    private final String value;

}
