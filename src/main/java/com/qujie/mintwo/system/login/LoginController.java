package com.qujie.mintwo.system.login;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController extends AbstractController {

    @Autowired
    private ITbUserService userService;

    @PostMapping("/sys/login")
    public Boolean login(@RequestBody TbUser tbUser, HttpSession session){
        TbUser tbUse1 = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", tbUser.getAccountName()).eq("Password", tbUser.getPassword()));
        if (tbUse1!=null){
            session.setAttribute("AccountName",tbUser.getAccountName());
            return true;
        }else {
            return false;
        }

    }

   /* @RequestMapping(value = "/AccountName", produces = { "application/json;charset=UTF-8" })
    public String AccountName(HttpServletRequest request){
        HttpSession session =  request.getSession();
        Object accountName =  session.getAttribute("AccountName");
        TbUser tbUser = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", accountName));
        String realName = tbUser.getRealName();
//        System.out.println(realName);
        return realName;
    }*/




}
