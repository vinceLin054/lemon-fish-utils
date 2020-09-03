package io.lemonfish.utils.dingtalk;

import com.dingtalk.api.request.OapiRobotSendRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author linwensi
 * @date 2020-09-03 9:24 上午
 */
@Slf4j
public class DingTalkMsgCenter {

    private String serverUrl;

    private BlockingQueue<OapiRobotSendRequest> robotSendRequestQueue;

    public DingTalkMsgCenter(String serverUrl) {
        this.serverUrl = serverUrl;
        this.robotSendRequestQueue = new LinkedBlockingDeque<OapiRobotSendRequest>(100);
        run();
    }

    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        OapiRobotSendRequest request = robotSendRequestQueue.take();
                        SendMsgUtils.send(serverUrl, request);
                    } catch (Exception e) {
                        //do nothing
                    }
                }
            }
        }).start();
    }

    public void addMsg(OapiRobotSendRequest request) {
        boolean offer = robotSendRequestQueue.offer(request);
        if (!offer) {
            log.error("robotSendRequestQueue is full, skip put request");
        }
    }


}
