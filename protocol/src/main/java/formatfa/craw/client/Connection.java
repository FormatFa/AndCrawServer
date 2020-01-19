package formatfa.craw.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import formatfa.craw.protocol.Constants;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;

public class Connection {
//    链接对象
    private Socket client;

    private ClientInfo clientInfo;
    private OutputStream os;

    private BufferedReader reader;
    public Connection(Socket client) throws IOException {
        this.client = client;
        this.os=client.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        readInfo();
    }

    private Response readResponse() throws Exception {
        String line = reader.readLine();
        if(line==null)
            return null;
        return new Response(line);

    }
    public Response  request(Request req) throws Exception {
        this.sendRequest(req);
        Response res = this.readResponse();
        return res;
    }

    //send a request
    public  void  sendRequest(Request req) throws IOException {

            this.os.write(req.getString().getBytes());
            this.os.write(Constants.SPLIT.getBytes());

    }
    private void readInfo() {
        try {
            Response res = readResponse();
            ClientInfo info = JSON.parseObject(res.getBody(),ClientInfo.class);
            this.clientInfo=info;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Response getLayout() throws Exception {
        Request layoutReq = new Request("GET","");
        sendRequest(layoutReq);
        Response res = readResponse();
        return res;
    }


}
