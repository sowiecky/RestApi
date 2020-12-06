package inz.restapiproject.controller;


import inz.restapiproject.model.Groups;
//import inz.restapiproject.model.GroupsHasLights;
import inz.restapiproject.model.Lights;
import inz.restapiproject.model.Users;
//import inz.restapiproject.service.GroupsHasLightsService;
import inz.restapiproject.service.GroupsService;
import inz.restapiproject.service.LightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        //TEST
        //String id = "100";
        //long idl = Long.parseLong(id);
        //List<Groups> test = groupsService.test(idl);
        //System.out.println(test);
        //KONIEC TEST nie dziala


        //zamiana idUsera ze String na long
        long idUser = Long.parseLong(user_id);
        //szukanie idGrupy dla danej jej nazwy i usera
        long seekIdGroup = groupsService.findIdSeekGroup(name, idUser);
        //Pobranie listy wszystkich grup dla podanego idUsera
        List<Groups> groupsList = groupsService.findAllGroupsForUserId(idUser);


        Lights lights = new Lights();
        lights.setSerial(serial);
        String nameLight = lightsService.findNameOfLight(serial);
        lights.setName(nameLight);

        lightsService.savelight(lights);

        //dla kazdego elementu z groupsList
        for (Groups group: groupsList) {
            if(group.getId() == seekIdGroup) {
                //dodaje do hashset
                group.getLights().add(lights);
                //zapisuje w bd
                groupsService.saveGroup(group);
                break;
            }
        }
        return "Saved";
    }

    @GetMapping("/get")
    public @ResponseBody List<Lights> getLights(@RequestParam String user_id){

            long idUser = Long.parseLong(user_id);

            return lightsService.findLights(idUser);
    }

}
