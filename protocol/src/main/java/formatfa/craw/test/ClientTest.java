package formatfa.craw.test;


import formatfa.craw.client.AndClient;
import formatfa.craw.client.Connection;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.RequestAction;
import formatfa.craw.protocol.Response;

public class ClientTest {
    public static void main(String[] args) {

        try {
            Connection connection =  AndClient.connect("192.168.0.123",2333);
            System.out.println("连接成功，服务端设备:"+connection.getClientInfo().getDeviceName());
//            发送请求
            Request req = new Request(RequestAction.GET.getText(),"req data");
            Response res = connection.request(req);
            System.out.println("res:"+res.getString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
