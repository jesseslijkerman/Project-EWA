package app.repositories;

import app.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UsersRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<User> findAll(){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("find_all_users", User.class);
        return namedQuery.getResultList();
    }

    public User findById(int id){
        return entityManager.find(User.class, id);
    }

    public User save(User user){
        return entityManager.merge(user);
    }

    public User deleteById(int id){
        User user = findById(id);
        entityManager.remove(user);
        return user;
    }
}
