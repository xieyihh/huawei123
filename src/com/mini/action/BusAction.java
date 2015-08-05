package com.mini.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mini.entity.Bus;
import com.mini.service.BusService;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 处理班车信息相关请求的Action类
 * @author 雷晓冰	2014-11-19
 */
public class BusAction extends BaseAction implements ModelDriven<Bus> {
	private static final long serialVersionUID = -1338335330516378248L;
	private Bus bus = new Bus();
	private BusService service;
	private int pageNum;				//页号
	private int pageSize;				//数量
	
	public BusService getService() {
		return service;
	}
	public void setService(BusService service) {
		this.service = service;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	@Override
	public Bus getModel() {
		return this.bus;
	}
	/**
	 * 添加班车信息
	 */
	public void add() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.add(this.bus);
		if(flag) {
			msg = "班车添加成功";
		} else {
			msg = "班车添加失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.bus = new Bus();
		writeJson(map);
	}
	/**
	 * 修改版车信息
	 */
	public void update() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.update(this.bus);
		if(flag) {
			msg = "班车信息修改成功";
		} else {
			msg = "班车信息修改失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.bus = new Bus();
		writeJson(map);
	}
	/**
	 * 删除班车信息
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.delete(this.bus.getId());
		if(flag) {
			msg = "班车信息删除成功";
		} else {
			msg = "班车信息删除失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.bus = new Bus();
		writeJson(map);
	}
	/**
	 * 查找班车信息
	 */
	public void find() {
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<Bus> model = service.findByConditions(this.bus, pageNum, pageSize);
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.bus = new Bus();
		writeJson(map);
	}
	/**
	 * 导入班车信息
	 */
	public void importBus() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";		//定义返回给前台的消息
		boolean success = false;	//是否导入成功
		File excel = this.uploadExcelFile();
		if (excel != null) {
			// 解析Excel文件
			map = this.parseBusExcel(excel);
			excel.delete();
		} else {
			msg = "文件上传失败";
			success = false;
			map.put("msg", msg);
			map.put("success", success);
		}
		super.writeJson(map);
	}
	/**
	 * 解析包含班车信息的Excel文件
	 * @param excel
	 * @return
	 */
	private Map<String , Object> parseBusExcel(File excel) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";		//定义返回给前台的消息
		boolean success = false;	//是否导入成功
		List<Bus> list = new ArrayList<Bus>();
		try {
			Workbook workbook;
			try {		//如果是.xlsx格式的，用XSSFWorkbook
				workbook = new XSSFWorkbook(new FileInputStream(excel));
			} catch (Exception e) {		//如果是.xls格式的，用HSSFWorkbook
				workbook = new HSSFWorkbook(new FileInputStream(excel));
			}
			Sheet sheet = workbook.getSheetAt(0);
			//从第二行开始（第一行是标题），遍历每一行获取每一个博士信息
			int rowNum = 1;
			for(rowNum=1; rowNum<=sheet.getLastRowNum(); rowNum++) {
				try {
					Row row = sheet.getRow(rowNum);
					//获取班车属性
					String type = row.getCell(0) != null ? row.getCell(0).toString() : "";	//班车类别
					String number = row.getCell(1) != null ? row.getCell(1).toString().replaceAll("\\.\\d*", "") : "";		//班车编号
					String time = row.getCell(2) != null ? row.getCell(2).toString().replaceAll("\\.\\d*", "") : "";	//发车时间
					String start = row.getCell(3) != null ? row.getCell(3).toString() : "";	//起点
					String end = row.getCell(4) != null ? row.getCell(4).toString() : "";	//终点
					String line = row.getCell(5) != null ? row.getCell(5).toString() : "";	//班车线路
					String price = row.getCell(6) != null ? row.getCell(6).toString().replaceAll("\\.\\d*", "") : "";	//票价
					String vehicleType = row.getCell(7) != null ? row.getCell(7).toString().replaceAll("\\.\\d*", "") : "";		//车型
					String plateNumber = row.getCell(8) != null ? row.getCell(8).toString().replaceAll("\\.\\d*", "") : "";	//车牌号
					String driver = row.getCell(9) != null ? row.getCell(9).toString() : "";	//司机
					String remark = row.getCell(10) != null ? row.getCell(10).toString() : "";		//备注
					Bus bus = new Bus();
					bus.setType(type);
					bus.setNumber(number);
					bus.setTime(time);
					bus.setStart(start);
					bus.setEnd(end);
					bus.setLine(line);
					bus.setPrice(price);
					bus.setVehicleType(vehicleType);
					bus.setPlateNumber(plateNumber);
					bus.setDriver(driver);
					bus.setRemark(remark);
					//将新建的bus对象添加到集合中
					list.add(bus);
				} catch (Exception e) {
//					System.out.println("rowNum : " + rowNum);
					break;
				}
			}
			if(rowNum <= sheet.getLastRowNum()) {		//如果i小于或等于最后一行的编号（从0开始），说明中间报错了
				success = false;
				msg = "Excel结构有误，请检查后重新导入";
				map.put("msg", msg);
				map.put("success", success);
			} else {		//数据解析没有出错，批量持久化数据
				boolean flag = service.add(list);
				if(flag) {
					msg = "成功的导入了" + sheet.getLastRowNum() + "条班车信息"; 
				} else {
					msg = "数据导入失败，请稍后重试";
				}
				success = true;
				map.put("msg", msg);
				map.put("success", success);
			}
			list.clear();
		} catch (Exception e) {
			
		}
		return map;
	}
	
}
