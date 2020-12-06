package inz.restapiproject.controller;


import com.google.gson.Gson;
import inz.restapiproject.model.Lights;
import inz.restapiproject.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mqtt")
public class MqttController {

    @Autowired
    MqttService mqttService;

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
    public @ResponseBody String sendInfo(@RequestParam String color , @RequestParam String serials) {

        String msg_color = color;

        String[] topic = serials.split("_");

        for(int i =0 ; i<topic.length; i++){
            System.out.println(topic[i]);
        }
        for (int i=0; i<topic.length; i++) {
            try {
                System.out.println(topic);
                mqttService.publish(topic[i], msg_color, 0, false);

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        return "Wyslane";
    }

}
