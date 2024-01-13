package com.jdk.wx.button;

public class PicPhotoOrAlbumButton extends AbstractButton{
    public PicPhotoOrAlbumButton(String name) {
        super(name);
        this.type = "pic_photo_or_album";
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
