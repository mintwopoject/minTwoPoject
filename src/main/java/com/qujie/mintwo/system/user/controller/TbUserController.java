package com.qujie.mintwo.system.user.controller;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qujie.mintwo.config.interceptor.WebSecurityConfig;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.R;
import com.qujie.mintwo.ustils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jobob
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/system/tbUser")
public class TbUserController  extends AbstractController {

    @Autowired
    private ITbUserService userService;
    @Resource
    private RedisUtils redisUtils;

    //用户列表
    @PostMapping("/userList")
    public void roleList(@RequestBody Map<String, Object> params , HttpServletResponse response) throws IOException {
        PageUtil page = userService.userList(params);
        this.ajaxRequestTable(response,page);
    }

    //用户单条
    @RequestMapping("/info/{id}")
    public TbUser info(@PathVariable("id")Integer id){
        TbUser tbUser = userService.selectOne(new EntityWrapper<TbUser>().eq("ID", id));
        return tbUser;
    }
    //用户修改
    @RequestMapping("/edit")
    public R edit(@RequestBody String request,HttpSession session){
        boolean edit = userService.edit(request,session.getAttribute(WebSecurityConfig.SESSION_KEY).toString());
        if (edit==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }

    //用户新增
    @RequestMapping("/save")
    public R save(@RequestBody String request,HttpSession session){
        boolean save = userService.save(request,session.getAttribute(WebSecurityConfig.SESSION_KEY).toString());
        if (save==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }

    }


    //   删除
    @RequestMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        boolean id1 = userService.delete(new EntityWrapper<TbUser>().eq("Id", id));
        if (id1==true){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取角色信息
     */
    @RequestMapping("/getUserRole/{userId}")
    public R getUserRole(@PathVariable("userId") Integer userId) {
        return userService.getUserRole( userId );
    }

    //用户校验
    @GetMapping("/checkName")
    public void checkmodelName(HttpServletResponse response) throws IOException {
        Map<String, Boolean> map = new HashMap<>();
        map.put("accountName", userService.checkName(GetParameterValues().get("accountName"),GetParameterValues().get("id")));
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
            ajaxRequest(response, resultString);
        }

    }

    //获取当前登陆人
    @RequestMapping("/AccountName")
    public TbUser AccountName(HttpSession session){
        try {
            String accountName =  session.getAttribute(WebSecurityConfig.SESSION_KEY).toString();
            TbUser tbUser = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", accountName));
            return tbUser;
        } catch (Exception e) {
            return null;
        }
    }



}
