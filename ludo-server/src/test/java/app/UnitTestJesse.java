package app;

import app.models.Lobby;
import app.models.User;
import app.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.swing.text.html.parser.Entity;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class UnitTestJesse {

    @Autowired
    private UsersRepository usersRepo;

    private WebClient client = WebClient.create();

    @Test
    void canRetrieveAllLobbies(){
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

        try {
            String response = client.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Assertions.assertNotNull(response, "Response should not be null");
            // Add additional assertions or checks on the response if needed

        } catch (WebClientResponseException ex) {
            Assertions.fail("Failed to retrieve lobbies: " + ex.getMessage());
        }
    }

    @Test
    void canAddLobby(){
        String backendUrl = "https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io/lobbies";
        String requestBody = "{\n" +
                "    \"maxPlayers\": 2,\n" +
                "    \"turnTimer\": 20,\n" +
                "    \"gameMode\": 1,\n" +
                "    \"name\": \"juup\",\n" +
                "    \"password\": null,\n" +
                "    \"created\": \"2023-06-15T14:04:39\",\n" +
                "    \"status\": \"ACTIVE\",\n" +
                "    \"whoseTurn\": 2,\n" +
                "    \"boardState\": \"['pR1','pR2','X','X',1,1,'pB1','X','X','pB1','pB2'],['pR3','pR4','X','X',1,'pB1',1,'X','X','pB3','pB4'],['X','X','X','X',1,'hB2',1,'X','X','X','X'],['X','X','X','X',1,'hB3',1,'X','X','X','X'],[1,'pR1',1,1,1,'hB4',1,1,1,1,1],[1,'hR1','hR2','hR3','hR4',0,'hY4','hY3','hY2','hY1',1],[1,1,1,1,1,'hG4',1,1,1,1,1],['X','X','X','X',1,'hG3',1,'X','X','X','X'],['X','X','X','X',1,'hG2',1,'X','X','X','X'],['pG1','pG2','X','X',1,'hG1',1,'X','X','pY1','pY2'],['pG3','pG4','X','X',1,1,1,'X','X','pY3','pY4']\"\n" +
                "}";

        try {
            ClientResponse response = client.post()
                    .uri(backendUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .exchange()
                    .block();

            Assertions.assertNotNull(response, "Response should not be null");
            Assertions.assertEquals(HttpStatus.CREATED, response.statusCode(), "HTTP status should be 200 OK");

        } catch (WebClientResponseException ex) {
            Assertions.fail("Failed to add lobby: " + ex.getMessage());
        }
    }

    @Test
    void canInvitePlayer(){
        // Set up the test data
        int userId = 12;
        int friendId = 15;
        int matchId = 232;

        String apiUrl = String.format("https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io/users/%d/invite/%d/%d",
                userId, friendId, matchId);

        try {
            // Perform the API call
            String responseBody = client.post()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Verify the response
            Assertions.assertNotNull(responseBody, "Response should not be null");
            Assertions.assertEquals("Invite email sent", responseBody, "Response body should match");

        } catch (WebClientResponseException ex) {
            Assertions.fail("API request failed: " + ex.getMessage());
        }
    }

    @Test
    void repoAddFriend(){
        // Set up the test data
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("user123");

        User friend = new User();
        friend.setEmail("friend@example.com");
        friend.setUsername("friend456");

        // Perform the method call
        User result = usersRepo.addFriend(user, friend);

        // Verify the result
        Assertions.assertNotNull(result, "Result should not be null");
        Assertions.assertEquals(user, result, "Result should be the same instance as user");

        // Verify the user's friends list
        List<User> friends = user.getFriends();
        Assertions.assertTrue(friends.contains(friend), "User should have the friend added");
        Assertions.assertEquals(1, friends.size(), "User's friends list size should be 1");

        usersRepo.addFriend(friend, user);

        // Verify the friend's friends list
        List<User> friendFriends = friend.getFriends();
        Assertions.assertTrue(friendFriends.contains(user), "Friend should have the user added");
        Assertions.assertEquals(1, friendFriends.size(), "Friend's friends list size should be 1");
    }

    @Test
    void repoFindFriendsByUserId(){
        // Set up the test data
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setUsername("user123");

        User friend1 = new User();
        friend1.setEmail("friend1@example.com");
        friend1.setUsername("friend1");

        User friend2 = new User();
        friend2.setEmail("friend2@example.com");
        friend2.setUsername("friend2");

        user.addFriend(friend1);
        user.addFriend(friend2);

        List<User> friends = usersRepo.findFriendsByUserId(1L);

        Assertions.assertNotNull(friends, "Result should not be null");
        Assertions.assertEquals(2, friends.size(), "User should have 2 friends");
    }


}
