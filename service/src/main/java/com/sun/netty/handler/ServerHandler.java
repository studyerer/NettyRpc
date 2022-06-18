package com.sun.netty.handler;


import com.sun.entity.Request;
import com.sun.entity.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServerHandler  extends SimpleChannelInboundHandler<Request> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        Response response = new Response();
        response.setRequestId(msg.getRequestId());
        try{
            Object handler = handler(msg);
            response.setResult(handler);
        }catch (Throwable throwable){
            response.setThrowable(throwable);
            throwable.printStackTrace();
        }
        ctx.writeAndFlush(response);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private Object handler(Request request) throws Throwable {

        Class<?> clz = Class.forName(request.getClassName());

        Object serviceBean = applicationContext.getBean(clz);

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();

        Class<?>[] parameterTypes = request.getParameterTypes();

        Object[] parameters = request.getParameters();

        FastClass fastClass = FastClass.create(serviceClass);
        FastMethod fastMethod = fastClass.getMethod(methodName,parameterTypes);

        return fastMethod.invoke(serviceBean,parameters);
    }
}
