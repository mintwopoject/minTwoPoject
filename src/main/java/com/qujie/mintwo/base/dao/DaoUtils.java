package com.qujie.mintwo.base.dao;

import com.qujie.mintwo.ustils.BetweenUtils;
import com.qujie.mintwo.ustils.CheckUtils;
import com.qujie.mintwo.ustils.MapToBean;
import com.qujie.mintwo.ustils.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DaoUtils<T> {

    @Autowired
    private BaseDao baseDao;

    public PageUtil findBySql(String sql,PageUtil PageUtils){
        return findBySql( sql, PageUtils,null);
    }

    public PageUtil findBySql(String sql, PageUtil PageUtils, String str){
        sql=sql(sql,str);
        if(PageUtils.getCurrPage()!=null && PageUtils.getPageSize()!=null){
            String s = select_count(sql);
            List<Map> bySql=null;
            if(s!=null){
                bySql = baseDao.findBySql(s);
            }else{
                bySql = baseDao.findBySql(sql);
            }

            PageUtils.setTotal(Integer.valueOf(bySql.get(0).get("listcount").toString()));
            PageUtils.setTotalPage((int)Math.ceil((double)PageUtils.getTotal()/PageUtils.getPageSize()));
        }
        sql+= BetweenUtils.getFENYE()+ " between "+((PageUtils.getCurrPage()-1)*PageUtils.getPageSize())+" and "+ PageUtils.getCurrPage()*PageUtils.getPageSize();
        PageUtils.setRows( baseDao.findBySql(sql));
        return PageUtils;
    }

    public List<Map> findBySql(String sql){
        List bySql = baseDao.findBySql(sql);
        return bySql;
    }

    private  String sql(String sql,String str){
        if(str==null || "".equals(str)){return sql;}
        String tempsql="";
        // 正则表达式规则
        String regEx = "order\\s*by";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        boolean rs = matcher.find();

        if(rs){
            tempsql=sql.substring(sql.indexOf(matcher.group()),sql.length() );
            sql=sql.substring(0, sql.indexOf(matcher.group()));
        }
        if(sql.toLowerCase().indexOf("where")==-1){
            sql+=" where 1=1 and";
        }else{
            sql+="  and ";
        }
        return sql+" "+str+" "+tempsql;
    }

    private  String select_count(String sql){
        int x=-99;
        x = sql.indexOf("from");
        if(x==-1){
            x = sql.indexOf("FROM");
        }
        if(x==-1||x==-99){return null;}
        String t=sql.substring(x,sql.length());
        return "select count(*)as listcount "+t;
    }

    //sql转bean
    public T findBySqlToBean(String sql ,Class clazz){
        List<Map> bySql = baseDao.findBySql(sql);
        if(CheckUtils.isNotNull(bySql)){
            try {
                return (T) MapToBean.transMapToBean(bySql.get(0),clazz);
//                return  (T)MapAndObject.mapToObject(bySql.get(0), clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
