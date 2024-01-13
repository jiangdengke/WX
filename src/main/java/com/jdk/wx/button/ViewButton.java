package com.jdk.wx.button;

public class ViewButton extends AbstractButton{
    public ViewButton(String name) {
        super(name);
        this.type="view";//这里直接把类型写死
    }
    private String type;
    private String url;

    public String getType() {
        return type;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
