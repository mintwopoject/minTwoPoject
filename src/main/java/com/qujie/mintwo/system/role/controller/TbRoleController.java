package com.qujie.mintwo.system.role.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.PageUtil;
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

    @PostMapping("/roleList")
    public void roleList(@RequestBody Map<String, Object> params , HttpServletResponse response) throws IOException {
        PageUtil page = roleService.roleList(params);
        this.ajaxRequestTable(response,page);
    }

    @RequestMapping("/info/{Id}")
    public TbRole info(@PathVariable("Id") Integer Id){
        TbRole tbRole = roleService.selectOne(new EntityWrapper<TbRole>().eq("Id", Id));
        return tbRole;
    }


}
