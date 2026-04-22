package com.webapp.arvand.coreback.Utils;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class WebClientUtil {
    private final WebClient webClient;

    public WebClientUtil(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T, R> R post(String url, T body, Map<String, String> headers, Class<R> responseType) {

        return webClient.post()
                .uri(url)
                .headers(h -> h.setAll(headers))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <R> R get(String url, Map<String, String> headers, Class<R> responseType) {

        return webClient.get()
                .uri(url)
                .headers(h -> h.setAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}
