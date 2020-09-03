package io.lemonfish.utils.dingtalk;

import com.dingtalk.api.request.OapiRobotSendRequest;

import java.util.Arrays;

/**
 * @author linwensi
 * @date 2020-09-03 10:31 上午
 */
public class RobotSendRequestBuilder {

    private OapiRobotSendRequest request;

    private RobotSendRequestBuilder(OapiRobotSendRequest request) {
        this.request = request;
    }

    public static RobotSendRequestBuilder builder() {
        return new RobotSendRequestBuilder(new OapiRobotSendRequest());
    }

    public RobotSendRequestBuilder textRequest(String content) {
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        return this;
    }

    public RobotSendRequestBuilder atSomeone(String... mobiles) {
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(mobiles));
        request.setAt(at);
        return this;
    }

    public RobotSendRequestBuilder atAll() {
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);
        return this;
    }

    public RobotSendRequestBuilder linkRequest(String url, String picUrl, String title, String text) {
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(url);
        link.setPicUrl(picUrl);
        link.setTitle(title);
        link.setText(text);
        request.setLink(link);
        return this;
    }

    public RobotSendRequestBuilder markdown(String title, String text) {
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(text);
        request.setMarkdown(markdown);
        return this;
    }

    public OapiRobotSendRequest build() {
        return request;
    }

}
