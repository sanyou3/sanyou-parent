package com.sanyou.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置类
 *
 * @author sanyou
 * @date 2022/4/16 15:39
 */
@Configuration
public class WebSocketConfiguration {

    /**
     * 这个类的主要注册每个加了{@link javax.websocket.server.ServerEndpoint}的 spring bean节点，这算是spring整合websocket的一个体现
     * 具体是怎么实现注册的，可以看看 {@link ServerEndpointExporter#afterSingletonsInstantiated()}方法的实现
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
