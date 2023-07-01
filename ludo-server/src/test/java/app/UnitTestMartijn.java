package app;
import app.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class UnitTestMartijn {
    private final WebClient client = WebClient.create();

    @Test
    void testToFetchOneUser() {
        String baseUrl = "https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io";
        String endpoint = "/users/1";

        String url = baseUrl + endpoint;

        try {
            HttpStatus status = client.get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .map(ResponseEntity::getStatusCode)
                    .block();

            assertEquals(HttpStatus.OK, status, "HTTP status should be OK");
        } catch (WebClientResponseException e) {
            fail("An exception occurred while making the request: " + e.getRawStatusCode());
        }

        try {
            ResponseEntity<String> responseEntity = client.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            assertNotNull(responseEntity, "Response entity should not be null");
            String response = responseEntity.getBody();
            assertNotNull(response, "Response body should not be null");
            System.out.println("Response: " + response);

        } catch (WebClientResponseException ex) {
            fail("Failed to retrieve user: " + ex.getMessage());
        }
    }
}
