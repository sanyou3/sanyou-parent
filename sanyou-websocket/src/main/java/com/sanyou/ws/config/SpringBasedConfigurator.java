package com.sanyou.ws.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

/**
 * 这个类是继承了 ServerEndpointConfig.Configurator 重写了 {@link #getEndpointInstance} 方法，
 * 主要是因为 是通过调用getEndpointInstance方法来获取每个连接对应调用的对象，
 * 而getEndpointInstance方法默认是通过直接通过反射构造的，导致spring的类无法通过@Resource 这类注解直接注入，所以重写了getEndpointInstance方法，
 * 让每个连接对应调用的对象是从spring容器中获取
 *
 * @author sanyou
 * @date 2022/4/16 15:46
 */
@Component
public class SpringBasedConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBasedConfigurator.applicationContext = applicationContext;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return applicationContext.getBean(clazz);
    }

}
