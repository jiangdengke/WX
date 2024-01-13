package com.jdk.wx.web;

import com.jdk.wx.message.TextMessage;
import com.jdk.wx.message.picturetextmessage.Article;
import com.jdk.wx.message.picturetextmessage.PictureTextMessage;
import com.thoughtworks.xstream.XStream;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class WxController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * 响应微信发送的token验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/")
    public String check(String signature,
                        String timestamp,
                        String nonce,
                        String echostr){
        /**
         * 1）将token、timestamp、nonce三个参数进行字典序排序
         *
         * 2）将三个参数字符串拼接成一个字符串进行sha1加密
         *
         * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
         */
        List<String> list = Arrays.asList("jiangdk", timestamp, nonce);
        Collections.sort(list);//排序
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : list) {
            stringBuffer.append(s);
        }

        try {
            MessageDigest instance = MessageDigest.getInstance("sha1");//使用sha1算法
            byte[] digest = instance.digest(stringBuffer.toString().getBytes());
            //将字节数组转换成16进制的字符串
            StringBuffer checkSum = new StringBuffer();
            for (byte b : digest) {
                checkSum.append(Integer.toHexString((b>>4)&15));
                checkSum.append(Integer.toHexString(b&15));
            }
            if (!StringUtils.isEmpty(signature) &&signature.equals(checkSum.toString())){
                return echostr;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
    /**
     * 一个消息先发到微信的服务器，然后又被发到我的服务器上。
     * 这里对微信官方发来的消息做处理
     */
    @PostMapping("/")
    public String receiveMessage(HttpServletRequest request) throws IOException, DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String,String> map = new HashMap<>();
        Document document = saxReader.read(request.getInputStream());//拿到Document对象
        Element rootElement = document.getRootElement();//获取root节点
        List<Element> elements = rootElement.elements();//获取所有子节点
        for (Element element : elements) {
            map.put(element.getName(),element.getStringValue());
        }
        System.out.println(map);
        String message = null;
        switch (map.get("MsgType")){
            case "text":
                if (map.get("Content").equals("图文")){
                    message = geteReplyPictureTextMessage(map);
                }else {
                    message = getReplyTextMessage(map,"谢谢关注");
                }
                break;
            case "event":
                message = handelEvent(map);
                break;
            case "image":
                message = handelImage(map);
            default:
                break;
        }

        return message;
    }

    /**
     * 暂时没想好怎么处理图片，sorry。
     * @param map
     * @return
     */

    private String handelImage(Map<String, String> map) {
        String replyImageMessage = getReplyTextMessage(map, "暂时没想好怎么处理图片，sorry。");
        return replyImageMessage;
    }

    private String handelEvent(Map<String, String> map) {
        /**
         * <xml>
         *   <ToUserName><![CDATA[toUser]]></ToUserName>
         *   <FromUserName><![CDATA[FromUser]]></FromUserName>
         *   <CreateTime>123456789</CreateTime>
         *   <MsgType><![CDATA[event]]></MsgType>
         *   <Event><![CDATA[CLICK]]></Event>
         *   <EventKey><![CDATA[EVENTKEY]]></EventKey>
         * </xml>
         */
        switch (map.get("Event")){
            case "CLICK":
                if(map.get("EventKey").equals("1")){
                    String clickEvent = getReplyTextMessage(map, "你点击了key为1的click事件");
                    return clickEvent;
                }
                break;
            case "VIEW":
                break;
            default:break;
        }
        return null;
    }

    private String geteReplyPictureTextMessage(Map<String, String> map) {
        System.out.println(1);
        PictureTextMessage pictureTextMessage = new PictureTextMessage();
        pictureTextMessage.setToUserName(map.get("FromUserName"));
        pictureTextMessage.setFromUserName(map.get("ToUserName"));
        pictureTextMessage.setCreateTime(System.currentTimeMillis()/1000);
        pictureTextMessage.setMsgType("news");
        pictureTextMessage.setArticleCount(1);
        Article article = new Article();
        article.setTitle("图文");
        article.setDescription("一个用于测试的图文");
        article.setPicUrl("https://mmbiz.qpic.cn/mmbiz_jpg/pPibJkibymljKkV8tTAicELIbdDZlxv9AxfiaibxsBwbniaO23FC0tARq95WnxAN9TwTn3gs1gEzglQCL9nMVJX0xcfw/0");
        article.setUrl("www.baidu.com");
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        pictureTextMessage.setArticles(articleList);
        XStream xStream = new XStream();
        xStream.processAnnotations(PictureTextMessage.class);
        String xml = xStream.toXML(pictureTextMessage);
        System.out.println(2);
        return xml;
    }

    private String getReplyTextMessage(Map<String, String> map,String message) {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(map.get("FromUserName"));
        textMessage.setFromUserName(map.get("ToUserName"));
        textMessage.setCreateTime(System.currentTimeMillis()/1000);
        textMessage.setMsgType("text");
        textMessage.setContent(message);
        XStream xStream = new XStream();
        xStream.processAnnotations(TextMessage.class);
        String xml = xStream.toXML(textMessage);
        return xml;
    }
}
