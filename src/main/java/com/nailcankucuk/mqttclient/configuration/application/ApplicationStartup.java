package com.nailcankucuk.mqttclient.configuration.application;

import com.nailcankucuk.mqttclient.helper.MqttHelper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private MqttHelper mqttHelper;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            mqttHelper.connect();
            mqttHelper.subscripbeTopic();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
