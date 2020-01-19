package formatfa.craw.server;


import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.net.Socket;

import formatfa.craw.client.ClientInfo;
import formatfa.craw.protocol.Constants;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;
import formatfa.craw.protocol.ResponseStatus;


public class ClientThread extends Thread {
//    服务端线程,通过简单的换行符分隔

    private Socket socket ;
    private OutputStream os;
    private RequestHandler handler;

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    public ClientThread(Socket socket, RequestHandler handler) throws IOException {
        this(socket);
        this.socket = socket;
        this.handler = handler;

    }

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.os = socket.getOutputStream();
    }

    @Override
    public void run() {


//        接受客户端的请求
//        发送一个客户端信息的响应
        sendInfo();

        try {
            handleRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sendInfo() {

        //获取客户端信息
        ClientInfo clientInfo =null;
        if(handler!=null)clientInfo=handler.getClientInfo(this);
        if(clientInfo==null)
        {
            clientInfo = new ClientInfo();
            clientInfo.setDeviceName("Unknow device name");
        }


        Response response=new Response(ResponseStatus.SUCCESS.getText(),JSONObject.toJSONString(clientInfo));
        System.out.println("sending first response of server device info :"+response.getString());

        sendResponse(response);
    }

    //    发送响应
    private void sendResponse(Response res)
    {
        if(res==null)
        {
            return;
        }

        try {
            this.os.write(res.getString().getBytes());
            this.os.write(Constants.SPLIT.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleRequest() throws IOException {
        InputStream is = socket.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        while( (line=reader.readLine())!=null)
        {
            try
            {
                Request req = new Request(line);
                System.out.println("receive client request:"+req.getString());
                processRequest(req);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    private void processRequest(Request req)
    {
        if(handler!=null)
        {
             Response res =  handler.handleRequest(this,req);
             sendResponse(res);
        }
//        switch (req.getAction())
//        {
//            case GET:
//                processGET(req);
//                break;
//            case UNKNOW:
//                break;
//
//        }

    }
    private void processGET(Request req)
    {

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        JSONObject object = new JSONObject();
//        object.put("name","formatfa");
//        object.put("age",12);
//        Response response=new Response(ResponseStatus.SUCCESS,object.toJSONString());
//        System.out.println("发送响应:"+response.getString());
//
//        sendResponse(response);

    }
}
