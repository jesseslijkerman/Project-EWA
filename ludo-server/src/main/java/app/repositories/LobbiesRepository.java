package app.repositories;

import app.models.Lobby;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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

    public List<Lobby> findAllJoinableLobbies(int userId){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_all_joinable_lobbies", Lobby.class);
        namedQuery.setParameter(1, userId);
        return namedQuery.getResultList();
    }



    public Lobby save(Lobby lobby){
        return entityManager.merge(lobby);
    }

    public Lobby deleteById(int id){
        Lobby user = findById(id);
        entityManager.remove(user);
        return user;
    }
}
