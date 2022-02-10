package com.lzl.wiki.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h3>wiki</h3>
 * <p>登录拦截器获取前端head中的token</p>
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-10 15:13
 **/

@Component
public class LoginInterceptor implements HandlerInterceptor {
    //    日志放入类
    private static final Logger LOG= LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
        private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        打印信息
        LOG.info("------------- LoginInterceptor 开始 -------------");
        long startTime=System.currentTimeMillis();
        request.setAttribute("requestStartTime",startTime);
//        OPTION请求不做校验
//        前后端分离的架构，前端会发一个OPTION请求先做预检，对预检请求不做校验
        if (request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }
        String path=request.getRequestURI().toString();
        LOG.info("接口登录拦截:,path:{}",path);

//        获取header的token参数
        String token =request.getHeader("token");
        LOG.info("登录校验开始,token:{}",token);
        if (token==null||token.isEmpty()){
            LOG.info("token为空,请求被拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        Object object=redisTemplate.opsForValue().get(token);
        if (object==null){
            LOG.warn("token无效，请求被拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }else {
            LOG.info("已登录:{}",object);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime =(Long) request.getAttribute("requestStartTime");
        LOG.info("------------- LoginInterceptor 结束 耗时：{} ms -------------");
    }
}
