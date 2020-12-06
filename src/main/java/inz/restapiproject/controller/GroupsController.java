package inz.restapiproject.controller;


import inz.restapiproject.model.Groups;
import inz.restapiproject.model.Lights;
import inz.restapiproject.service.GroupsService;
import inz.restapiproject.service.LightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class GroupsController {

    @Autowired
    GroupsService groupsService;

    @Autowired
    LightsService lightsService;

    @PostMapping("/add")
    public @ResponseBody
    String addGroup(@RequestParam String name, @RequestParam String user_id){

        long iduser = Long.parseLong(user_id);

        //Sprawdzenie czy grupa znajduje sie juz w BD
        if(groupsService.checkExistOfGroup(name).isEmpty()) {
            Groups groups = new Groups();
            groups.setName(name);
            groups.setUsers_id(iduser);

            groupsService.saveGroup(groups);

            return "Saved";
        }else{
            return "Group_exists";
        }
    }

    @GetMapping("/get")
    public @ResponseBody
    List<Groups> getAllGroups(@RequestParam String user_id){
        long iduser = Long.parseLong(user_id);
        return groupsService.findAllGroupsForUserId(iduser);
    }

    @GetMapping("/test")
    public @ResponseBody
    List<Groups> test(){

        return groupsService.test(102);
    }


}
