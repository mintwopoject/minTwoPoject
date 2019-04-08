package com.qujie.mintwo.system.roleMenu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.mapper.TbRoleMenuMapper;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Service
public class TbRoleMenuServiceImpl extends ServiceImpl<TbRoleMenuMapper, TbRoleMenu> implements ITbRoleMenuService {

    @Autowired
    private TbRoleMenuMapper tbRoleMenuMapper;


    @Override
    public List<TbRoleMenu> selectByIds(Integer RoleId) {
        return tbRoleMenuMapper.selectByIds(RoleId);
    }
}
