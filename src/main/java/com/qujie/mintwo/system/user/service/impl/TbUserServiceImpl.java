package com.qujie.mintwo.system.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.base.dao.DaoUtils;
import com.qujie.mintwo.system.menu.entity.Menu;
import com.qujie.mintwo.system.menu.entity.TbMenu;
import com.qujie.mintwo.system.menu.mapper.TbMenuMapper;
import com.qujie.mintwo.system.menu.service.ITbMenuService;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.user.entity.TbUser;
import com.qujie.mintwo.system.user.mapper.TbUserMapper;
import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.system.userRole.entity.TbUserRole;
import com.qujie.mintwo.system.userRole.service.ITbUserRoleService;
import com.qujie.mintwo.ustils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {
    @Autowired
    private DaoUtils<TbUser> daoUtils;
    
    @Autowired
    private ITbUserService tbUserService;
    @Autowired
    private ITbUserRoleService tbUserRoleService;
    //列表
    @Override
    public PageUtil userList(Map<String, Object> params) {
        Object accountName = params.get("accountName_search");
        Object realName = params.get("realName_search");
        String sql ="select * from (select *, ROW_NUMBER() OVER(Order by id) AS RowId from tbUser ";
        if (CheckUtils.isNotNull(accountName)){
            sql+=" where AccountName like '%"+accountName+"%'";
        }
        if (CheckUtils.isNotNull(realName)){
            sql += " and RealName like '%"+realName+"%'";
        }
        sql+=") as b where 1=1 ";
        BetweenUtils.setFENYE(" and RowId ");
        return daoUtils.findBySql(sql, PageUtilsFactory.getInstance(params));
    }
    //修改
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(String request,String USERNAME) {
        JSONObject Json = JSONObject.fromObject(request);
        TbUser tbUser = new TbUser();
        String id = Json.get("id").toString();
        tbUser.setId(id!=null?Integer.valueOf(id):null);
        tbUser.setAccountName(Json.get("accountName")!=null?Json.get("accountName").toString():"");
        tbUser.setPassword(MD5Utils.getMD5Code( Json.get("password")!=null?Json.get("password").toString():""));
        tbUser.setEmail(Json.get("email")!=null?Json.get("email").toString():"");
        tbUser.setRealName(Json.get("realName")!=null?Json.get("realName").toString():"");
        tbUser.setMobilePhone(Json.get("mobilePhone")!=null?Json.get("mobilePhone").toString():"");
        tbUser.setUpdateBy(USERNAME);
        tbUser.setDescription(Json.get("description")!=null?Json.get("description").toString():"");
        tbUser.setIsAble( (Boolean) Json.get("isAble"));

        tbUser.setUpdateTime(new Date());
        boolean b = tbUserService.updateById(tbUser);

        List<TbUserRole> list1 = tbUserRoleService.selectList(new EntityWrapper<TbUserRole>().eq("UserId", tbUser.getId()));
        for (int i = 0; i < list1.size(); i++) {
            tbUserRoleService.delete(new EntityWrapper<TbUserRole>().eq("UserId",list1.get(i).getUserId()));

        }
        Object temObject = Json.get("roleids");
        List<TbUserRole> list = new ArrayList<>();
        if(temObject instanceof JSONArray){
            JSONArray array = (JSONArray)temObject;
            for (int i = 0; i <array.size() ; i++) {
                TbUserRole tbUserRole = new TbUserRole();
                tbUserRole.setUserId(tbUser.getId());
                tbUserRole.setRoleId(Integer.valueOf(array.get(i).toString()));
                list.add(tbUserRole);
            }
            boolean b2 = tbUserRoleService.insertBatch(list);
            return true;
        }else {
            TbUserRole tbUserRole1 = new TbUserRole();
            String tem = temObject.toString();
            tbUserRole1.setUserId(tbUser.getId());
            tbUserRole1.setRoleId(Integer.valueOf(tem));
            boolean insert = tbUserRoleService.insert(tbUserRole1);
            return true;
        }


    }

    //新增
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(String request,String USERNAME) {
        JSONObject Json = JSONObject.fromObject(request);
        TbUser tbUser = new TbUser();
        tbUser.setAccountName(Json.get("accountName")!=null?Json.get("accountName").toString():"");
        tbUser.setPassword(MD5Utils.getMD5Code( Json.get("password")!=null?Json.get("password").toString():""));
        tbUser.setEmail(Json.get("email")!=null?Json.get("email").toString():"");
        tbUser.setRealName(Json.get("realName")!=null?Json.get("realName").toString():"");
        tbUser.setCreateBy(USERNAME);
        tbUser.setDescription(Json.get("description")!=null?Json.get("description").toString():"");
        tbUser.setMobilePhone(Json.get("mobilePhone")!=null?Json.get("mobilePhone").toString():"");
        tbUser.setCreateTime(new Date());
        tbUser.setIsAble( (Boolean) Json.get("isAble"));
        boolean b = tbUserService.insert(tbUser);

        Object temObject = Json.get("roleids");
        List<TbUserRole> list = new ArrayList<>();
        if(temObject instanceof JSONArray){
            JSONArray array = (JSONArray)temObject;
            for (int i = 0; i <array.size() ; i++) {
                TbUserRole tbUserRole = new TbUserRole();
                tbUserRole.setUserId(tbUser.getId());
                tbUserRole.setRoleId(Integer.valueOf(array.get(i).toString()));
                list.add(tbUserRole);
            }
            boolean b2 = tbUserRoleService.insertBatch(list);
            return true;
        }else{
            TbUserRole tbUserRole1 = new TbUserRole();
            String tem = temObject.toString();
            tbUserRole1.setUserId(tbUser.getId());
            tbUserRole1.setRoleId(Integer.valueOf(tem));
            boolean insert = tbUserRoleService.insert(tbUserRole1);
            return true;
        }

    }


    @Override
    public R getUserRole(Integer userId) {
        List<TbUserRole> list1 = tbUserRoleService.selectList(new EntityWrapper<TbUserRole>().eq("UserId", userId));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            list.add(list1.get(i).getRoleId());
        }
        Map<String,Object> map = new HashMap();
        map.put("roleIds",list);
        return R.ok(map);
    }
    //用户名唯一校验
    @Override
    public boolean checkName(Object name, Object id) {
        String sql = "select count(*) count from tbUser where AccountName = ";
        if(CheckUtils.AllObjNotNull(name)){
            sql +="'"+name.toString()+"'" +
                    "" +
                    "  " ;
            if(CheckUtils.isNotNull(id)){
                sql +=" and ID != '" +id.toString() +"' " ;
            }
            List<Map> bySql = daoUtils.findBySql(sql);
            return (Integer)(bySql.get(0).get("count")) <= 0;
        }else{
            return false;
        }
    }

    /**
     * 多表查询示例
     *     @Override
     *     public TbUser selectById_(Integer id) {
     *         String sql ="select \n" +
     *                 "a.*,a.id contract_id,b.scan_copy_of_contract_accessories_uuid\n" +
     *                 "from tb_contract as a\n" +
     *                 "left join tb_contract_sign_the_register as b on a.id=b.contract_id\n" +
     *                 "where a.del_flag=0 and a.id="+id+"";
     *         return daoUtils.findBySqlToBean(sql,TbUser.class);
     *     }
     */


}
