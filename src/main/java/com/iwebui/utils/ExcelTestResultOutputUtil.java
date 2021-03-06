package com.iwebui.utils;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.iwebui.dto.LoginCaseDto;
import com.iwebui.dto.LoginUrlDto;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelTestResultOutputUtil {
    /**
     * 功能描述：把同一个表格多个sheet测试结果重新输出，如果后续增加多个List<Map<String, Object>>对象，需要后面继续追加
     * @ExcelEntiry sheet表格映射的实体对象
     * @return
     */
    public static String exportSheet( Object...objects){


        Workbook workBook = null;
        try {
            // 创建参数对象（用来设定excel得sheet得内容等信息）
            ExportParams deptExportParams = new ExportParams();
            // 设置sheet得名称
            deptExportParams.setSheetName("登录用例");
            // 设置sheet表头名称
            deptExportParams.setTitle("测试用例");
            // 创建sheet1使用得map
            Map<String, Object> deptExportMap = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            deptExportMap.put("title", deptExportParams);
            // 模版导出对应得实体类型
            deptExportMap.put("entity", LoginCaseDto.class);
            // sheet中要填充得数据
            deptExportMap.put("data", objects[0]);
            ExportParams empExportParams = new ExportParams();
            empExportParams.setTitle("被测RUL路径");
            empExportParams.setSheetName("被测url");
            // 创建sheet2使用得map
            Map<String, Object> empExportMap = new HashMap<>();
            empExportMap.put("title", empExportParams);
            empExportMap.put("entity", LoginUrlDto.class);
            empExportMap.put("data", objects[1]);
            // 将sheet1、sheet2使用得map进行包装
            List<Map<String, Object>> sheetsList = new ArrayList<>();
            sheetsList.add(deptExportMap);
            sheetsList.add(empExportMap);
            // 执行方法
            workBook = EasyPoiUtil.exportExcel(sheetsList, ExcelType.HSSF);
            //String fileName = URLEncoder.encode("test", "UTF-8");
            String filepath = (String) LoadStaticConfigUtil.getCommonYml( "testcaseexcel.cases");
            FileOutputStream fos = new FileOutputStream(filepath);
            workBook.write(fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workBook != null) {
                try {
                    workBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

}
