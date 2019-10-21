package com.mengc.nettySpring.netty.netty;

import com.mengc.nettySpring.netty.netty.handler.HttpRequestDecoderByteMC;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
* @author ：mengchao
* @date ：Created in 2019/9/19 9:53
* @description：绑定基础channelHandler
*/
public class HttpFileInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
            this.initChannelByte(socketChannel);
    }
    private void initChannelByte(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //绑定解码器
        pipeline.addLast("httpRequestDecoderByteMC",new HttpRequestDecoderByteMC());
    }
}
