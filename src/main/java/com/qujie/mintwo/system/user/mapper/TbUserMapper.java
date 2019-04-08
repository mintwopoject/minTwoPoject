package com.qujie.mintwo.system.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.user.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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
@Component("TbUserMapper")
public interface TbUserMapper extends BaseMapper<TbUser> {


}
