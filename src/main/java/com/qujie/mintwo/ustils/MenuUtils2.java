package com.qujie.mintwo.ustils;



import com.qujie.mintwo.system.menu.entity.Menu;

import java.util.*;

public class MenuUtils2 {

    /*
     * 排序,根据order排序
     */
    public static Comparator<Menu> order(){
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getMenuOrder() != o2.getMenuOrder()){
                    return o1.getMenuOrder() - o2.getMenuOrder();
                }
                return 0;
            }
        };
        return comparator;
    }
    public static Comparator<Menudepartment> order1(){
        Comparator<Menudepartment> comparator = new Comparator<Menudepartment>() {
            @Override
            public int compare(Menudepartment o1, Menudepartment o2) {
                if(o1.getDepartmentOrder() != o2.getDepartmentOrder()){
                    return o1.getDepartmentOrder() - o2.getDepartmentOrder();
                }
                return 0;
            }
        };
        return comparator;
    }


    public static List<Menu> findTree(List<Menu> allMenu,List<Integer> checkmenuid){
        if(checkmenuid.size()==0){checkmenuid=null;}
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询所有菜单
            //根节点
            List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getMenuFather()==0){//父节点是0的，为根节点。
                   /* nav.*/
                    rootMenu.add(nav);
                }
            }

            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild(String.valueOf(nav.getId()), allMenu,checkmenuid);

                //判断是否选中父节点
                boolean checkStatus = false;
                if(childList.size()>0){
                    nav.setNodes(childList);//给根节点设置子节点
                    for (Menu tem:childList
                    ) {
                        Map<String,Object> temMap = tem.getState();
                        if(temMap!=null){
                            boolean childChecked = (boolean)temMap.get("checked");
                            if(childChecked){
                                checkStatus = true;
                                break;
                            }
                        }

                    }
                }

                if(checkStatus){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("checked", true);
                    nav.setState(map);
                }
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            data.put("success", "true");
            data.put("list", rootMenu);
            return rootMenu;
        } catch (Exception e) {
            return new ArrayList();
        }
    }


    public static List<Menudepartment> findTree1(List<Menudepartment> allMenu,List<String> checkmenuid){
        if(checkmenuid.size()==0){checkmenuid=null;}
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询所有菜单
            //根节点
            List<Menudepartment> rootMenu = new ArrayList<Menudepartment>();
            for (Menudepartment nav : allMenu) {
                if(nav.getDepartmentFather()==0){//父节点是0的，为根节点。
                    /* nav.*/
                    rootMenu.add(nav);
                }
            }

            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, order1());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menudepartment nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menudepartment> childList = getChild1(String.valueOf(nav.getId()), allMenu,checkmenuid);
                nav.setNodes(childList);//给根节点设置子节点
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            data.put("success", "true");
            data.put("list", rootMenu);
            return rootMenu;
        } catch (Exception e) {
            return new ArrayList();
        }
    }


    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public static List<Menudepartment> getChild1(String id,List<Menudepartment> allMenu,List<String> checkmenuid){
        //子菜单
        List<Menudepartment> childList = new ArrayList<Menudepartment>();
        for (Menudepartment nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getDepartmentFather().equals(Integer.valueOf(id))){
                if(checkmenuid!=null && checkmenuid.size()>0 && checkmenuid.contains(String.valueOf(nav.getId()))){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("checked", true);
                    nav.setState(map);
                }
                childList.add(nav);
            }
        }
        //递归
        for (Menudepartment nav : childList) {
            nav.setNodes(getChild1(String.valueOf(nav.getId()), allMenu,checkmenuid));
        }
        Collections.sort(childList,order1());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menudepartment>();
        }
        return childList;
    }

    public static List<Menu> getChild(String id,List<Menu> allMenu,List<Integer> checkmenuid){
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getMenuFather().equals(Integer.valueOf(id))){
                boolean checkStatus = false;
                if(checkmenuid!=null && checkmenuid.size()>0 && checkmenuid.contains(nav.getId())){
                    checkStatus= true;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("checked", checkStatus);
                nav.setState(map);
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setNodes(getChild(String.valueOf(nav.getId()), allMenu,checkmenuid));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }
}
