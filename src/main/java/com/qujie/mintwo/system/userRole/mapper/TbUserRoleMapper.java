package com.qujie.mintwo.system.userRole.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.userRole.entity.TbUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Mapper
@Component("TbUserRoleMapper")
public interface TbUserRoleMapper extends BaseMapper<TbUserRole> {

}
