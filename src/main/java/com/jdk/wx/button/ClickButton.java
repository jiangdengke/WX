package com.jdk.wx.button;

public class ClickButton extends AbstractButton{
    public ClickButton(String name) {
        super(name);
        this.type="click";
    }
    private String type;
    private String key;

    public String getType() {
        return type;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
