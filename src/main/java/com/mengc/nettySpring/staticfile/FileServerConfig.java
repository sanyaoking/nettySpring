package com.mengc.nettySpring.staticfile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ：mengchao
 * @date ：Created in 2019/10/18 11:05
 * @description：springboot静态文件服务器配置
 */
@Configuration
public class FileServerConfig extends WebMvcConfigurerAdapter {
    @Value("${local.fileserver.dir}")
    private String localFileServerDir;

    @Value("${local.fileserver.path}")
    private String localFileServerPath;

    //访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/" + this.getLocalFileServerPath() + "/**").addResourceLocations("file:" + this.getLocalFileServerDir() + "/");

        // 本地文件夹要以"flie:" 开头，文件夹要以"/" 结束，example：
        //registry.addResourceHandler("/abc/**").addResourceLocations("file:D:/pdf/");
        super.addResourceHandlers(registry);
    }

    /**
     * 文件实际路径转为服务器url路径
     * @param absolutePath
     * @return
     */
    public String toServerPath(String absolutePath) {
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        return "/" + absolutePath.replace(localFileServerDir, localFileServerPath);
    }

    public String getLocalFileServerDir() {
        return localFileServerDir;
    }

    public void setLocalFileServerDir(String localFileServerDir) {
        this.localFileServerDir = localFileServerDir;
    }

    public String getLocalFileServerPath() {
        return localFileServerPath;
    }

    public void setLocalFileServerPath(String localFileServerPath) {
        this.localFileServerPath = localFileServerPath;
    }
}
