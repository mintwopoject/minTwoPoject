package com.qujie.mintwo.system.roleMenu.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Mapper
public interface TbRoleMenuMapper extends BaseMapper<TbRoleMenu> {

    List<TbRoleMenu> selectByIds(@Param("RoleId") Integer RoleId);
}
