package formatfa.craw.andcrawserver;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.RequiresApi;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import formatfa.craw.client.ClientInfo;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.RequestAction;
import formatfa.craw.protocol.Response;
import formatfa.craw.protocol.ResponseStatus;
import formatfa.craw.server.AndCrawServer;
import formatfa.craw.server.RequestHandler;
import formatfa.craw.server.ClientThread;

public class AccessibilityServer extends AccessibilityService implements RequestHandler {
    public static final String tag="AccessibilityServer";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(tag,"AccessbilityEvent:"+event.getAction());
        AccessibilityNodeInfo node = getRootInActiveWindow();

        Log.d(tag,"on node:"+node);

    }


    private AndCrawServer server;
    Rect rect = new Rect();
    private JSONObject getWindow(AccessibilityNodeInfo root)   {
        JSONObject result = new JSONObject();
        if(root==null)
        {
            return result;
        }


//        将可以get的到的东西都放进去r
        CharSequence text = root.getText();
        CharSequence classname = root.getClassName();
        result.put("text",text);
        result.put("className",classname);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            result.put("error",root.getError());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            result.put("paneTitle",root.getPaneTitle());
        }
        result.put("windowId",root.getWindowId());

        root.getBoundsInScreen(rect);
        result.put("left",rect.left);
        result.put("top",rect.top);
        result.put("right",rect.right);
        result.put("bottom",rect.bottom);


//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            JSONArray acionList = new JSONArray();
//            List<AccessibilityNodeInfo.AccessibilityAction> actions = root.getActionList();
//            for(AccessibilityNodeInfo.AccessibilityAction action:actions)
//            {
//                JSONObject a = new JSONObject();
//                a.put("id",action.getId());
//                a.put("label",action.getLabel());
//                acionList.add(a);
//            }
//            result.put("actionList",acionList);
//        }

        JSONArray childs = new JSONArray();
        for(int i=0;i<root.getChildCount();i+=1)
        {

            JSONObject child = getWindow(root.getChild(i));
            childs.add(child);
        }
        result.put("children",childs);
        return result;

    }
    @Override
    public void onInterrupt() {
        Log.w(tag,"onInterrupt");

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onServiceConnected() {

        Log.w(tag,"onServiceConnected..");

        AccessibilityServiceInfo info = this.getServiceInfo();
        info.eventTypes= AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED| AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED|AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.packageNames=null;
        info.feedbackType=AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        this.setServiceInfo(info);

        //
        server = new AndCrawServer(2333);
        server.start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Response handleRequest(ClientThread serverThread, Request req) {

        Log.e(tag,"handle request:"+req);
        if(req.getAction().equals(RequestAction.GET.getText()))
        {

//            List<AccessibilityWindowInfo> windows =  getWindows();


            Log.d(tag,"是否可以检索见面内容:"+this.getServiceInfo().getCanRetrieveWindowContent());
            Log.d(tag,"获取界面布局请求111...");

                JSONObject object = getWindow(getRootInActiveWindow());

                Response res = new Response(ResponseStatus.SUCCESS.getText(),object.toJSONString());
                Log.d(tag,"return:"+res.getString());
            return res;
        }

        return null;
    }

    @Override
    public ClientInfo getClientInfo(ClientThread clientThread) {
        ClientInfo info = new ClientInfo();
        info.setDeviceName("model:"+Build.MODEL+" sdk:"+Build.VERSION.SDK_INT+" version:"+Build.VERSION.RELEASE);

        return info;
    }
}
