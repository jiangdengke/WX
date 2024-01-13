package com.jdk.wx.token;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

public class TokenUtil {
    private static AccessToken accessToken = new AccessToken();
    private static final String APP_ID = "";
    private static final String APP_SECRET = "";

    public static void main(String[] args) {
        System.out.println(getAccessToken());
        System.out.println(getAccessToken());
    }
    public static void getToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET;
        String response = HttpUtil.get(url);
        JSONObject jsonObject = new JSONObject(response);
        String token = jsonObject.getStr("access_token");
        Long expiresIn = jsonObject.getLong("expires_in");
        accessToken.setToken(token);
        accessToken.setExpireTime(expiresIn);
    }
    public static String getAccessToken(){
        if(accessToken==null|| accessToken.isExpired()){
            getToken();
        }
        return accessToken.getToken();
    }

}
