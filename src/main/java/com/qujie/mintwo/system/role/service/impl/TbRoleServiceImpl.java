package com.qujie.mintwo.system.role.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.base.dao.DaoUtils;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.mapper.TbRoleMapper;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import com.qujie.mintwo.ustils.BetweenUtils;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.PageUtilsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements ITbRoleService {

    @Autowired
    private ITbRoleService roleService;

    @Autowired
    private DaoUtils<TbRole> daoUtils;
    @Autowired
    private ITbRoleMenuService tbRoleMenuService;

    @Override
    public PageUtil roleList(Map<String, Object> params) {
        String sql ="select * from ( \n" +
                "select *, ROW_NUMBER() OVER(Order by id) AS RowId from tbRole \n" +
                ") as b where 1=1 ";
        BetweenUtils.setFENYE(" and RowId ");
        return daoUtils.findBySql(sql, PageUtilsFactory.getInstance(params));

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateByIds(TbRole role) {
        String menuids = role.getMenuids();
        String[] split=null;
        if (menuids!=null&&menuids!=""){
            split = menuids.split(",");
        }
        role.setUpdateTime(new Date());
        List<TbRoleMenu> tbRoleMenus = tbRoleMenuService.selectList(new EntityWrapper<TbRoleMenu>().eq("RoleId", role.getId()));
        boolean b = roleService.updateById(role);
        boolean b1=false;
        if (tbRoleMenus.size()>0){
            for (int i = 0; i < tbRoleMenus.size(); i++) {
                b1 =  tbRoleMenuService.deleteById(tbRoleMenus.get(i));
            }
        }
        for (int i = 0; i <split.length ; i++) {
            TbRoleMenu tbRoleMenu = new TbRoleMenu();
            tbRoleMenu.setRoleId(role.getId());
            tbRoleMenu.setMenuId(Integer.valueOf(split[i]));
            tbRoleMenuService.insert(tbRoleMenu);
        }

        if (b==true&&b1==true){
            return true;
        }else {
            return false;
        }


    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saves(TbRole role) {
        String menuids = role.getMenuids();
        String[] split=null;
        if (menuids!=null&&menuids!=""){
            split = menuids.split(",");
        }
        role.setCreateTime(new Date());
        boolean b = roleService.insert(role);

        for (int i = 0; i <split.length ; i++) {
            TbRoleMenu tbRoleMenu = new TbRoleMenu();
            tbRoleMenu.setRoleId(role.getId());
            tbRoleMenu.setMenuId(Integer.valueOf(split[i]));
            tbRoleMenuService.insert(tbRoleMenu);
        }

        if (b==true){
            return true;
        }else {
            return false;
        }


    }
}
