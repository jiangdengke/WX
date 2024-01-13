package com.jdk.wx.message.picturetextmessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 *  *     <item>
 *  *       <Title><![CDATA[title1]]></Title>
 *  *       <Description><![CDATA[description1]]></Description>
 *  *       <PicUrl><![CDATA[picurl]]></PicUrl>
 *  *       <Url><![CDATA[url]]></Url>
 *  *     </item>
 */
@Data
@XStreamAlias("item")
public class Article {
    @XStreamAlias("Title")
    private String title;
    @XStreamAlias("Description")
    private String description;
    @XStreamAlias("PicUrl")
    private String picUrl;
    @XStreamAlias("Url")
    private String url;
}
