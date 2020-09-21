package com.github.tt4g.spring.additional.configuration.file.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ShowConfigController {

    private final String additionalMessage;

    private final String additionalMessageRef;

    @Autowired
    public ShowConfigController(
        @Value("${additional.message}") String additionalMessage,
        @Value("${additional.message-ref}") String additionalMessageRef) {

        this.additionalMessage = additionalMessage;
        this.additionalMessageRef = additionalMessageRef;
    }

    @GetMapping("showMessage")
    public Mono<ResponseEntity<Map<String, String>>> showMessage() {
        Map <String, String> keyToMessage =
            Map.of(
                "additional.message", this.additionalMessage,
                "additional.message-ref", this.additionalMessageRef);

        return Mono.just(
            ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(keyToMessage));
    }

}
