package app;

import app.models.User;
import app.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class UnitTestCalvin {


    private WebClient client = WebClient.create();

    @Autowired
    private UsersRepository usersRepo;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void repoResetPassword() {
        // Create a test user with an initial password
        User user = new User();
        user.setId(1L);
        user.setPassword(passwordEncoder.encode("password123"));

        // Save the user to the repository
        usersRepo.save(user);

        // Define the new password
        String newPassword = "newPassword456";

        // Update the password directly using the setter
        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = usersRepo.save(user);

        // Verify the updated password
        assertNotNull(updatedUser, "User should not be null");
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()), "Password should be updated");
    }

    @Test
    public void findByIdByToken(){
        // Create a sample user
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("user123");
        user.setId(62L);
        user.setResetPasswordToken("testToken");
        usersRepo.save(user);

        // Call the method to be tested
        User foundUser = usersRepo.findIdByToken("testToken");

        // Assertions
        assertEquals(user.getId(), foundUser.getId(), "User IDs should match");
    }

    @Test
    public void testResetPasswordWithToken(){
        // Create a test user with an initial password
        User user = new User();
        user.setId(1L);
        String initialPassword = "password123";
        String token = "ExampleToken";
        user.setPassword(passwordEncoder.encode(initialPassword));
        usersRepo.save(user);

        // Define the new password
        String newPassword = "newPassword456";

        // Call the resetPassword method
        User updatedUser = usersRepo.resetPassword(user.getId(), newPassword);

        // Verify the updated password
        assertNotNull(updatedUser, "User should not be null");
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()), "Password should be updated");
        assertEquals(null, updatedUser.getResetPasswordToken(), "Password token should be null") ;
    }

    @Test
    public void canRetrieveAllUsers(){
        String url = "https://pauperzooi.agreeablemeadow-c9c78c36.westeurope.azurecontainerapps.io/users";


        try {
            HttpStatus status = client.get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .map(ResponseEntity::getStatusCode)
                    .block();

            Assertions.assertEquals(HttpStatus.OK, status, "HTTP status should be OK");

            String response = client.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Assertions.assertNotNull(response, "Response should not be null");

        } catch (HttpClientErrorException ex) {
            Assertions.fail("Client error occurred: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
        } catch (HttpServerErrorException ex) {
            Assertions.fail("Server error occurred: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
        } catch (Exception ex) {
            Assertions.fail("An exception occurred while making the request: " + ex.getMessage());
        }
    }

    @Test
    public void asd(){

    }
}