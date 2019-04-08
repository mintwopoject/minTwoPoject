package com.qujie.mintwo.system.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.base.dao.DaoUtils;
import com.qujie.mintwo.system.menu.entity.Menu;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.menu.mapper.TbMenuMapper;
import com.qujie.mintwo.system.menu.service.ITbMenuService;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.mapper.TbUserMapper;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.system.userRole.entity.TbUserRole;
import com.qujie.mintwo.system.userRole.service.ITbUserRoleService;
import com.qujie.mintwo.ustils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {
    @Autowired
    private DaoUtils<TbUser> daoUtils;
    
    @Autowired
    private ITbUserService tbUserService;
    @Autowired
    private ITbUserRoleService tbUserRoleService;
    @Override
    public PageUtil userList(Map<String, Object> params) {
        String sql ="select * from ( \n" +
                "select *, ROW_NUMBER() OVER(Order by id) AS RowId from tbUser \n" +
                ") as b where 1=1 ";
        BetweenUtils.setFENYE(" and RowId ");
        return daoUtils.findBySql(sql, PageUtilsFactory.getInstance(params));
    }

    @Override
    public boolean edit(TbUser tbUser) {
        tbUser.setUpdateTime(new Date());
        boolean b = tbUserService.updateById(tbUser);
        String[] split = tbUser.getRoleids();
//        String[] split = roleId.split(",");
        List<TbUserRole> list = new ArrayList<>();
        for (int i = 0; i <split.length ; i++) {
            TbUserRole tbUserRole = new TbUserRole();
            tbUserRole.setUserId(tbUser.getId());
            tbUserRole.setRoleId(Integer.valueOf(split[i]));
            list.add(tbUserRole);
        }
        List<TbUserRole> list1 = tbUserRoleService.selectList(new EntityWrapper<TbUserRole>().eq("UserId", tbUser.getId()));
        for (int i = 0; i < list1.size(); i++) {
            tbUserRoleService.delete(new EntityWrapper<TbUserRole>().eq("UserId",list1.get(i).getUserId()));

        }
        boolean b2 = tbUserRoleService.insertBatch(list);
        if (b==true&b2==true){
            return true;
        }else {
            return false;
        }

    }


    @Override
    public boolean save(TbUser tbUser) {
        tbUser.setCreateTime(new Date());
        tbUser.setPassword("123123");
        boolean b = tbUserService.insert(tbUser);
        String[] split = tbUser.getRoleids();
        List<TbUserRole> list = new ArrayList<>();
        for (int i = 0; i <split.length ; i++) {
            TbUserRole tbUserRole = new TbUserRole();
            tbUserRole.setUserId(tbUser.getId());
            tbUserRole.setRoleId(Integer.valueOf(split[i]));
            list.add(tbUserRole);
        }
        boolean b2 = tbUserRoleService.insertBatch(list);
        if (b==true&b2==true){
            return true;
        }else {
            return false;
        }

    }


    @Override
    public R getUserRole(Integer userId) {
        List<TbUserRole> list1 = tbUserRoleService.selectList(new EntityWrapper<TbUserRole>().eq("UserId", userId));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            list.add(list1.get(i).getRoleId());
        }
        Map<String,Object> map = new HashMap();
        map.put("roleIds",list);
        return R.ok(map);
    }

}
