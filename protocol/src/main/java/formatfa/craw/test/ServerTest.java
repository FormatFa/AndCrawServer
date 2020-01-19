package formatfa.craw.test;


import formatfa.craw.client.ClientInfo;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;
import formatfa.craw.server.AndCrawServer;
import formatfa.craw.server.RequestHandler;
import formatfa.craw.server.ClientThread;

public class ServerTest implements RequestHandler {

    public static void main(String[] args) {

        AndCrawServer server = new AndCrawServer(2333);
        server.start(new ServerTest());

    }

    public Response handleRequest(ClientThread serverThread, Request req) {
        System.out.println("服务接受到请求:"+req.getString());


        return null;
    }

    @Override
    public ClientInfo getClientInfo(ClientThread clientThread) {
        return null;
    }
}
