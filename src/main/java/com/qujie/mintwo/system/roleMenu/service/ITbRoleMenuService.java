package com.qujie.mintwo.system.roleMenu.service;

import com.baomidou.mybatisplus.service.IService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
public interface ITbRoleMenuService extends IService<TbRoleMenu> {

    List<TbRoleMenu> selectByIds( Integer RoleId);

}
