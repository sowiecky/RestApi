package inz.restapiproject.repository;


import inz.restapiproject.model.Lights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LightsRepository extends JpaRepository<Lights, Long> {

    @Query("SELECT l FROM Lights l")
    List<Lights> getLights(long idUser);

    @Query(value = "SELECT l.name FROM Lights l WHERE l.serial = ?1 LIMIT 1", nativeQuery = true)
    String getNameOfLightBySerial(String serial);

    @Query("SELECT l FROM Lights l WHERE l.serial = ?1")
    List<Lights> checkExistOfSerialLight(String serial);

    @Query("SELECT l.id FROM Lights l WHERE l.serial = ?1")
    long findIdSeekLightBySerial(String serial);

    @Query("SELECT l FROM Lights l WHERE l.id = ?1")
    Lights getLightById(long idLight);
}
