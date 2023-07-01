package app;
import app.models.User;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Test
    void testCorrectBoardSizeCount() {
        String baseUrl = "https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io";
        String endpoint = "/lobbies/191";

        String url = baseUrl + endpoint;

        try {
            ResponseEntity<String> responseEntity = client.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            assertNotNull(responseEntity, "Response entity should not be null");
            String boardState = responseEntity.getBody();
            assertNotNull(boardState, "Board state should not be null");

            System.out.println("Board State: " + boardState); // Print the board state to the console

            // Verify the character count
            int characterCount = countCharacters(boardState);
            int expectedCharacterCount = 150; // Update with the expected character count

            assertEquals(expectedCharacterCount, characterCount, "Character count should match");

        } catch (WebClientResponseException ex) {
            fail("Failed to retrieve board state: " + ex.getMessage());
        }
    }

    private int countCharacters(String responseJson) {
        // Parse the JSON response to a JsonObject
        JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();

        // Get the board state from the JsonObject
        JsonElement boardStateElement = jsonObject.get("boardState");
        if (boardStateElement == null || !boardStateElement.isJsonPrimitive()) {
            throw new IllegalArgumentException("Invalid board state format");
        }

        // Extract the board state string
        String boardState = boardStateElement.getAsString();

        // Use a regular expression to find all game elements
        Matcher m = Pattern.compile("'([^']*)'").matcher(boardState);

        // Count all alphanumeric characters in the game elements
        int characterCount = 0;
        while (m.find()) {
            String gameElement = m.group(1); // Extract the game element without the quotes
            characterCount += gameElement.length(); // Count the characters in the game element
        }

        return characterCount;
    }
}
