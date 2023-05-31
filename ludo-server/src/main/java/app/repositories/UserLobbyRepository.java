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

    public List<UserLobby> findAllUsersInLobby(int id){
        TypedQuery<UserLobby> namedQuery = entityManager.createNamedQuery("find_users_in_lobby", UserLobby.class);
        namedQuery.setParameter(1, id);
        return namedQuery.getResultList();
    }

    public void removeUserFromLobby(String username, int lobbyId) {
        Query namedQuery = entityManager.createNamedQuery("remove_user_from_lobby");
        namedQuery.setParameter("username", username);
        namedQuery.setParameter("lobbyId", lobbyId);
        namedQuery.executeUpdate();
    }

    public UserLobby save(UserLobby userLobby){
        return entityManager.merge(userLobby);
    }

    public UserLobby findById(int id){
            return entityManager.find(UserLobby.class, id);
    }


    public UserLobby deleteById(int id){
            UserLobby lobby = findById(id);
            entityManager.remove(lobby);
            return lobby;
    }



    }
