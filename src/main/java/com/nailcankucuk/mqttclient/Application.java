package com.nailcankucuk.mqttclient;

import com.nailcankucuk.mqttclient.helper.MqttHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

/**
 * 27/03/2018 23:54.
 * @author nail
 */
@SpringBootApplication
public class Application {
    private MqttHelper mqttHelper;
    @Autowired
    public Application(final MqttHelper mqttHelper) {
        this.mqttHelper = mqttHelper;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    public void stopApplication() {
        mqttHelper.disconnect();
    }
}
