package formatfa.craw.protocol;

public class Response {

    private String status;
    private String body;
    private String data;

    public Response(String status, String body) {
        this.status = status;
        this.body = body;
    }

    public Response(String data) throws Exception {
        this.data = data;
        this.parseData();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getString()
    {
        return this.status+"\t"+this.body;
    }
    private void parseData() throws Exception {

        String [] temps = this.data.split("\t",2);
        if(temps.length!=2)
        {
           return;
        }
        this.status=temps[0];
        this.body = temps[1];

    }
}
