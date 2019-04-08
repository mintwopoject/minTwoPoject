package com.qujie.mintwo.system.role.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qujie.mintwo.base.dao.DaoUtils;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.mapper.TbRoleMapper;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import com.qujie.mintwo.system.roleMenu.entity.TbRoleMenu;
import com.qujie.mintwo.system.roleMenu.service.ITbRoleMenuService;
import com.qujie.mintwo.ustils.BetweenUtils;
import com.qujie.mintwo.ustils.MyException;
import com.qujie.mintwo.ustils.PageUtil;
import com.qujie.mintwo.ustils.PageUtilsFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements ITbRoleService {

    @Autowired
    private ITbRoleService roleService;

    @Autowired
    private DaoUtils<TbRole> daoUtils;
    @Autowired
    private ITbRoleMenuService tbRoleMenuService;

    @Override
    public PageUtil roleList(Map<String, Object> params) {
        String sql ="select * from ( \n" +
                "select *, ROW_NUMBER() OVER(Order by id) AS RowId from tbRole \n" +
                ") as b where 1=1 ";
        BetweenUtils.setFENYE(" and RowId ");
        return daoUtils.findBySql(sql, PageUtilsFactory.getInstance(params));

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateByIds(TbRole role) {
        String menuids = role.getMenuids();
        String[] split=null;
        if (menuids!=null&&menuids!=""){
            split = menuids.split(",");
        }
        role.setUpdateTime(new Date());
        List<TbRoleMenu> tbRoleMenus = tbRoleMenuService.selectList(new EntityWrapper<TbRoleMenu>().eq("RoleId", role.getId()));
        boolean b = roleService.updateById(role);
        boolean b1=false;
        if (tbRoleMenus.size()>0){
            for (int i = 0; i < tbRoleMenus.size(); i++) {
                b1 =  tbRoleMenuService.deleteById(tbRoleMenus.get(i));
            }
        }
        for (int i = 0; i <split.length ; i++) {
            TbRoleMenu tbRoleMenu = new TbRoleMenu();
            tbRoleMenu.setRoleId(role.getId());
            tbRoleMenu.setMenuId(Integer.valueOf(split[i]));
            tbRoleMenuService.insert(tbRoleMenu);
        }

        if (b==true&&b1==true){
            return true;
        }else {
            return false;
        }


    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saves(TbRole role) {
        String menuids = role.getMenuids();
        String[] split=null;
        if (menuids!=null&&menuids!=""){
            split = menuids.split(",");
        }
        role.setCreateTime(new Date());
        boolean b = roleService.insert(role);

        for (int i = 0; i <split.length ; i++) {
            TbRoleMenu tbRoleMenu = new TbRoleMenu();
            tbRoleMenu.setRoleId(role.getId());
            tbRoleMenu.setMenuId(Integer.valueOf(split[i]));
            tbRoleMenuService.insert(tbRoleMenu);
        }

        if (b==true){
            return true;
        }else {
            return false;
        }


    }


    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<TbRole> roleList=new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        TbRole tbRole;
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null) {
                continue;
            }


            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException

            tbRole = new TbRole();

            if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
                throw new MyException("导入失败(第"+(r+1)+"行,角色名请设为文本格式)");
            }

            String RoleName = row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            if(RoleName == null || RoleName.isEmpty()){//判断是否为空
                throw new MyException("导入失败(第"+(r+1)+"行,角色名未填写)");
            }


            row.getCell(1).setCellType(1);//得到每一行的 第二个单元格的值
            String Description = row.getCell(1).getStringCellValue();
            if(Description==null || Description.isEmpty()){
                throw new MyException("导入失败(第"+(r+1)+"行,权限未填写)");
            }

            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第3个单元格的值
            String CreateBy = row.getCell(2).getStringCellValue();
            if(CreateBy==null || CreateBy.isEmpty()){
                throw new MyException("导入失败(第"+(r+1)+"行,创建人未填写)");
            }


            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第4个单元格的值
            String CreateTime = row.getCell(3).getStringCellValue();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date cedate=sdf.parse(CreateTime);
            if(cedate==null){
                throw new MyException("导入失败(第"+(r+1)+"行,创建时间未填写)");
            }


            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第3个单元格的值
            String UpdateBy = row.getCell(4).getStringCellValue();
            if(UpdateBy==null || UpdateBy.isEmpty()){
                throw new MyException("导入失败(第"+(r+1)+"行,修改人未填写)");
            }



            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第4个单元格的值
            String UpdateTime = row.getCell(5).getStringCellValue();
            Date update=sdf.parse(UpdateTime);
            if(update==null){
                throw new MyException("导入失败(第"+(r+1)+"行,修改时间未填写)");
            }


            tbRole.setRoleName(RoleName);
            tbRole.setDescription(Description);
            tbRole.setCreateBy(CreateBy);
            tbRole.setCreateTime(cedate);
            tbRole.setUpdateBy(UpdateBy);
            tbRole.setUpdateTime(update);

            roleList.add(tbRole);
        }
        for (TbRole tbRole1 :roleList) {

            roleService.insert(tbRole1);
            System.out.println(" 插入 "+tbRole1);
        }

        return notNull;
    }


}
