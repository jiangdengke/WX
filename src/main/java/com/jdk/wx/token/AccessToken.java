package com.jdk.wx.token;

public class AccessToken {
    private String token;
    private long expireTime;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireIn) {
        this.expireTime = System.currentTimeMillis()+expireIn+1000;
    }

    public AccessToken() {
    }

    public AccessToken(String token, long expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }

    //判断有没有超时
    public boolean isExpired(){
        return System.currentTimeMillis()>this.expireTime;
    }

}
