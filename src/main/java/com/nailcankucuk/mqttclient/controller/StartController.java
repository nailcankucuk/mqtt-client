package com.nailcankucuk.mqttclient.controller;

import com.nailcankucuk.mqttclient.helper.MqttHelper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/start")
public class StartController {
    @Autowired
    private MqttHelper mqttHelper;

    @GetMapping(path = "/mqtt")
    public void start() {
        try {
            mqttHelper.connect();
            mqttHelper.subscripbeTopic();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
