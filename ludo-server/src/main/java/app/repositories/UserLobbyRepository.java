package app.repositories;

import app.models.UserLobby;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserLobbyRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<UserLobby> findAll(){
        TypedQuery<UserLobby> namedQuery = entityManager.createNamedQuery("find_all_user_lobbies", UserLobby.class);
        return namedQuery.getResultList();
    }

    public List<String> findAllUsersInLobby(Long lobbyId){
        TypedQuery<String> namedQuery = entityManager.createNamedQuery("find_users_in_lobby", String.class);
        namedQuery.setParameter(1, lobbyId);
        return namedQuery.getResultList();
    }

    public int getPlayerNumber(Long lobbyId, Long userId){
        TypedQuery<Integer> namedQuery = entityManager.createNamedQuery("get_player_number", Integer.class);
        namedQuery.setParameter("lobbyId", lobbyId);
        namedQuery.setParameter("userId", userId);
        return namedQuery.getSingleResult();
    }

    public void updateBoardHome(Long lobbyId, int playerNumber, int whichHome, int zeroOrOne){
        Query namedQuery = entityManager.createNamedQuery("update_home");
        namedQuery.setParameter("lobbyId", lobbyId);
        namedQuery.setParameter("playerNumber", playerNumber);
        namedQuery.setParameter("whichHome", whichHome);
        namedQuery.setParameter("zeroOrOne", zeroOrOne);
        namedQuery.executeUpdate();
    }

    public void removeUserFromLobby(Long userId, Long lobbyId) {
        Query namedQuery = entityManager.createNamedQuery("remove_user_from_lobby");
        namedQuery.setParameter("userId", userId);
        namedQuery.setParameter("lobbyId", lobbyId);
        namedQuery.executeUpdate();
    }

    public Long getTurn(Long id){
            TypedQuery<Long> namedQuery = entityManager.createNamedQuery("get_userId_turn", Long.class);
            namedQuery.setParameter("lobbyId", id);
            return namedQuery.getSingleResult();
    }

    public Integer getCurrentTurn(Long id){
        TypedQuery<Integer> namedQuery = entityManager.createNamedQuery("get_current_turn", Integer.class);
        namedQuery.setParameter("lobbyId", id);
        return namedQuery.getSingleResult();
    }

    public UserLobby save(UserLobby userLobby){
        return entityManager.merge(userLobby);
    }

    public List<UserLobby> findById(Long id){

        TypedQuery<UserLobby> namedQuery = entityManager.createNamedQuery("find_user_lobby", UserLobby.class);
        namedQuery.setParameter(1, id);
        return namedQuery.getResultList();
    }


    public UserLobby deleteById(Long id){
            UserLobby lobby = (UserLobby) findById(id);
            entityManager.remove(lobby);
            return lobby;
    }
}
