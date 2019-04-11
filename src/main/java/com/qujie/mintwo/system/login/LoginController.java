package com.qujie.mintwo.system.login;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.config.UserInterceptor;
import com.qujie.mintwo.config.kaptcha.controller.MainController;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController extends AbstractController {

    @Autowired
    private ITbUserService userService;

    @PostMapping("/sys/login")
    public Integer login(@RequestBody TbUser tbUser, HttpSession session){
        TbUser tbUse1 = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", tbUser.getAccountName()).eq("Password", MD5Utils.getMD5Code(tbUser.getPassword())));
        String s = session.getAttribute(MainController.LOGIN_VALIDATE_CODE).toString();
        if (tbUse1!=null){
            if (s.equals(tbUser.getValidateCode())){
                session.setAttribute(UserInterceptor.SESSION_KEY,tbUser.getAccountName());
                AbstractController.USERNAME=session.getAttribute(UserInterceptor.SESSION_KEY).toString();
                return 0;//登陆成功
            }else {
                return 2;//验证码错误
            }

        }else {
            return 1;//用户名或密码不正确
        }

    }

    @RequestMapping("/AccountName")
    public TbUser AccountName(HttpServletRequest request){

        HttpSession session = null;
        try {
            session = request.getSession();
            String accountName =  session.getAttribute(UserInterceptor.SESSION_KEY).toString();
            TbUser tbUser = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", accountName));
            return tbUser;
        } catch (Exception e) {
            return null;
        }

    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.removeAttribute(UserInterceptor.SESSION_KEY);
    }


}
