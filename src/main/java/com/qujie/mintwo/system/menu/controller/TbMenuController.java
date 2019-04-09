package com.qujie.mintwo.system.menu.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.menu.entity.Menu;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.menu.service.ITbMenuService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.system.userRole.entity.TbUserRole;
import com.qujie.mintwo.system.userRole.service.ITbUserRoleService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.*;


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
    @Autowired
    private ITbUserService userService;

    @Autowired
    private ITbUserRoleService userRoleService;

    @RequestMapping("/menuList")
    public Object menuList(){
        List<TbMenu> stringObjectMap = tbMenuService.menuList();
        JSONArray jsonArray = JSONArray.fromObject(stringObjectMap);
        return jsonArray;
    }

    /**
     * 导航菜单
     */
    @RequestMapping("/navList")
    public Object navList(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object accountName = session.getAttribute("AccountName");
        TbUser tbUser = userService.selectOne(new EntityWrapper<TbUser>().eq("AccountName", accountName));
        //所有角色
        List<Integer> list3 = new ArrayList<>();
        List<TbUserRole> list = userRoleService.selectList(new EntityWrapper<TbUserRole>().eq("UserId", tbUser.getId()));
        for (int i = 0; i < list.size(); i++) {
            list3.add(list.get(i).getRoleId());
        }

        //根据角色查菜单id
        Set<Integer> list1 = new HashSet<>();
        for (int i = 0; i < list3.size(); i++) {
            if (list3.get(i)!=null||!list3.get(i).equals("")){
                List<TbRoleMenu> tbRoleMenu = roleMenuService.selectList(new EntityWrapper<TbRoleMenu>().eq("RoleId", list3.get(i)));
                for (int j = 0; j <tbRoleMenu.size() ; j++) {
                    list1.add(tbRoleMenu.get(j).getMenuId());
                }

            }
        }
        //获取所有菜单
        List<TbMenu> list2 = new ArrayList<>();
        for (Integer set :list1) {
            if (set!=null||!set.equals("")){
                TbMenu tbMenu = tbMenuService.selectOne(new EntityWrapper<TbMenu>().eq("Id", set));
                list2.add(tbMenu);
            }
        }
        List<TbMenu> tbMenus = tbMenuService.navList(list2);
        JSONArray jsonArray = JSONArray.fromObject(tbMenus);

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
    @PostMapping("/menuChachaox/{id}")
    public List<Menu> menuChachaox(@PathVariable("id") Integer id){
        List<TbRoleMenu> tbRoleMenus1 = roleMenuService.selectByIds(id);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <tbRoleMenus1.size() ; i++) {
            list.add(tbRoleMenus1.get(i).getMenuId());
        }
        List<Menu> menus = tbMenuService.selectLists(list);
//        JSONArray jsonArray = JSONArray.fromObject(menus);
        return menus;
    }


}
