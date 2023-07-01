package app;

import app.models.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class UnitTestJesse {

    private WebClient client = WebClient.create();

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @BeforeEach
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    @Test
    void canRetrieveAllLobbies(){
//        ResponseEntity<Lobby[]> response = this.restTemplate.getForEntity("/lobbies", Lobby[].class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        Lobby[] lobbies = response.getBody();
//        assertThat(lobbies.length, is(greaterThan(0)));

        String url = "https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io/lobbies";

        try{
            HttpStatus status = client.get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .map(ResponseEntity::getStatusCode)
                    .block();

            assertEquals(HttpStatus.OK, status, "HTTP status should be OK");
        } catch (WebClientResponseException e){
            fail("An exception occurred while making the request: " + e.getRawStatusCode());
        }
    }



}
