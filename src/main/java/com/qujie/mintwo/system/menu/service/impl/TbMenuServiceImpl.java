package com.qujie.mintwo.system.menu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.base.dao.DaoUtils;
import com.qujie.mintwo.system.menu.entity.Menu;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.menu.mapper.TbMenuMapper;
import com.qujie.mintwo.system.menu.service.ITbMenuService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import com.qujie.mintwo.ustils.MenuUtils2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Service
public class TbMenuServiceImpl extends ServiceImpl<TbMenuMapper, TbMenu> implements ITbMenuService {
    @Autowired
    private DaoUtils<TbMenu> daoUtils;
    @Autowired
    private ITbMenuService tbMenuService;
    @Autowired
    private ITbRoleMenuService roleMenuService;

    @Autowired
    private TbMenuMapper tbMenuMapper;
    //查询菜单
    public List<TbMenu> menuList(){
        List<TbMenu> tbMenus = tbMenuService.selectList(new EntityWrapper<>());

        List<TbMenu> menuList = new ArrayList<TbMenu>();
        // 先找到所有的一级菜单
        for (int i = 0; i < tbMenus.size(); i++) {
            // 一级菜单没有parentId
            if (tbMenus.get(i).getParentId()==0) {
                menuList.add(tbMenus.get(i));
            }
        }
        Collections.sort(menuList, sort());
        for (int i = 0; i <menuList.size() ; i++) {
            List<TbMenu> child = getChild(String.valueOf(menuList.get(i).getId()), tbMenus);
            menuList.get(i).setNodes(child);
        }
        /*for (TbMenu menu : menuList) {
            //将父节点和所有节点传入得到所有子节点
            menu.setChildMenus(getChild(menu.getId(), tbMenus));
        }*/
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("menu", menuList);
        return menuList;
//        return jsonMap;

    }

