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
import com.qujie.mintwo.ustils.BetweenUtils;
import com.qujie.mintwo.ustils.MenuUtils2;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.PageUtilsFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {
    @Autowired
    private DaoUtils<TbUser> daoUtils;
    @Override
    public PageUtil userList(Map<String, Object> params) {
        String sql ="select * from ( \n" +
                "select *, ROW_NUMBER() OVER(Order by id) AS RowId from tbUser \n" +
                ") as b where 1=1 ";
        BetweenUtils.setFENYE(" and RowId ");
        return daoUtils.findBySql(sql, PageUtilsFactory.getInstance(params));
    }
}
