import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class server {
    public static void main(String[] args) throws Exception {
//        String host = "tcp://172.16.40.173:1883";
        String host = "tcp://119.3.210.123:1883";
        String topic = "lwm2m/gdzytest001/dn";
//        String topic = "testtopic";
        String clientId = "server";// clientId不能重复
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        MqttClient client = new MqttClient(host, clientId);
        client.connect(options);

        MqttMessage message = new MqttMessage();

//        @SuppressWarnings("resource")
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入要发送的内容：");
//        while (true) {
//            String line = scanner.nextLine();
            String line = "{\n" +
                    "    \"reqID\": \"2\",\n" +
                    "    \"msgType\": \"observe\",\n" +
                    "    \"data\": {\n" +
                    "        \"path\": \"/19/0/0\"\n" +
                    "    }\n" +
                    "}";
            message.setPayload(line.getBytes());
            client.publish(topic, message);
            client.close();

//        }
    }
}
