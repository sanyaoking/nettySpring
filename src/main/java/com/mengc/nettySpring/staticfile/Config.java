package com.mengc.nettySpring.staticfile;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author ：mengchao
 * @date ：Created in 2019/10/18 15:38
 * @description：
 */
public class Config {
    @Bean(name = "dispatcherServlet")
    public DispatcherServlet getDispatcherServlet(){
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation("SpringmvcContext.xml");
        return dispatcherServlet;
    }
}
