package com.nailcankucuk.mqttclient.helper;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created on 29/03/2018 12:49
 * @author nail
 */
@Component
public class MqttHelper {
    Logger logger = LoggerFactory.getLogger(MqttHelper.class);
    private final static String PROPERTIES_FILE_NAME = "/mqtt.properties";
    Properties props = new Properties();
    private static MqttClient client = null;
    private static boolean connected = false;

    public MqttClient connect() throws MqttException {
        if (null != client || connected) {
            logger.warn("You also have an mqtt client connection!");
            return client;
        }

        MemoryPersistence persistence = new MemoryPersistence();
        try {
            props.load(MqttHelper.class.getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (IOException exc) {
            logger.error("There was an error while load MQTT Properties.");
        }
        client =
                new MqttClient(props.getProperty("BROKER_URL"), props.getProperty("CLIENT_ID"), persistence);
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        client.connect(connectOptions);
        client.setCallback(new SimpleCallback());
        logger.info("MQTT connection OK");
        connected = true;
        return client;
    }

    public void subscripbeTopic() {
        String topicName = props.getProperty("TOPIC_NAME");
        logger.debug("Subscribing to topic:{}", topicName);
        try {
            client.subscribe(topicName);
            logger.debug("Successfully subscribe to topic: {}", topicName);
        } catch (MqttException e) {
            logger.error("There was an error while subscribe to topic: {}", topicName);
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (client == null || !connected) {
            logger.error("Not any connection to mosquitto! You have to connect to broker for send a message!");
        }
        String topicName = props.getProperty("TOPIC_NAME");
        MqttMessage mqttMessage = new MqttMessage(message.getBytes(Charset.forName("UTF-8")));
        if(props.getProperty("QOS")!=null){
            mqttMessage.setQos(Integer.parseInt(props.getProperty("QOS")));
        }
        try {
            client.publish(topicName, mqttMessage);
            logger.debug("Message '{}' published to topic '{}'", message, topicName);
        } catch (MqttException e) {
            logger.error("There was an error while publishing the message!");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (null == client || !connected) {
            logger.error("There is not any connection!");
        }
        try {
            client.disconnect();
            logger.debug("Client disconnect from mosquitto successfully!");
        } catch (MqttException e) {
            logger.error("There was an error while disconnect from mosquitto!");
            e.printStackTrace();
        }
    }

}
