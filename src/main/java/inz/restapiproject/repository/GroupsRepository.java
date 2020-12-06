package inz.restapiproject.repository;

import inz.restapiproject.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    @Query("SELECT g FROM Groups g WHERE g.name = ?1 AND g.users_id = ?2")
    List<Groups> findIdDefaultGroup(String defaultName, long idUser);

    @Query("SELECT g FROM Groups g WHERE g.users_id = ?1")
    List<Groups> findAllGroupsForUserId(long idUser);

    @Query("SELECT g.id FROM Groups g WHERE g.name = ?1 AND g.users_id = ?2")
    long findJustIdGroupForUserIdAndName(String name, long idUser);

    @Query("SELECT g.id FROM Groups g WHERE g.name = ?1 AND g.users_id = ?2")
    long findIdSeekGroup(String name, long iduser);

    @Query("SELECT g FROM Groups g WHERE g.name = ?1")
    List<Groups> checkExistOfGroup(String name);

    @Query("SELECT g FROM Groups g INNER JOIN Lights l ON g.id= ?1")
    List<Groups> test(long id);
}
