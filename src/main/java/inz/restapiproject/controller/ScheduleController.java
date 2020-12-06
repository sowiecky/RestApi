package inz.restapiproject.controller;

import inz.restapiproject.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/schedule")
public class ScheduleController {

    @Autowired
    GroupsService groupsService = new GroupsService();

/*
    //PODODAWAC ODPOWIEDNIE REQUESTY STRINGI
    @PostMapping("/add")
    public @ResponseBody String addSchedule(){
        groupsService.findJustIdGroupForUserIdAndName();
    }
*/
}
