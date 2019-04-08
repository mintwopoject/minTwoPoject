package com.qujie.mintwo.excel;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qujie.mintwo.system.generalMethod.GeneralMethod;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.system.role.service.ITbRoleService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ustils/Excel")
public class Excel {

    @Autowired
    private ITbRoleService roleService;

    @RequestMapping("/import")
    @ResponseBody
    public boolean exImport(@RequestParam(value = "filename") MultipartFile file) {
        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = roleService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a==false){
            System.out.println("操作失败");
        }
        return a;
    }




    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {

        String[] arr = GeneralMethod.arr;
        List<TbRole> roleList=null;

        if (arr==null){
            roleList = roleService.selectList(new EntityWrapper<TbRole>());

        }else {
            for (int i = 0; i < arr.length; i++) {
                TbRole tbRole = roleService.selectOne(new EntityWrapper<TbRole>().eq("Id", arr[i]));
                roleList.add(tbRole);
            }
        }

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("角色信息表");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (26.25 * 20));
        row.createCell(0).setCellValue("角色信息表");//为第一行单元格设值

        /*为标题设计空间
         * firstRow从第1行开始
         * lastRow从第0行结束
         *
         *从第1个单元格开始
         * 从第3个单元格结束
         */
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);

		/*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
		sheet.addMergedRegion(columnRegion);*/


        /*
         * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
         * 第一个table_name 表名字
         * 第二个table_name 数据库名称
         * */
        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("角色名称");//为第一个单元格设值
        row.createCell(1).setCellValue("权限");//为第二个单元格设值
        row.createCell(2).setCellValue("创建人");//为第三个单元格设值
        row.createCell(3).setCellValue("创建时间");//为第三个单元格设值
        row.createCell(4).setCellValue("修改人");//为第三个单元格设值
        row.createCell(5).setCellValue("修改时间");//为第三个单元格设值

        for (int i = 0; i < roleList.size(); i++) {
            row = sheet.createRow(i + 2);
            TbRole tbRole = roleList.get(i);
            row.createCell(0).setCellValue(tbRole.getRoleName());
            row.createCell(1).setCellValue(tbRole.getDescription());
            row.createCell(2).setCellValue(tbRole.getCreateBy());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String CreateTime=sdf.format(tbRole.getCreateTime());
            row.createCell(3).setCellValue(CreateTime);
            row.createCell(4).setCellValue(tbRole.getUpdateBy());
            String UpdateTime=sdf.format(tbRole.getUpdateTime());
//            if (UpdateTime!=null){
                row.createCell(5).setCellValue(UpdateTime);
//            }

        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=role.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();


    }

}
