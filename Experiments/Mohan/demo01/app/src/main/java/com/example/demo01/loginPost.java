package com.example.demo01;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

class loginPost {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
//    public String logina(String name, String pwd)throws ParseException, IOException, JSONException{
//
//        OkHttpClient httpClient = new OkHttpClient();
//        String strurl = "//loginurl";
//
//
//         request = new HttpPost(strurl);
//        request.addHeader("Accept","application/json");
//        request.addHeader("Content-Type","application/json");//customize header
//        JSONObject param = new JSONObject();//define JSON object
//        param.put("sequenceId", "87620056570355357690");
//        param.put("accType", "0");
//        param.put("loginId", name);
//        param.put("password", pwd);
//        //param.put("thirdpartyAppId", "");
//        //param.put("thirdpartyAccessToken", "");
//        param.put("loginType", "1");
//        Log.i("===========", param.toString());
//        StringEntity se = new StringEntity(param.toString());
//        request.setEntity(se);// request
//        HttpResponse httpResponse = httpClient.execute(request);// receive
//        int code = httpResponse.getStatusLine().getStatusCode();
//        System.out.print(code);
//        String result = EntityUtils.toString(httpResponse.getEntity());
//        JSONObject result1 = new JSONObject(result);
//        String info = (String) result1.get("retInfo");
//        Log.i("=============", info);
//        return info;
//    }

}
