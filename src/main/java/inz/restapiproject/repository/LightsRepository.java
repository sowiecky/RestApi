package inz.restapiproject.repository;


import inz.restapiproject.model.Lights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LightsRepository extends JpaRepository<Lights, Long> {

//    @Query("SELECT l.name, g.name, l.serial FROM Users u INNER JOIN Groups g ON g.users_id = u.id INNER JOIN Groups_has_lights ghl ON ghl.groups_id=g.id INNER JOIN Lights l ON l.id=ghl.lights_id WHERE u.id = ?1")
//    List<Lights> getLights(long idUser);

    @Query("SELECT l FROM Lights l")
    List<Lights> getLights(long idUser);

    @Query(value = "SELECT l.name FROM Lights l WHERE l.serial = ?1 LIMIT 1", nativeQuery = true)
    String getNameOfLightBySerial(String serial);

    @Query("SELECT l FROM Lights l WHERE l.serial = ?1 AND l.name = ?2")
    List<Lights> checkExistOfLight(String serial, String name);
}
