/*
package com.userexprior.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AIServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    public AIServiceHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String aiServiceUrl =
            "http://localhost:8085/actuator/health";

    @Override
    public Health health() {

        try {

            ResponseEntity<String> response =
                    restTemplate.getForEntity(aiServiceUrl, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                return Health.up()
                        .withDetail("AI Service", "Running")
                        .withDetail("Status Code", response.getStatusCode().value())
                        .build();
            }

            return Health.down()
                    .withDetail("Status Code", response.getStatusCode().value())
                    .build();

        } catch (Exception ex) {
            return Health.down(ex)
                    .withDetail("AI Service", "Connection Failed")
                    .build();
        }
    }
}*/
