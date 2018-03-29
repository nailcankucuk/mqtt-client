package com.nailcankucuk.mqttclient.helper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCallback implements MqttCallback {
    Logger logger = LoggerFactory.getLogger(SimpleCallback.class);
    @Override
    public void connectionLost(Throwable cause) {
        logger.error("Connection closed to MQTT broker. Cause is '{}'",cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + topic);
        System.out.println("| Message: " + new String(message.getPayload()));
        System.out.println("-------------------------------------------------");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Delivery is Complete");
    }
}
