package com.sanyou.ws.server;

import com.sanyou.ws.config.SpringBasedConfigurator;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端
 *
 * @author sanyou
 * @date 2022/4/16 15:44
 */
@Component
@ServerEndpoint(value = "/chat/{username}", configurator = SpringBasedConfigurator.class)
public class WebSocketServer {

    private final Map<Session, String> userSessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        userSessionMap.put(session, username);
        multicastMessage(session, "欢迎" + username + "加入群聊!");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        String username = userSessionMap.get(session);
        multicastMessage(session, username + ": " + message);
    }

    @OnClose
    public void onClose(Session session) {
        String username = userSessionMap.remove(session);
        multicastMessage(session, username + "退出群聊。");
    }

    private void multicastMessage(Session session, String message) {
        for (Session userSession : userSessionMap.keySet()) {
            if (userSession == session) {
                //是自己，忽略
                continue;
            }
            try {
                userSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
