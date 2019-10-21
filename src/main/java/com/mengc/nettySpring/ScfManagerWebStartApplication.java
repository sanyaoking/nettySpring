package com.mengc.nettySpring;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author ：mengchao
 * @date ：Created in 2019/10/18 15:45
 * @description：使用外置tomcat  这里需要类似于web.xml的配置方式来启动spring上下文，这里需要配置web.xml
 * 使用内嵌的tomcat时SpringbootdemoApplication的main方法启动的方式因此重写SpringBootServletInitializer的configure方法，
 * 在Application类的同级添加一个SpringBootStartApplication类
 */
public class ScfManagerWebStartApplication  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ScfManagerWebStartApplication.class);
    }
}
