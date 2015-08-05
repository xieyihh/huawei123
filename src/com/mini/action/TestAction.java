package com.mini.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.mini.form.TestForm;
import com.opensymphony.xwork2.ModelDriven;

public class TestAction extends BaseAction implements ModelDriven<TestForm>{

	private TestForm testform=new TestForm();
	@Override
	public TestForm getModel() {
		// TODO Auto-generated method stub
		return testform;
	}
	public String list() throws Exception {
		System.out.println(testform.getTextDate());
		File file=testform.getTextDate();//得到文件
		InputStream fis = new FileInputStream(file);
		POIFSFileSystem fs= new POIFSFileSystem(fis);
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);//得到表一
		HSSFRow row = sheet.getRow(0);//得到第一行
		// 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        int rowNum=sheet.getLastRowNum();
        for(int i=1;i<rowNum;i++){
        	
        }
        for(int i=0;i<colNum;i++){
        	System.out.println(row.getCell(i));
        }
        PrintWriter out =this.getResponse().getWriter();
        out.print("Hello java, 这里是struts2, 在action方法中直接输出文本信息.");  
        out.print("Time: " + (new Date()).getTime());   
        out.flush();  
        out.close();
        return "";
//		return "success";
	}

}
