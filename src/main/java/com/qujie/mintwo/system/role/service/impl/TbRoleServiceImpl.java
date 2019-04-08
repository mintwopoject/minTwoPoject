package com.qujie.mintwo.system.role.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.base.dao.DaoUtils;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.mapper.TbRoleMapper;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import com.qujie.mintwo.ustils.BetweenUtils;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.PageUtilsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageUtil roleList(Map<String, Object> params) {
        String sql ="select * from ( \n" +
                "select *, ROW_NUMBER() OVER(Order by id) AS RowId from tbRole \n" +
                ") as b where 1=1 ";
        BetweenUtils.setFENYE(" and RowId ");
        return daoUtils.findBySql(sql, PageUtilsFactory.getInstance(params));

    }
}
