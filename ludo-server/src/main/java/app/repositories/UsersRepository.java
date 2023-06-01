package app.repositories;

import app.models.User;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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

    public User findById(Long id){
        return entityManager.find(User.class, id);
    }

    public User save(User user){
        return entityManager.merge(user);
    }

    public User findByEmail(String email){
        TypedQuery<User> query = this.entityManager.createNamedQuery("findUserByEmail", User.class).setParameter("emailParam", email);
        return (User) query.getSingleResult();
    }

    public User deleteById(Long id){
        User user = findById(id);
        entityManager.remove(user);
        return user;
    }
}
