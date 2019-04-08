package com.qujie.mintwo.system.menu.service;

import com.baomidou.mybatisplus.service.IService;
import com.qujie.mintwo.system.menu.entity.TbMenu;

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
public interface ITbMenuService extends IService<TbMenu> {

    List<TbMenu> menuList();

    List<TbMenu> menuInfoList(Integer id);

    List<TbMenu> menuInfoLists(List<TbMenu> list);





}
