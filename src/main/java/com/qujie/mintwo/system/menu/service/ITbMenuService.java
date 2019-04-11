package com.qujie.mintwo.system.menu.service;

import com.baomidou.mybatisplus.service.IService;
import com.qujie.mintwo.system.menu.entity.Menu;
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
    //查询所有导航栏菜单
    List<TbMenu> navList(List<TbMenu> tbMenus);

    List<TbMenu> menuInfoList(Integer id);

    List<TbMenu> menuInfoLists(List<TbMenu> list);


    List<Menu> selectLists(List<Integer> checkmenuid);

    //删除
    Boolean deleteMenu(Integer id);





}
