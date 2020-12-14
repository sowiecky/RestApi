package inz.restapiproject.service;


import inz.restapiproject.model.Groups;
import inz.restapiproject.model.Lights;
import inz.restapiproject.repository.LightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class LightsService {

    @Autowired
    LightsRepository lightsRepository;

    //Zwraca wszystkie urzadzenia
    public List<Lights> getUsers(){
        return lightsRepository.findAll();
    }

    //Dodanie zarowki
    //(LightsContoller)
    public void savelight(Lights n) {
        lightsRepository.save(n);
    }

    //Zwraca wszystkie zarowki
    //(LightsController)
    public List<Lights> findLights(long idUser) {
        return lightsRepository.getLights(idUser);
    }

    public String findNameOfLight(String serial) {
        return lightsRepository.getNameOfLightBySerial(serial);
    }

    //Sprawdzenie czy urzadzenie znajduje sie juz w BD
    //(LightsController)
    public List<Lights> checkExistOfLight(String serial, String name) {
        return lightsRepository.checkExistOfLight(serial, name);
    }


    public long findIdSeekLightBySerial(String serial) {
        return lightsRepository.findIdSeekLightBySerial(serial);
    }

    public Lights getLightById(long idLight) {
        return lightsRepository.getLightById(idLight);
    }

    public void deleteLight(Lights light) {
        lightsRepository.delete(light);
    }
}
