package com.mengc.nettySpring;

import com.mengc.nettySpring.netty.netty.HttpFileStart;
import com.mengc.nettySpring.staticfile.FileServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import java.io.IOException;

//@SpringBootApplication
public class NettySpringApplication {
	public static DispatcherServlet dispatcherServlet;
	public static void main(String[] args) throws Exception {
		MockServletContext servletContext = new MockServletContext();
		MockServletConfig servletConfig = new MockServletConfig(servletContext);
		servletConfig.addInitParameter("contextConfigLocation","classpath:/META-INF/spring/root-context.xml");
		servletContext.addInitParameter("contextConfigLocation","classpath:/META-INF/spring/root-context.xml");
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setServletContext(servletContext);
		appContext.setServletConfig(servletConfig);
		appContext.setConfigLocation("classpath:SpringmvcContext.xml");
		appContext.refresh();
		NettySpringApplication.dispatcherServlet = new DispatcherServlet(appContext);
		NettySpringApplication.dispatcherServlet.init(servletConfig);
		WebApplicationContext wac = dispatcherServlet.getWebApplicationContext();
		FileServerConfig f = wac.getBean(FileServerConfig.class);
		HttpFileStart hfs = new HttpFileStart();
		hfs.start();
//		SpringApplication.run(NettySpringApplication.class, args);
	}

}
