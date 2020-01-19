package formatfa.craw.server;

import formatfa.craw.client.ClientInfo;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;


//服务收到请求时的处理接口
public interface RequestHandler {

    public Response handleRequest(ClientThread clientThread, Request req);
    public ClientInfo getClientInfo(ClientThread clientThread);

}
