import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class notify {
    public static void main(String[] args) throws Exception {
//        String host = "tcp://172.16.40.173:1883";
        String host = "tcp://119.3.210.123:1883";
        String topic = "lwm2m/+/up/notify";
        String clientId = "notifytest";// clientId不能重复
        // 1.设置mqtt连接属性
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        // 2.实例化mqtt客户端
        MqttClient client = new MqttClient(host, clientId);
        // 3.连接
        client.connect(options);

        client.setCallback(new PushCallback());
        while (true) {
            MqttMessage message = new MqttMessage();
            client.subscribe(topic, 2);

        }
    }
}
