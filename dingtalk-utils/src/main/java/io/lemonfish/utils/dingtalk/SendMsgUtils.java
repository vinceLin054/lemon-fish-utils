package io.lemonfish.utils.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author linwensi
 * @date 2020-09-02 4:27 下午
 */
@Slf4j
public class SendMsgUtils {

    public static void send(String serverUrl, OapiRobotSendRequest request) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(serverUrl);
        OapiRobotSendResponse response = client.execute(request);
        if (!response.isSuccess()) {
            log.error("send dingtalk message failed, errorCode: {}, errorMsg: {}", response.getErrcode(), response.getErrmsg());
        }
    }

}
