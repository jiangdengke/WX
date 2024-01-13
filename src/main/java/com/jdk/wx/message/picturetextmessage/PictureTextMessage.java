package com.jdk.wx.message.picturetextmessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

/**
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[news]]></MsgType>
 *   <ArticleCount>1</ArticleCount>
 *   <Articles>
 *     <item>
 *       <Title><![CDATA[title1]]></Title>
 *       <Description><![CDATA[description1]]></Description>
 *       <PicUrl><![CDATA[picurl]]></PicUrl>
 *       <Url><![CDATA[url]]></Url>
 *     </item>
 *   </Articles>
 * </xml>
 */
@Data
@XStreamAlias("xml")
public class PictureTextMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    @XStreamAlias("CreateTime")
    private long createTime;
    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("ArticleCount")
    private int articleCount;
    @XStreamAlias("Articles")
    private List<Article> articles;
}