    //查询所有子菜单
    private List<TbMenu> getChild(String id, List<TbMenu> rootMenu) {
        // 子菜单
        List<TbMenu> childList = new ArrayList<>();
        for (TbMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
//            if (menu.getParentId()!=null) {
                //判断子父节点是否相等
                if (id.equals(String.valueOf(menu.getParentId()))) {
                    //相等则add
                    childList.add(menu);
                }
//            }
        }

        Collections.sort(childList, sort());
        // 把子菜单的子菜单再循环一遍
        for (TbMenu menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isBlank(menu.getLinkAddress())) {
                // 递归
                menu.setNodes(getChild(String.valueOf(menu.getId()), rootMenu));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
    //修改时带出所有菜单
    public List<TbMenu> menuInfoLists(List<TbMenu> list){
        List<TbMenu> tbMenus = tbMenuService.selectList(new EntityWrapper<>());

        List<TbMenu> menuList = new ArrayList<TbMenu>();
        // 先找到所有的一级菜单
        for (int i = 0; i < tbMenus.size(); i++) {
            // 一级菜单没有parentId
            if (tbMenus.get(i).getParentId()==0) {
                menuList.add(tbMenus.get(i));
            }
        }

        for (int i = 0; i <menuList.size() ; i++) {
            List<TbMenu> child = getChild1(String.valueOf(menuList.get(i).getId()), list);
            menuList.get(i).setNodes(child);
        }
        /*for (TbMenu menu : menuList) {
            //将父节点和所有节点传入得到所有子节点
            menu.setChildMenus(getChild(menu.getId(), tbMenus));
        }*/
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("menu", menuList);
        return menuList;
//        return jsonMap;

    }

    //查询所有子菜单
    private List<TbMenu> getChild1(String id, List<TbMenu> list) {
        // 子菜单
        List<TbMenu> childList = new ArrayList<>();
        for (TbMenu menu : list) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu!=null) {
//            if (menu.getParentId()!=null) {
                //判断子父节点是否相等
                if (id.equals(String.valueOf(menu.getParentId()))) {
                    //相等则add
                    childList.add(menu);
                }
            }
//            }
        }


        // 把子菜单的子菜单再循环一遍
        for (TbMenu menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isBlank(menu.getLinkAddress())) {
                // 递归
                menu.setNodes(getChild1(String.valueOf(menu.getId()), list));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    public List<TbMenu> menuInfoList(Integer id) {
        List list = tbMenuMapper.menuInfoList(id);
        return list;
    }

    @Override
    public List<Menu> selectLists(List<Integer> checkmenuid) {
        String sql = " select * from tbMenu";
        List<Map> bySql = daoUtils.findBySql(sql);
        List<Menu> menus=new ArrayList<>();
        for (Map sysTMenu : bySql) {
            Menu menu=new Menu();
            menu.setId(Integer.valueOf(sysTMenu.get("Id").toString()));
            menu.setMenuFather(Integer.valueOf(sysTMenu.get("ParentId").toString()));
            menu.setText(sysTMenu.get("Name").toString());
            menu.setMenuOrder(Integer.valueOf(sysTMenu.get("Sort").toString()));
            menus.add(menu);
        }
        List<Menu> tree = MenuUtils2.findTree(menus,checkmenuid);
        return tree;

    }




    //查询菜单
    public List<TbMenu> navList(List<TbMenu> tbMenus){

        List<TbMenu> menuList = new ArrayList<TbMenu>();
        // 先找到所有的一级菜单
        for (int i = 0; i < tbMenus.size(); i++) {
            // 一级菜单没有parentId
            if (tbMenus.get(i).getParentId()==0) {
                menuList.add(tbMenus.get(i));
            }
        }
        Collections.sort(menuList, sort());
        for (int i = 0; i <menuList.size() ; i++) {
            List<TbMenu> child = getChild2(String.valueOf(menuList.get(i).getId()), tbMenus);
            menuList.get(i).setNodes(child);
        }
        /*for (TbMenu menu : menuList) {
            //将父节点和所有节点传入得到所有子节点
            menu.setChildMenus(getChild(menu.getId(), tbMenus));
        }*/
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("menu", menuList);
        return menuList;
//        return jsonMap;

    }

    //查询所有子菜单
    private List<TbMenu> getChild2(String id, List<TbMenu> rootMenu) {
        // 子菜单
        List<TbMenu> childList = new ArrayList<>();
        for (TbMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
//            if (menu.getParentId()!=null) {
            //判断子父节点是否相等
            if (id.equals(String.valueOf(menu.getParentId()))) {
                //相等则add
                childList.add(menu);
            }
//            }
        }


        // 把子菜单的子菜单再循环一遍
        Collections.sort(childList, sort());
        for (TbMenu menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isBlank(menu.getLinkAddress())) {
                // 递归
                menu.setNodes(getChild2(String.valueOf(menu.getId()), rootMenu));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    /*
     * 排序,根据order排序
     */
    public static Comparator<TbMenu> sort(){
        Comparator<TbMenu> comparator = new Comparator<TbMenu>() {
            @Override
            public int compare(TbMenu o1, TbMenu o2) {
                if(o1.getSort() != o2.getSort()){
                    return o1.getSort() - o2.getSort();
                }
                return 0;
            }
        };
        return comparator;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Integer id) {
        TbMenu tbMenu = tbMenuService.selectOne(new EntityWrapper<TbMenu>().eq("Id", id));
        if (tbMenu.getParentId()==0){
            return false;
        }
        boolean id1 = tbMenuService.delete(new EntityWrapper<TbMenu>().eq("Id", id));
        List<TbRoleMenu> tbRoleMenus = roleMenuService.selectList(new EntityWrapper<TbRoleMenu>().eq("MenuId", id));
        if (tbRoleMenus.size()>0){
            for (int i = 0; i <tbRoleMenus.size() ; i++) {
                roleMenuService.delete(new EntityWrapper<TbRoleMenu>().eq("MenuId", tbRoleMenus.get(i).getMenuId()));
            }
        }
        if (id1==true){
            return true;
        }else {
            return false;
        }
    }

}
