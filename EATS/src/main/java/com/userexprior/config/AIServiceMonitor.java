package com.userexprior.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class AIServiceMonitor {

//    private final RestTemplate restTemplate = new RestTemplate();

    private volatile boolean aiServiceUp = false;

    @Autowired
    private   WebClient webClient;


    //@Scheduled(fixedDelay = 50000) // Every 10 seconds
    public void checkHealth() {

        try {

/*            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            "http://localhost:8085/actuator/health",
                            String.class);

            aiServiceUp = response.getStatusCode().is2xxSuccessful();

            System.out.println("AI Service Status : " + aiServiceUp);*/

            webClient.get()
                    .uri("http://localhost:8085/actuator/health")
                    .retrieve()
                    .bodyToMono(ResponseEntity.class)
                    .doOnError(error -> {
                        aiServiceUp = false;
                        System.out.println("AI Service DOWN");
                    })
                    .subscribe(responseBody -> {
                        aiServiceUp = responseBody.getStatusCode().is2xxSuccessful();
                        System.out.println("AI Service UP");
                    });

            /*HttpClient client = HttpClient.newHttpClient();

            HttpRequest request =
                    HttpRequest.newBuilder().GET()
                            .uri(URI.create("http://localhost:8085/actuator/health"))
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());*/

        } catch (Exception ex) {

            aiServiceUp = false;

            System.out.println("AI Service DOWN");
        }
    }

    public boolean isAiServiceUp() {
        return aiServiceUp;
    }
}
