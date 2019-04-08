package com.qujie.mintwo.system.role.service;

import com.baomidou.mybatisplus.service.IService;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.ustils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
public interface ITbRoleService extends IService<TbRole> {

    PageUtil roleList(Map<String, Object> params);



}
