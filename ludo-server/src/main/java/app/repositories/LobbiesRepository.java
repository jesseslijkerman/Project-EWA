package app.repositories;

import app.models.Lobby;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class LobbiesRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Lobby> findAll(){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_all_lobbies", Lobby.class);
        return namedQuery.getResultList();
    }

    public Lobby findById(Long id){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_lobby_by_id", Lobby.class);
        return namedQuery.setParameter("id", id).getSingleResult();
    }


    public int rollDice(){
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;
        return randomNumber;
    }

    public void updateTurn(Long id){
        Query namedQuery = entityManager.createNamedQuery("update_lobby_turn");
        namedQuery.setParameter("lobbyId", id);
        namedQuery.executeUpdate();
    }

    public void updateBoard(Long id, String board){
        Query namedQuery = entityManager.createNamedQuery("update_game_board");
        namedQuery.setParameter(2, id);
        namedQuery.setParameter(1, board);
        namedQuery.executeUpdate();
    }


    public List<Lobby> findByUserId(Long userId){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_lobbies_by_user_id", Lobby.class);
        namedQuery.setParameter(1, userId);
        return namedQuery.getResultList();
    }



    public List<Lobby> findAllJoinableLobbies(Long userId){
        TypedQuery<Lobby> namedQuery = entityManager.createNamedQuery("find_all_joinable_lobbies", Lobby.class);
        namedQuery.setParameter(1, userId);
        return namedQuery.getResultList();
    }



    public Lobby save(Lobby lobby){
        return entityManager.merge(lobby);
    }

    public Lobby deleteById(Long id){
        Lobby user = findById(id);
        entityManager.remove(user);
        return user;
    }
}
