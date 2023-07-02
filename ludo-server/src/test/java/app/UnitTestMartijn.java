package app;

import app.models.Lobby;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import app.repositories.LobbiesRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class UnitTestMartijn {


    private final WebClient client = WebClient.create();

    @Autowired
    private LobbiesRepository lobbiesRepository;

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

            System.out.println("Board State: " + boardState); 

            // Verify the character count
            int characterCount = countCharacters(boardState);
            int expectedCharacterCount = 150; //amount of characters that are in the boardstate string

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

    @Test
    public void testRollDice() {
        // Invoke the rollDice method
        int diceRoll = lobbiesRepository.rollDice();

        System.out.println("Rolled dice: " + diceRoll);

        // Assert that the diceRoll is within the expected range (1 to 6)
        Assertions.assertTrue(diceRoll >= 1 && diceRoll <= 6, "Dice roll should be between 1 and 6");
    }

    @Test
    public void testFindAllJoinableLobbies() {
        Lobby lobby1 = new Lobby();
        lobby1.setId(191L);
        lobby1.setGameMode(1);
        lobby1.setMaxPlayers(2);
        lobby1.setName("juup");
        lobby1.setPassword(null);
        lobby1.setStatus("INACTIVE");
        lobby1.setTurnTimer(20);
        lobby1.setWhoseTurn(2);
        lobby1.setBoardState("['pR1','pR2','X','X',1,1,'pB1','X','X','pB1','pB2'],['pR3','pR4','X','X',1,'pB1',1,'X','X','pB3','pB4'],['X','X','X','X',1,'hB2',1,'X','X','X','X'],['X','X','X','X',1,'hB3',1,'X','X','X','X'],[1,'pR1',1,1,1,'hB4',1,1,1,1,1],[1,'hR1','hR2','hR3','hR4',0,'hY4','hY3','hY2','hY1',1],[1,1,1,1,1,'hG4',1,1,1,1,1],['X','X','X','X',1,'hG3',1,'X','X','X','X'],['X','X','X','X',1,'hG2',1,'X','X','X','X'],['pG1','pG2','X','X',1,'hG1',1,'X','X','pY1','pY2'],['pG3','pG4','X','X',1,1,1,'X','X','pY3','pY4']");
        lobbiesRepository.save(lobby1);

        Lobby lobby2 = new Lobby();
        lobby2.setId(192L);
        lobby2.setGameMode(1);
        lobby2.setMaxPlayers(2);
        lobby2.setName("yep1");
        lobby2.setPassword(null);
        lobby2.setStatus("INACTIVE");
        lobby2.setTurnTimer(23);
        lobby2.setWhoseTurn(1);
        lobby2.setBoardState("['pR1','pR2','X','X','pB1',1,1,'X','X','pB1','pB2'],['X','X','X','X',1,'hB2',1,'X','X','X','X'],['X','X','X','X',1,'hB3',1,'X','X','X','X'],[1,'pR1',1,1,1,'hB4',1,1,1,1,1],[1,'hR1','hR2','hR3','hR4',0,'hY4','hY3','hY2','hY1',1],[1,1,1,1,1,'hG4',1,1,1,1,1],['X','X','X','X',1,'hG3',1,'X','X','X','X'],['X','X','X','X',1,'hG2',1,'X','X','X','X'],['pG1','pG2','X','X',1,'hG1',1,'X','X','pY1','pY2'],['pG3','pG4','X','X',1,1,1,'X','X','pY3','pY4']");
        lobbiesRepository.save(lobby2);

        Long userId = 123L;

        // Retrieve all joinable lobbies for a user
        List<Lobby> lobbies = lobbiesRepository.findAllJoinableLobbies(userId);


        for (Lobby lobby : lobbies) {
            Assertions.assertEquals("INACTIVE", lobby.getStatus(), "Lobby should be joinable");
        }
    }

    @Test
    void testAuthenticateUser() {
        String baseUrl = "https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io";
        String loginEndpoint = "/usersAuth/login";
        String logoutEndpoint = "/usersAuth/logout";

        String loginUrl = baseUrl + loginEndpoint;
        String logoutUrl = baseUrl + logoutEndpoint;

        JsonObject loginBody = new JsonObject();
        loginBody.addProperty("email", "testvoorJUnit@lol.com");
        loginBody.addProperty("password", "eenpassword");

        try {
            // Test login
            ResponseEntity<String> loginResponse = client.post()
                    .uri(loginUrl)
                    .header("Content-Type", "application/json")
                    .body(BodyInserters.fromValue(loginBody.toString()))
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            assertEquals(HttpStatus.ACCEPTED, loginResponse.getStatusCode(), "HTTP status should be ACCEPTED");

            System.out.println("Login status: " + loginResponse.getStatusCode());
            System.out.println("Login response body: " + loginResponse.getBody());



            ResponseEntity<String> logoutResponse = client.post()
                    .uri(logoutUrl)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            assertEquals(HttpStatus.OK, logoutResponse.getStatusCode(), "HTTP status should be OK");
        } catch (WebClientResponseException e) {
            fail("An exception occurred while making the request: " + e.getRawStatusCode());
        }
    }

}
