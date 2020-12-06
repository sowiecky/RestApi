package inz.restapiproject.controller;


import com.google.gson.Gson;
import inz.restapiproject.model.Groups;
import inz.restapiproject.model.Lights;
import inz.restapiproject.service.GroupsService;
import inz.restapiproject.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/mqtt")
public class MqttController {

    @Autowired
    MqttService mqttService;
    @Autowired
    GroupsService groupsService;

//    @GetMapping("/sendInfo")
//    public @ResponseBody String sendInfo(@RequestParam String color , @RequestParam String str) {
//
//        String msg_color = color;
//
//        String[] topic = str.split("\\s+");
//
//        for(int i =0 ; i<topic.length; i++){
//            System.out.println(topic[i]);
//        }
//        for (int i=0; i<topic.length; i++) {
//            try {
//                System.out.println(topic);
//                mqttService.publish(topic[i], msg_color, 0, false);
//
//            } catch (MqttException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return "Koniec";
//    }

    @GetMapping("/sendInfo")
    public @ResponseBody String sendInfo(@RequestParam String message , @RequestParam String groupId) {

        String messageToMqtt = message.replace("_", " ");
        Groups group = groupsService.getGroupById(Long.parseLong(groupId));
        Set<Lights> lights = group.getLights();

        for (Lights light: lights) {
            try {
                mqttService.publish(light.getSerial(),messageToMqtt, 0, false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        return "Wyslane";
    }
}
