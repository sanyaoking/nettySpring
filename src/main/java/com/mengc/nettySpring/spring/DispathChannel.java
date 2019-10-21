package com.mengc.nettySpring.spring;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.CharsetUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * @author ：mengchao
 * @date ：Created in 2019/10/18 11:19
 * @description：netty转spring请求
 */
public class DispathChannel extends SimpleChannelInboundHandler<FullHttpRequest> {
    private boolean readingChunks;
    private final DispatcherServlet dispatcherServlet;
    private final ServletContext servletContext;
    public DispathChannel(DispatcherServlet dispatcherServlet) {
            this.dispatcherServlet = dispatcherServlet;
            this.servletContext = dispatcherServlet.getServletConfig().getServletContext();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.getDecoderResult().isSuccess()) {
            throw new Exception("解析失败！");
        }

        MockHttpServletRequest servletRequest = createServletRequest(request);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();

        this.dispatcherServlet.service(servletRequest, servletResponse);

        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        EmptyHttpHeaders headers = EmptyHttpHeaders.INSTANCE;
        DefaultHttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
        for (String name : servletResponse.getHeaderNames()) {
            for (Object value : servletResponse.getHeaderValues(name)) {
                response.headers().set(name, value);
            }
        }
        // Write the initial line and the header.
        ctx.write(response);
        InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());
        // Write the content.
        ChannelFuture writeFuture = ctx.write(new ChunkedStream(contentStream));
        ctx.flush();
        writeFuture.addListener(ChannelFutureListener.CLOSE);

    }
    private MockHttpServletRequest createServletRequest(FullHttpRequest httpRequest) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(httpRequest.getUri()).build();

        MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
        servletRequest.setRequestURI(uriComponents.getPath());
        servletRequest.setPathInfo(uriComponents.getPath());
        servletRequest.setMethod(httpRequest.method().name());

        if (uriComponents.getScheme() != null) {
            servletRequest.setScheme(uriComponents.getScheme());
        }
        if (uriComponents.getHost() != null) {
            servletRequest.setServerName(uriComponents.getHost());
        }
        if (uriComponents.getPort() != -1) {
            servletRequest.setServerPort(uriComponents.getPort());
        }

        for (String name : httpRequest.headers().names()) {
            for (String value : httpRequest.headers().getAll(name)) {
                servletRequest.addHeader(name, value);
            }
        }
        servletRequest.setContent(httpRequest.content().array());

        try {
            if (uriComponents.getQuery() != null) {
                String query = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
                servletRequest.setQueryString(query);
            }

            for (Map.Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
                for (String value : entry.getValue()) {
                    servletRequest.addParameter(
                            UriUtils.decode(entry.getKey(), "UTF-8"),
                            UriUtils.decode(value, "UTF-8"));
                }
            }
        }
        catch (Exception ex) {
            // shouldn't happen
            ex.printStackTrace();
        }

        return servletRequest;
    }


}
