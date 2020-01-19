package formatfa.craw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private AndCrawServer server;
    RequestHandler handler;

    public ServerThread(AndCrawServer server, RequestHandler handler) {
        this.server = server;
        this.handler = handler;
    }

    public ServerThread(AndCrawServer server) {
        this.server = server;
    }

    private boolean running=true;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(server.getPort());
            String host=socket.getInetAddress().toString();
            System.out.println("start server at "+host+":"+socket.getLocalPort()+" waiting client connect...");
            while(running)
            {
                Socket client = socket.accept();
                ClientThread thread = new ClientThread(client,handler);
                thread.start();

            }
            System.err.println("ServerThread has stopped");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
