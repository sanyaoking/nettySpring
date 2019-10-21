package com.mengc.nettySpring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author ：mengchao
 * @date ：Created in 2019/10/9 17:29
 * @description：初始化Dispath与netty
 */
@Service
public class InitNettyApplicationRunner implements ApplicationRunner,ApplicationListener<ContextRefreshedEvent> {
    ApplicationContext applicationContext;
    @Override
    public void run(ApplicationArguments applicationArguments) {
//        DispatcherServlet dispatcherServlet = new DispatcherServlet();
//        dispatcherServlet.setContextConfigLocation("");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        applicationContext = contextRefreshedEvent.getApplicationContext();
    }
}
