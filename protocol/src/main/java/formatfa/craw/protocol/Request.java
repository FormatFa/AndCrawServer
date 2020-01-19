package formatfa.craw.protocol;

public class Request {

//    代表一个请求
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String action;
    private String body;

    public Request(String data) {
        this.data = data;
        this.parseData();
    }

    public Request(String action, String body) {
        this.action = action;
        this.body = body;
    }

    public String getString()
    {
        return this.action+"\t"+this.body;
    }
    private void parseData() {
        String [] temps = this.data.split("\t",2);
        this.action=temps[0];
        this.body = temps[1];
    }
}
