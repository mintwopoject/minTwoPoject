package com.qujie.mintwo.config.interceptor;

/**
 * Created by huangds on 2017/10/24.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录配置
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{
    public final static String SESSION_KEY="AccountName";

    @Bean
    public SecurityInterceptor getSecurityInterceptor(){
        return new SecurityInterceptor();
    }

    public void  addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        List<String> list = new ArrayList();
        System.out.println("注册拦截器");
        list.add("/login.html");
//        list.add("/login");
        list.add("/system/login");
        list.add("/static/**");
        list.add("/vercode/loginValidateCode");
        list.add("/assets/avatars/beijin.jpg");
        list.add("/assets/avatars/beijin.jpg");
        list.add("/system/*.js");
        list.add("/assets/**");
        list.add("/lib/**");
        list.add("/error");
        list.add("/loginVerify");
        list.add("/logout");
        list.add("/system/tbUser/AccountName");
//        list.add("/main.html");
        addInterceptor.excludePathPatterns(list);

        addInterceptor.addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        System.out.println("123123123");
        super.addResourceHandlers(registry);
    }


    private class SecurityInterceptor extends HandlerInterceptorAdapter{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws IOException {
            HttpSession session = request.getSession();

//            判断是否已有该用户登录的session
            if(session.getAttribute(SESSION_KEY) != null){
                return true;
            }
            System.out.println(session.getAttribute(SESSION_KEY));

//            跳转到登录页
            String url = "/login.html";
            response.sendRedirect(url);
            return false;
        }
    }
}
