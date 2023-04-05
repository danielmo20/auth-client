package com.itac.security.authclient.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.itac.security.authclient.controller.dto.SecurityRequest;
import lombok.AllArgsConstructor;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.GRANT_TYPE;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.SCOPE;

@RestController
@AllArgsConstructor
public class SecurityController {

    @PostMapping(value = "/token")
    public JsonNode getToken(@RequestBody SecurityRequest request) {
        return this.generateToken(request);

    }

    private JsonNode generateToken(SecurityRequest request) {

        String basicCredentials = Base64Utils.encodeToString(
                (request.getClientId()+":"+request.getClientSecret()
                ).getBytes());

        return WebClient.builder().build()
                .post()
                .uri("http://auth-server:9000/oauth2/token")
                .header("Authorization", "Basic " + basicCredentials)
                .body(
                        BodyInserters.fromFormData(GRANT_TYPE, CLIENT_CREDENTIALS.getValue())
                                .with(SCOPE, request.getScope()))

                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

    }
}
