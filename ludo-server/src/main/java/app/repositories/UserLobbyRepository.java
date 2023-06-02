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

    public List<String> findAllUsersInLobby(Long id){
        TypedQuery<String> namedQuery = entityManager.createNamedQuery("find_users_in_lobby", String.class);
        namedQuery.setParameter(1, id);
        return namedQuery.getResultList();
    }

    public void removeUserFromLobby(String username, Long lobbyId) {
        Query namedQuery = entityManager.createNamedQuery("remove_user_from_lobby");
        namedQuery.setParameter("username", username);
        namedQuery.setParameter("lobbyId", lobbyId);
        namedQuery.executeUpdate();
    }

    public Long getTurn(Long id){
            TypedQuery<Long> namedQuery = entityManager.createNamedQuery("get_userId_turn", Long.class);
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
