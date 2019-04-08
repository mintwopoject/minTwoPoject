package com.qujie.mintwo.system.role.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.R;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public R edit(@RequestBody TbRole role){
        boolean b = roleService.updateByIds(role);
        if (b==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }

    //角色新增
    @RequestMapping("/save")
    public R save(@RequestBody TbRole role){
        boolean b = roleService.saves(role);
        if (b==true){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }


}
