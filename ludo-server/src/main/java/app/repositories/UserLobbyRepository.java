package app.repositories;

import app.models.Lobby;
import app.models.UserLobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    @Transactional
    public class UserLobbyRepository {
        @PersistenceContext
        EntityManager entityManager;

        public List<UserLobby> findAll(){
            TypedQuery<UserLobby> namedQuery = entityManager.createNamedQuery("find_all_userLobbies", UserLobby.class);
//            namedQuery.setMaxResults(1);
            return namedQuery.getResultList();
        }

        public UserLobby findById(int id){
            return entityManager.find(UserLobby.class, id);
        }

        public UserLobby save(UserLobby lobby){
            return entityManager.merge(lobby);
        }

        public UserLobby deleteById(int id){
            UserLobby lobby = findById(id);
            entityManager.remove(lobby);
            return lobby;
        }
    }





@Repository
@Transactional
public class UserLobbyRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<UserLobby> findAll(){
        TypedQuery<UserLobby> namedQuery = entityManager.createNamedQuery("find_all_user_lobbies", UserLobby.class);
        return namedQuery.getResultList();
    }

    public UserLobby save(UserLobby userLobby){
        return entityManager.merge(userLobby);
    }
}
