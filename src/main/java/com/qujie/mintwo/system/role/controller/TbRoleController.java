package com.qujie.mintwo.system.role.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.config.interceptor.WebSecurityConfig;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.R;
import net.sf.json.JSONArray;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/role/tbRole")

public class TbRoleController extends AbstractController {

    @Autowired
    private ITbRoleService roleService;
    @Autowired
    private ITbRoleMenuService roleMenuService;

    //角色列表
    @PostMapping("/roleList")
    public void roleList(@RequestBody Map<String, Object> params , HttpServletResponse response) throws IOException {
        PageUtil page = roleService.roleList(params);
        this.ajaxRequestTable(response,page);
    }
    //角色单条
    @RequestMapping("/info/{Id}")
    public TbRole info(@PathVariable("Id") Integer Id){
        TbRole tbRole = roleService.selectOne(new EntityWrapper<TbRole>().eq("Id", Id));
        return tbRole;
    }
    //角色修改
    @RequestMapping("/edit")
    public R edit(@RequestBody TbRole role, HttpSession session){
        boolean b = roleService.updateByIds(role,session.getAttribute(WebSecurityConfig.SESSION_KEY).toString());
        if (b==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }

//   删除
    @RequestMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        boolean b = roleService.deleteRole(id);

        if (b==true){
            return true;
        }else {
            return false;
        }
    }

    //角色新增
    @RequestMapping("/save")
    public R save(@RequestBody TbRole role,HttpSession session){
        boolean b = roleService.saves(role,session.getAttribute(WebSecurityConfig.SESSION_KEY).toString());
        if (b==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }

    //所有角色获取
    @RequestMapping("/selectRoleList")
    public List<TbRole> selectRoleList(){
        List<TbRole> tbRoles = roleService.selectList(new EntityWrapper<>());
        return tbRoles;
    }


}
