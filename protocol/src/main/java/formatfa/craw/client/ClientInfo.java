package formatfa.craw.client;

import com.alibaba.fastjson.JSONObject;


public class ClientInfo {
//    客户端信息
//    装置名字
    private String deviceName;
    private  int age;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public static void main(String[] args) {
        ClientInfo info = new ClientInfo();
        info.setDeviceName("formatfa");

        String json = JSONObject.toJSONString(info);
        System.out.println(json);

    }
}
