package com.itac.security.authclient.controller;

import com.itac.security.authclient.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RestController
public class BankController {

    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/banks")
    public List<Bank> listBanks(@RequestHeader("itac_token") String itacToken) {
        return this.webClient
                .get()
                .uri("http://localhost:8090/transaction/banks")
                .headers(h -> h.setBearerAuth(itacToken))

                // Descomentar para ignorar el BearerAuth(itacToken)
                // y generar un nuevo token para la petición
                //.attributes(clientRegistrationId("open-finance-client-credentials"))

                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Bank>>() {})
                .block();
    }

}
