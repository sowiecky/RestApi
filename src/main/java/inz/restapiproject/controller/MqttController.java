package inz.restapiproject.controller;

import inz.restapiproject.model.Lights;
import inz.restapiproject.service.GroupsService;
import inz.restapiproject.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/mqtt")
public class MqttController {

    @Autowired
    MqttService mqttService;
    @Autowired
    GroupsService groupsService;

    @GetMapping("/sendInfo")
    public @ResponseBody String sendInfo(@RequestParam String message , @RequestParam String groupId) {

        String messageToMqtt = message.replace("_", " ");                       //dostosowywanie wiadomości
        Set<Lights> lights = groupsService.getGroupById(Long.parseLong(groupId)).getLights();   //pobieranie zarowek z wybranej grupy

        for (Lights light: lights) {
            try {
                mqttService.publish(light.getSerial(),messageToMqtt, 0, false);     //dla kazdej zarowki wysyłanie tej samej wiadomosci
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        return "Wyslane";
    }

    @GetMapping("/sendInfoOne")
    public @ResponseBody String sendInfoOne(@RequestParam String message , @RequestParam String serial) {

        String messageToMqtt = message.replace("_", " ");                       //dostosowywanie wiadomości

        try {
            mqttService.publish(serial,messageToMqtt, 0, false);                    //wyslanie na jedno konkretne urzadzenie
        } catch (MqttException e) {
            e.printStackTrace();
        }


        return "Wyslane";
    }

}
