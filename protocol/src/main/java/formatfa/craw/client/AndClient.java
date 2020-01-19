package formatfa.craw.client;



import java.io.IOException;
import java.net.Socket;

import formatfa.craw.protocol.Response;

public class AndClient {
//    host和端口
    private String host,port;

    public AndClient(String host, String port) {
        this.host = host;
        this.port = port;
    }
    public static Connection connect(String host,int port) throws IOException {
        Socket socket = new Socket(host,port);
//        连接成功，返回设备信息

        Connection conn = new Connection(socket);
        return conn;
    }

    public static void main(String[] args) throws IOException {
        Connection conn = AndClient.connect("localhost",2333);
        System.out.println("连接设备成功:"+conn.getClientInfo().getDeviceName());
        try {
            Response layout =  conn.getLayout();
            System.out.println("获取布局:"+layout.getString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
