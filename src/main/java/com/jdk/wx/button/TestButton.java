package com.jdk.wx.button;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.jdk.wx.token.TokenUtil;

import java.util.ArrayList;
import java.util.List;

public class TestButton {
    public static void main(String[] args) {
        Button button = new Button();
        List<AbstractButton> buttons = new ArrayList<>();//用来装一级菜单的按钮
        button.setButton(buttons);
        //一级菜单的第一个按钮
        ClickButton clickButton = new ClickButton("测试Click");
        clickButton.setKey("1");
        //一级菜单的第二个按钮
        ViewButton viewButton = new ViewButton("测试View");
        viewButton.setUrl("http://www.baidu.com");
        //一级菜单中的第三个按钮，是一个二级菜单
        SubButton subButton = new SubButton("测试二级");
        List<AbstractButton> subButtons = new ArrayList<>();//用来装二级菜单的按钮
        subButton.setSub_button(subButtons);
        //二级菜单的第一个按钮
        ClickButton clickButton1 = new ClickButton("ClickButton");
        clickButton1.setKey("2");
        //二级菜单的第二个按钮
        ViewButton viewButton1 = new ViewButton("ViewButton");
        viewButton1.setUrl("http://www.qq.com");
        //二级菜单的第三个按钮
        PicPhotoOrAlbumButton picPhotoOrAlbumButton = new PicPhotoOrAlbumButton("上传图片");
        picPhotoOrAlbumButton.setKey("PicPhotoOrAlbum");
        subButtons.add(clickButton1);
        subButtons.add(viewButton1);
        subButtons.add(picPhotoOrAlbumButton);
        buttons.add(clickButton);
        buttons.add(viewButton);
        buttons.add(subButton);


        JSONObject jsonObject = new JSONObject(button);
        String menu = jsonObject.toString();
        System.out.println(menu);
        //https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+TokenUtil.getAccessToken();
        String post = HttpUtil.post(url, menu);
        System.out.println(post);
    }
}
