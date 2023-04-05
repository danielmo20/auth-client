package com.itac.security.authclient.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SecurityRequest {
    private String clientId;
    private String clientSecret;
    private String scope;

}
