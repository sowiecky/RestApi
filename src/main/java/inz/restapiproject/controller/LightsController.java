package inz.restapiproject.controller;


import inz.restapiproject.model.Groups;
//import inz.restapiproject.model.GroupsHasLights;
import inz.restapiproject.model.Lights;
import inz.restapiproject.model.Users;
//import inz.restapiproject.service.GroupsHasLightsService;
import inz.restapiproject.service.GroupsService;
import inz.restapiproject.service.LightsService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/lights")
public class LightsController {

    @Autowired
    LightsService lightsService;

    @Autowired
    GroupsService groupsService;

//    @Autowired
//    GroupsHasLightsService groupsHasLightsService;

    /*
    @GetMapping("/all")
    public @ResponseBody
    List<Lights> getLights(){
        return lightsService.getUsers();
    }

     */

    @PostMapping("/add")
    public @ResponseBody String addLight(@RequestParam String serial, @RequestParam String name, @RequestParam String user_id){

            long idUser = Long.parseLong(user_id);

            if(lightsService.checkExistOfLight(serial, name).isEmpty()) {
                //Dodanie światła
                Lights n = new Lights();
                n.setSerial(serial);
                n.setName(name);

                lightsService.savelight(n);

                //Pobranie id swiatla
                long idLight = n.getId();

                //Znalezienie id defaultowej grupy dla podanego idUsera
                long defaultGroupId = groupsService.findIdDefaultGroup("Wszystkie urzadzenia", idUser);
                System.out.println(defaultGroupId);

                //Pobranie listy wszystkich grup dla podanego idUsera
                List<Groups> groupsList = groupsService.findAllGroupsForUserId(idUser);

                //dla kazdego elementu z groupsList
                for (Groups group : groupsList) {
                    //sprawdza czy id grupy zgadza sie z id"Wszystkie urz"
                    if (group.getId() == defaultGroupId) {
                        //dodaje do hashset
                        group.getLights().add(n);
                        //zapisuje w bd
                        groupsService.saveGroup(group);
                        break;
                    }
                }
                return "Saved";
            }else {
                return "Light_exists";
            }
    }


    @PostMapping("/addLightToGroup")
    public @ResponseBody String addLightToGroup(@RequestParam String serial, @RequestParam String name, @RequestParam String user_id){

        long idGroup = groupsService.findIdSeekGroup(name, Long.parseLong(user_id));
        Groups group = groupsService.getGroupById(idGroup);

        Set<Lights> lights  = groupsService.getGroupById(groupsService.findIdDefaultGroup("Wszystkie urzadzenia", Long.parseLong(user_id))).getLights(); //wszystkie urządzenia uzytkownika

        for(Lights singleLight: lights) {
            if (singleLight.getSerial().equals(serial)) {           //wyszukiwanie żarówki o podanym serialu
                if (!group.getLights().contains(singleLight)) {     //Jeśli żarówka nie jest w podanej grupie - dodaj
                    group.getLights().add(singleLight);             //tworzenie połączenia w tabeli łączącej
                    groupsService.saveGroup(group);                 //zapis w bazie
                    return "saved";
                }
            }
        }
        return "light_exists_in_group";
    }

    @GetMapping("/get")
    public @ResponseBody List<Lights> getLights(@RequestParam String user_id){

            long idUser = Long.parseLong(user_id);

            return lightsService.findLights(idUser);
    }



}
