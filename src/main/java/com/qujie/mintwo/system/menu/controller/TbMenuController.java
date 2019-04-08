package com.qujie.mintwo.system.menu.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.menu.service.ITbMenuService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *  菜单controller
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/system/tbmenu")
public class TbMenuController{
    @Autowired
    private ITbMenuService tbMenuService;

    @Autowired
    private ITbRoleMenuService roleMenuService;

    @RequestMapping("/menuList")
    public Object menuList(){
        List<TbMenu> stringObjectMap = tbMenuService.menuList();
        JSONArray jsonArray = JSONArray.fromObject(stringObjectMap);
        return jsonArray;
    }

    @RequestMapping("/info/{ParentId}")
    public TbMenu info(@PathVariable("ParentId") Integer ParentId){
        TbMenu tbMenu = tbMenuService.selectOne(new EntityWrapper<TbMenu>().eq("Id", ParentId));
        return tbMenu;
    }

    /**
     * 修改拿到的父id
     */
    @RequestMapping("/infoto/{id}")
    public TbMenu infoto(@PathVariable("id") Integer id){
        TbMenu tbMenu = tbMenuService.selectOne(new EntityWrapper<TbMenu>().eq("Id", id));
        TbMenu tbMenu1 = tbMenuService.selectOne(new EntityWrapper<TbMenu>().eq("id", tbMenu.getParentId()));
        return tbMenu1;
    }

    /**
     * 修改拿到当前数据
     */
    @RequestMapping("/infotos/{id}")
    public TbMenu infotos(@PathVariable("id") Integer id){
        TbMenu tbMenu = tbMenuService.selectOne(new EntityWrapper<TbMenu>().eq("Id", id));
        return tbMenu;
    }

    /**
     * 新增菜单
     */
    @RequestMapping("/add")
    public boolean add(@RequestBody TbMenu tbMenu){
        tbMenu.setCreateTime(new Date());
        tbMenu.setUpdateTime(new Date());
        tbMenu.setCreateBy("admin");
        boolean insert = tbMenuService.insert(tbMenu);
        if (insert==true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 修改菜单
     */
    @RequestMapping("/edit")
    public boolean edit(@RequestBody TbMenu tbMenu){
        tbMenu.setUpdateTime(new Date());
        tbMenu.setUpdateBy("admin");
        boolean update = tbMenuService.updateById(tbMenu);
        if (update==true){
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        boolean id1 = tbMenuService.delete(new EntityWrapper<TbMenu>().eq("Id", id));
        if (id1==true){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取所有菜单及选中
     * @param id
     * @return
     */
    @PostMapping("/muenInfo/{id}")
    public Object muenInfo(@PathVariable("id") Integer id){
        List<TbMenu> tbMenus = tbMenuService.menuInfoList(id);
        List<TbMenu> tbMenus1 = tbMenuService.menuInfoLists(tbMenus);
        JSONArray jsonArray = JSONArray.fromObject(tbMenus1);
        return jsonArray;
    }

    /**
     * 获取所有选中的菜单
     */
    @RequestMapping("/menuChachaox/{id}")
    public List<TbMenu> menuChachaox(@PathVariable("id") Integer id){
        List<TbMenu> tbMenus = tbMenuService.menuInfoList(id);
        if (tbMenus.size()>0){
            return tbMenus;
        }else {
            return null;
        }
    }


}
