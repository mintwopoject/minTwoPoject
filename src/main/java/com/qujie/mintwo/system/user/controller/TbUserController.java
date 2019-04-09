package com.qujie.mintwo.system.user.controller;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public R edit(@RequestBody TbUser tbUser){
        boolean edit = userService.edit(tbUser);
        if (edit==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }

    //用户新增
    @RequestMapping("/save")
    public R save(@RequestBody TbUser tbUser){
        boolean edit = userService.save(tbUser);
        if (edit==true){
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


}
