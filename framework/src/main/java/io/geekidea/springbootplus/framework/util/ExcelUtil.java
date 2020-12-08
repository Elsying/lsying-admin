package io.geekidea.springbootplus.framework.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.List;

public class ExcelUtil {

    /**
     * 在指定位置生成excel文件
     *
     * @param fileName 文件路径
     * @param clazz    导出数据类型
     * @param list     导出数据
     * @param <T>      可以不加这个也行，但是会有警告，看起来不舒服
     */
    public static <T> void writeToExcel(String fileName, Class<T> clazz, List<T> list) {
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        WriteSheet sheet = EasyExcel.writerSheet().head(clazz).build();
        excelWriter.write(list, sheet);
        excelWriter.finish();
    }
}
