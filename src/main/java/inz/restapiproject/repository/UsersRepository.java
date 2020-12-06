package inz.restapiproject.repository;

import inz.restapiproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.login = ?1 AND u.password = ?2")
    List<Users> findByLoginAndPassword(String login, String password);

    @Query("SELECT u FROM Users u WHERE u.email = ?1 AND u.password = ?2")
    List<Users> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM Users u WHERE u.login = ?1")
    List<Users> findByLogin(String login);

    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    List<Users> findByEmail(String email);


}
