package inz.restapiproject.controller;


import inz.restapiproject.model.Groups;
import inz.restapiproject.model.Users;
import inz.restapiproject.repository.UsersRepository;
import inz.restapiproject.service.GroupsService;
import inz.restapiproject.service.MqttService;
import inz.restapiproject.service.UsersService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MqttService mqttService;
    @Autowired
    private GroupsService groupsService;

    //Do zwrocenia wszystkich userow
    @GetMapping("/all")
    public @ResponseBody
    List<Users> getUsers(){
        return usersService.getUsers();
    }

    //Dodanie do BD
    @PostMapping("/add")
    public @ResponseBody String addUser(@RequestParam String login, @RequestParam String password, @RequestParam String email){

        //Sprawdzenie czy podane dane istenieja w bazie
        if(usersService.findLoginAndEmail(login, email).equals("Username_exist")){
            return "Username_exist";
        }else if(usersService.findLoginAndEmail(login, email).equals("Email_exist")){
            return "Email_exist";
        }else {

            //Zapisuje nowego usera
            Users n = new Users();
            n.setLogin(login);
            n.setPassword(password);
            n.setEmail(email);

            usersService.saveuser(n);

            //Zwraca id dodanego uzytkownika
            long id = n.getId();

            //Przypisuje nowego usera do defaultGroup
            Groups group = new Groups();
            group.setName("Wszystkie urządzenia");
            group.setUsers_id(id);

            groupsService.saveDefaultGroup(group);

            return "Saved";
        }
    }

    //Do sprawdzenia po login&passwd
    @GetMapping("/checklog")
    public @ResponseBody String checklog(@RequestParam String login, @RequestParam String password){


        if(usersService.findLoginAndPassword(login, password)){

            String idOfLoggedUser = usersService.findLogin(login);

            return "true+" + idOfLoggedUser ;
        }else{
            return "false";
        }

    }

    //Do sprawdzenia po mail&passwd
    @GetMapping("/checkema")
    public @ResponseBody Boolean checkema(@RequestParam String email, @RequestParam String password){

        if(usersService.findEmailAndPassword(email, password)){
            return true;
        }else{
            return false;
        }
    }



    @PostMapping("/test")
    public @ResponseBody void test2(@RequestParam String login, @RequestParam String password){
        final String topic = login;
        String msg = password;

        try {
            mqttService.publish(topic, password, 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

/*
    @PostMapping("/test2")
    public @ResponseBody String test3(){

        Users users = new Users("root2", "password", "root2@root.com");

        Groups groups1 = new Groups("forRoot2v1");
        Groups groups2 = new Groups("forRoot2v2");

        users.getGroups().add(groups1);
        users.getGroups().add(groups2);

        this.usersRepository.save(users);

        return "halo";

    }
*/


}
