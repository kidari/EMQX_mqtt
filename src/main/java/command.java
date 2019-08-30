import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class command {
    public static void main(String[] args) throws Exception {
//        String host = "tcp://172.16.40.173:1883";
        String host = "tcp://119.3.210.123:1883";
        String topic = "lwm2m/221.178.127.104/dn";
//        String topic = "testtopic";
        String clientId = "command";// clientId不能重复
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
                    "    \"reqID\": \"3\",\n" +
                    "    \"msgType\": \"write\",\n" +
                    "    \"data\": {\n" +
                    "    \"path\": \"/19/1/0\",\n" +
                    "    \"type\": \"String\",\n" +
                    "    \"value\": \"abc\"\n" +
                    "    }\n" +
                    "}";
            String t = "{\n" +
                    "    \"path\": {?ResourcePath},\n" +
                    "    \"type\": {?ValueType},\n" +
                    "    \"value\": {?Value}\n" +
                    "}";

            System.out.println(topic+"^_^"+line);
            message.setPayload(line.getBytes());
            client.publish(topic, message);
            client.close();

//        }
    }
}
