package formatfa.craw.protocol;

public enum ResponseStatus {
//    返回的状态码
    SUCCESS("success"),FAIL("fail");

    private String text;
    ResponseStatus(String text) {
        this.text=text;
    }
    public String  getText()
    {
        return this.text;
    }

    public static ResponseStatus getStatus(String text)
    {
        if(text.equals(SUCCESS.getText()))return SUCCESS;
        else return FAIL;

    }


}
