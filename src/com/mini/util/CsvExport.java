/**
 * 系统数据导出Excel 生成器
 * @version 1.0
 */
package com.mini.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class CsvExport {

	private final int SPLIT_COUNT = 50000; //Excel每个工作簿的行数

	private String fieldName = null; //excel标题数据集

	private ArrayList<String> fieldData = null; //excel数据内容	

	private HSSFWorkbook workBook = null;
	private OutputStream csvfile=null; 
	/**
	 * 构造器
	 * @param fieldName 结果集的字段名
	 * @param data
	 */
	public CsvExport(String fieldName, ArrayList<String> fieldData) {

		this.fieldName = fieldName;
		this.fieldData = fieldData;
	}

	/**
	 * 创建csv文件
	 * @return HSSFWorkbook
	 */

	public boolean  createCsv(OutputStream os) {
		//得到行数
		boolean isSucess=false;
		int rows =fieldData.size();
		OutputStreamWriter osw=null;
	    BufferedWriter bw=null;
		try {	      
	        osw = new OutputStreamWriter(os);
	        
	        bw =new BufferedWriter(osw);
	        //首先生成头文件	        	               
			bw.append(fieldName).append("\r");       
	        //再补充数据
	        if(fieldData!=null && !fieldData.isEmpty()){
	        	for(String data : fieldData){	               
						bw.append(data).append("\r");					
	            }	
	        }
	        isSucess=true;
		} catch (IOException e) {
				// TODO Auto-generated catch block
			isSucess=false;
		}finally{
			if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            

		}
		return isSucess;			
		
	}

	

}
