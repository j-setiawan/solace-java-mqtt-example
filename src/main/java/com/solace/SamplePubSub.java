package com.solace;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SamplePubSub implements MqttCallback {
    public static void main(String[] args) throws Exception {
        String url = args[0];
        String username = args[1];
        String password = args[2];

        String topicBase = "iot/" + username;

        MqttClient client = new MqttClient(url, username);
        client.setCallback(new SamplePubSub());

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(username);
        connectOptions.setPassword(password.toCharArray());

        client.connect(connectOptions);

        client.subscribe(topicBase + "/messages");

        while (true) {
            client.publish(topicBase + "/heartbeats", new MqttMessage("ping".getBytes()));
            Thread.sleep(1000);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("Received message on topic " + topic + ": " + mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
