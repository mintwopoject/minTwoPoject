package com.qujie.mintwo.system.login;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.verificationCode.MainController;
import com.qujie.mintwo.config.interceptor.WebSecurityConfig;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.MD5Utils;
import com.qujie.mintwo.ustils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by huangds on 2017/10/24.
 */
@Controller
public class LoginController {
    @Autowired
    private ITbUserService userService;

    @Resource
    private  RedisUtils redisUtils;

//    @GetMapping("/")
//    public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY)String account, Model model){
//        return "main.html";
//    }


    @PostMapping("/loginVerify")
    public synchronized void loginVerify(String accountName ,String password ,String validateCode, HttpSession session, HttpServletResponse response) throws IOException {
        TbUser tbUse1 = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", accountName).eq("Password", MD5Utils.getMD5Code(password)));
        String s = session.getAttribute(MainController.LOGIN_VALIDATE_CODE).toString();
        if (tbUse1!=null){
            if (s.equals(validateCode)){
                redisUtils.set(WebSecurityConfig.SESSION_KEY,accountName);
                AbstractController.USERNAME=redisUtils.get(WebSecurityConfig.SESSION_KEY).toString();
                response.sendRedirect("/main.html");
//                return 1;//登陆成更
            }else {
                response.sendRedirect("/login.html");
//                return 2;//验证码错误
            }

        }else {
            response.sendRedirect("/login.html");
//            return 3;//用户名或密码错误
        }
//        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        redisUtils.delete(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login.html";
    }


}
