package com.qujie.mintwo.system.roleMenu.controller;


import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import com.qujie.mintwo.system.roleMenu.service.impl.TbRoleMenuServiceImpl;
import com.qujie.mintwo.ustils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/system/tbRoleMenu")
public class TbRoleMenuController {

    @Autowired
    private ITbRoleMenuService tbRoleMenuService;



}
