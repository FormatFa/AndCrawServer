package formatfa.craw.protocol;

public enum RequestAction {
    GET("get"),UNKNOW("unknow");

    private String text;

    RequestAction(String text) {
        this.text = text;
    }
    public String getText()
    {
        return this.text;
    }

    public static RequestAction getAction(String text)
    {
        if(GET.getText().equals(text))
            return GET;
        else
            return UNKNOW;
    }
}
