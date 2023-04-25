package app.repositories;

import app.models.Lobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class LobbiesRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Lobby> findAll(){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_all_lobbies", Lobby.class);
        return namedQuery.getResultList();
    }

    public Lobby findById(int id){
        return entityManager.find(Lobby.class, id);
    }

    public List<Lobby> findByUserId(int userId){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_lobbies_by_user_id", Lobby.class);
        namedQuery.setParameter(1, userId);
        return namedQuery.getResultList();
    }

    public Lobby save(Lobby user){
        return entityManager.merge(user);
    }

    public Lobby deleteById(int id){
        Lobby user = findById(id);
        entityManager.remove(user);
        return user;
    }
}
