package app.repositories;

import app.models.User;

import app.services.EmailService;
import app.services.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UsersRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private EmailService emailService;

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
        TypedQuery<User> query = this.entityManager.createNamedQuery("find_user_by_email_or_username", User.class).setParameter("param", email);

        return (User) query.getSingleResult();
    }

    public User deleteById(Long id){
        User user = findById(id);
        entityManager.remove(user);
        return user;
    }

    public User signup(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return save(user);
    }

    public User updatePassword(Long userId, String newPassword){
        Query query = entityManager.createNamedQuery("reset_password");
        String hashedPassword = passwordEncoder.encode(newPassword);
        query.setParameter("newPassword", hashedPassword);
        query.setParameter("userId", userId);
        query.executeUpdate();

        return findById(userId);
    }


    /**
     * findIdByToken is used to get the id to reset the password from the user with that id
     */
    public User findIdByToken(String token){
        TypedQuery<User> query = this.entityManager.createNamedQuery("findIdByToken", User.class).setParameter("tokenParam", token);
        return  query.getSingleResult();
    }


    /**
     * resetPassword is used to reset the password and token. This way the token is null again and not able to reset the password again
     */
    public User resetPassword(Long userId, String newPassword){
        Query query = entityManager.createNamedQuery("reset_password_and_token");
        String hashedPassword = passwordEncoder.encode(newPassword);
        query.setParameter("newPassword", hashedPassword);
        query.setParameter("userId", userId);
        query.executeUpdate();

        return findById(userId);
    }


    public User addFriend(User user, User friend){
        user.addFriend(friend);
        return user;
    }

    public User removeFriend(User user, User friend){
        user.removeFriend(friend);
        return user;
    }

    public List<User> findFriendsByUserId(Long userId) {
        Query query = entityManager.createNamedQuery("find_friends_by_user_id");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public void inviteToGame(User invitedUser, User user, Long matchId){
        JavaMailSender javaMailSender = mailConfig.javaMailSender();
        emailService.sendGameInvite(javaMailSender, invitedUser.getEmail(), user.getUsername(), matchId);
    }

}
