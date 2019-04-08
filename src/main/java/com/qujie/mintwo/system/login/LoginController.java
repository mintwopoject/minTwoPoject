package com.qujie.mintwo.system.login;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController extends AbstractController {

    @Autowired
    private ITbUserService userService;

    @PostMapping("/login")
    public boolean login(String AccountName,String Password, HttpSession session){

        TbUser tbUser = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", AccountName).eq("Password", MD5Utils.getMD5Code(Password)));
        if (tbUser!=null){
            session.setAttribute("AccountName",AccountName);
            return true;
        }else {
            return false;
        }

    }




}
