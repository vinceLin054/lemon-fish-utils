package io.lemonfish.utils.dingtalk;

import com.taobao.api.ApiException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author linwensi
 * @date 2020-09-02 4:32 下午
 */
public class SendTest {

    private static DingTalkMsgCenter handler;

    @BeforeAll
    public static void init() {
        if (handler == null) {
            handler = new DingTalkMsgCenter("https://oapi.dingtalk.com/robot/send?access_token=dcd7bb63bcad551ae7ddbaa057f73c687a90e3061324c95c5322fdf6185d85ae");
        }
    }

    @Test
    public void sendText() throws ApiException, InterruptedException {
        handler.addMsg(RobotSendRequestBuilder.builder().textRequest("试一下").atAll().build());
        Thread.sleep(10000);
    }

    @Test
    public void sendLink() throws ApiException, InterruptedException {
        handler.addMsg(RobotSendRequestBuilder
                .builder().linkRequest("https://www.baidu.com", null, "测试", "试一下").atAll().build());
        Thread.sleep(10000);
    }

    @Test
    public void sendMarkdown() throws ApiException, InterruptedException {
        handler.addMsg(RobotSendRequestBuilder
                .builder().markdown("https://www.baidu.com", "```试一下```").atAll().build());
        Thread.sleep(10000);
    }
}
