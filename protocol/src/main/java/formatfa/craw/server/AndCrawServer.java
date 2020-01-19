package formatfa.craw.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import formatfa.craw.client.ClientInfo;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;

public class AndCrawServer {




    public static void main(String[] args) {


        new AndCrawServer(2333).start(new RequestHandler() {
            public Response handleRequest(ClientThread serverThread, Request req) {
                return null;
            }

            @Override
            public ClientInfo getClientInfo(ClientThread clientThread) {
                return null;
            }
        });


    }

    private int port;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public AndCrawServer(int port) {
        this.port = port;
    }




    public ServerThread start(RequestHandler handler) {

        ServerThread thread = new ServerThread(this,handler);
        thread.start();
        return thread;


    }


}
