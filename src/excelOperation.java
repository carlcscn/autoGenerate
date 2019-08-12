import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Created by liu_changshi on 2019/6/10.
 */
public class excelOperation {

    public static void setExcel(String pathName,int rowNum,int columnNum,String content) throws IOException {
        FileInputStream fs=new FileInputStream(pathName);
        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
        HSSFWorkbook wb=new HSSFWorkbook(ps);
        HSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表

        FileOutputStream out=new FileOutputStream(pathName);  //向d://buildResult.xls中写数据
        HSSFRow row=sheet.getRow(rowNum); //在现有行号后追加数据
        row.getCell(columnNum).setCellValue(content); //设置第一个（从0开始）单元格的数据
        out.flush();
        wb.write(out);
        out.close();


    }

    public static String getExcel(String pathName,int rowNum,int columnNum) throws IOException {
        FileInputStream fs=new FileInputStream(pathName);
        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
        HSSFWorkbook wb=new HSSFWorkbook(ps);
        HSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表

        HSSFRow row=sheet.getRow(rowNum);
        String a = row.getCell(columnNum).getStringCellValue();
        return a;
    }



}
