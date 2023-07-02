package app;

import app.controllers.LobbiesController;
import app.controllers.UserController;
import app.models.Lobby;
import app.models.User;
import app.repositories.UserLobbyRepository;
import app.repositories.LobbiesRepository;
import app.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTestTristan {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private UserLobbyRepository userLobbyRepository;

    @InjectMocks
    private LobbiesRepository lobbiesRepository;


    @Test
    public void testUpdateBoardHome() {
        // Arrange
        Long lobbyId = 123L;
        int playerNumber = 1;
        int whichHome = 2;
        int zeroOrOne = 1;

        when(entityManager.createNamedQuery(anyString())).thenReturn(query);

        // Act
        userLobbyRepository.updateBoardHome(lobbyId, playerNumber, whichHome, zeroOrOne);

        // Assert
        verify(entityManager).createNamedQuery("update_home");
        verify(query).setParameter("lobbyId", lobbyId);
        verify(query).setParameter("playerNumber", playerNumber);
        verify(query).setParameter("whichHome", whichHome);
        verify(query).setParameter("zeroOrOne", zeroOrOne);
        verify(query).executeUpdate();
    }

    @Test
    public void testUpdateBoard() {
        // Arrange
        Long id = 123L;
        String board = "['pR1','pR2','X','X',1,1,'pB1','X','X','pB1','pB2']" +
                ",['pR3','pR4','X','X',1,'pB1',1,'X','X','pB3','pB4']" +
                ",['X','X','X','X',1,'hB2',1,'X','X','X','X']" +
                ",['X','X','X','X',1,'hB3',1,'X','X','X','X']" +
                ",[1,'pR1',1,1,1,'hB4',1,1,1,1,1]" +
                ",[1,'hR1','hR2','hR3','hR4',0,'hY4','hY3','hY2','hY1',1]" +
                ",[1,1,1,1,1,'hG4',1,1,1,1,1]," +
                "['X','X','X','X',1,'hG3',1,'X','X','X','X']," +
                "['X','X','X','X',1,'hG2',1,'X','X','X','X']," +
                "['pG1','pG2','X','X',1,'hG1',1,'X','X','pY1','pY2']," +
                "['pG3','pG4','X','X',1,1,1,'X','X','pY3','pY4']";

        when(entityManager.createNamedQuery(anyString())).thenReturn(query);

        // Act
        lobbiesRepository.updateBoard(id, board);

        // Assert
        verify(entityManager).createNamedQuery("update_game_board");
        verify(query).setParameter(2, id);
        verify(query).setParameter(1, board);
        verify(query).executeUpdate();
    }

    @Test
    void rollDiceShouldReturnNumberBetween1And6() {
        LobbiesRepository repository = new LobbiesRepository();

        // Rolls dice 10 times
        for (int i = 0; i < 10; i++) {
            int result = repository.rollDice();

            assertTrue(result >= 1 && result <= 6, "The dice roll should be between 1 and 6.");
        }
    }


    @Test
    void testIfFriendInviteReturnsOKResponse() {
        // Arrange
        Long userId = 1L;
        Long friendId = 2L;
        Long matchId = 3L;
        User user = new User();
        user.setId(userId);
        User friend = new User();
        friend.setId(friendId);

        when(usersRepository.findById(userId)).thenReturn(user);
        when(usersRepository.findById(friendId)).thenReturn(friend);

        // Act
        ResponseEntity<String> response = userController.inviteToGame(userId, friendId, matchId);

        // Assert
        assertEquals(ResponseEntity.ok("Invite email sent"), response);
        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, times(1)).findById(friendId);
        verify(usersRepository, times(1)).inviteToGame(friend, user, matchId);
    }

    @Test
    void testIfTurnIncreasedCorrectly(){
        // Arrange
        Lobby lobby = new Lobby();
        lobby.setId(123L);
        lobby.setWhoseTurn(1);

        when(entityManager.createNamedQuery(anyString())).thenReturn(query);

        // Act
        lobbiesRepository.updateTurn(lobby.getId());

        // Assert
        verify(entityManager).createNamedQuery("update_lobby_turn");
        verify(query).setParameter("lobbyId", lobby.getId());
        verify(query).executeUpdate();

    }
}
