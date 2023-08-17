package com.cymal.handler;

import com.cymal.model.DchBody;
import com.cymal.config.DchServiceConfig;
import com.cymal.config.DchServiceConfigCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class DchInvokerMethodHandler extends ChannelInboundHandlerAdapter {

    public static final transient Object NO_EXIST = new Object();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DchBody body = (DchBody) msg;
        Object result = null;
        try {
            result = InvokerMethod(body);
        } catch (Exception e) {
            result = NO_EXIST;
        } finally {
            ctx.channel().writeAndFlush(result);
        }
    }

    private Object InvokerMethod(DchBody body) {

        String interfaceName = body.getInterfaceName();
        String methodName = body.getMethodName();
        Class<?>[] paramtersType = body.getParamtersType();
        Object[] paramtersValue = body.getParamtersValue();

        DchServiceConfig dchServiceConfig = DchServiceConfigCenter.getInstance().getDchServiceConfig(interfaceName);
        Object refImpl = dchServiceConfig.getRef();

        Object returnValue = null;
        try {
            Class<?> cla = refImpl.getClass();
            Method method = cla.getMethod(methodName, paramtersType);
            returnValue = method.invoke(refImpl, paramtersValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnValue;

    }

}
