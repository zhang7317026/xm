package com.zrz.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zrz.service.fund.impl.ExcelPO;
 
 
/**
 * 导出Excel
 *
 * @param <T>
 */
public class ExportExcelUtil<T>{
	
	// 2007 版本以上 最大支持1048576行
	public  final static String  EXCEl_FILE_2007 = "2007";
	// 2003 版本 最大支持65536 行
	public  final static String  EXCEL_FILE_2003 = "2003";
	
	
	public static void main(String[] args) {

		List<Map<String,Object>> excelList = new ArrayList<Map<String,Object>>();
		for(int s=1;s<=5;s++){
			Map<String,Object> sheetMap = new HashMap<String,Object>(); 
			
			String sheetName = "sheet"+s;
			String[] sheetHeaders = { "date0", "now_all", "input_all" };
			List<ExcelPO> sheetList = new ArrayList<ExcelPO>();
			for (int i = 1; i < 10; i++) {
				sheetList.add(new ExcelPO("2019-01-0"+i, s*i*i*1.1 , s*i*3.7));
			}
			
			sheetMap.put("sheetName", sheetName);
			sheetMap.put("sheetHeaders", sheetHeaders);
			sheetMap.put("sheetList", sheetList);
			excelList.add(sheetMap);
		}
		
		
		

		ExportExcelUtil<ExcelPO> util = new ExportExcelUtil<ExcelPO>();
		util.exportExcel(excelList, "D:/test.xlsx");

	}
	
	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * @param List<Map<String,Object>>
	 * @param 包含String sheetName
	 * @param 包含String[] sheetHeaders
	 * @param 包含List<ExcelPO> sheetList
	 */
	public void exportExcel(List<Map<String,Object>> excelList, String url){
		OutputStream out = null;
		try{
			out = new FileOutputStream(url);
			String version = "";
			if(url.endsWith(".xlsx")){
				version = EXCEl_FILE_2007;
			}else{
				version = EXCEL_FILE_2003;
			}
			
			if(StringUtils.isEmpty(version) || EXCEL_FILE_2003.equals(version.trim())){
				//声明一个2003工作薄
				HSSFWorkbook workbook2003 = new HSSFWorkbook();
				for(Map<String,Object> sheetMap : excelList){
					String sheetName = (String) sheetMap.get("sheetName");
					String[] sheetHeaders = (String[]) sheetMap.get("sheetHeaders");
					Collection<T> sheetList = (Collection<T>) sheetMap.get("sheetList");
					
					exportExcel2003(
							sheetName, sheetHeaders, sheetList, out, "yyyy-MM-dd HH:mm:ss", workbook2003);
				}
				try {
					workbook2003.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				//声明一个2007工作薄
				XSSFWorkbook workbook2007 = new XSSFWorkbook();
				for(Map<String,Object> sheetMap : excelList){
					String sheetName = (String) sheetMap.get("sheetName");
					String[] sheetHeaders = (String[]) sheetMap.get("sheetHeaders");
					Collection<T> sheetList = (Collection<T>) sheetMap.get("sheetList");
					
					exportExcel2007(
							sheetName, sheetHeaders, sheetList, out, "yyyy-MM-dd HH:mm:ss", workbook2007);
				}
				try {
					workbook2007.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	/**
	 * <p>
	 * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
	 * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
	 * </p>
	 * 
	 * @param sheetName
	 *            表格标题名
	 * @param sheetHeaders
	 *            表格头部标题集合
	 * @param sheetList
	 *            需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
	 *            JavaBean属性的数据类型有基本数据类型及String,Date
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportExcel2007(
			String sheetName, 
			String[] sheetHeaders, 
			Collection<T> sheetList, 
			OutputStream out, 
			String pattern,
			XSSFWorkbook workbook) {
		
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(new XSSFColor(java.awt.Color.gray));
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体"); 
		font.setColor(new XSSFColor(java.awt.Color.BLACK));
		font.setFontHeightInPoints((short) 11);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(new XSSFColor(java.awt.Color.WHITE));
		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
 
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cellHeader;
		for (int i = 0; i < sheetHeaders.length; i++) {
			cellHeader = row.createCell(i);
			cellHeader.setCellStyle(style);
			cellHeader.setCellValue(new XSSFRichTextString(sheetHeaders[i]));
		}
 
		// 遍历集合数据，产生数据行
		Iterator<T> it = sheetList.iterator();
		int index = 0;
		T t;
		Field[] fields;
		Field field;
		XSSFRichTextString richString;
		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		Matcher matcher;
		String fieldName;
		String getMethodName;
		XSSFCell cell;
		Class tCls;
		Method getMethod;
		Object value;
		String textValue;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			t = (T) it.next();
			// 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
			fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style2);
				field = fields[i];
				fieldName = field.getName();
				getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					tCls = t.getClass();
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					}else if(value instanceof Float) {
						cell.setCellValue((Double) value);
					}else if(value instanceof Double) {
						cell.setCellValue((Double) value);
					}else if(value instanceof Long) {
						cell.setCellValue((Long) value);
					}else if(value instanceof Boolean) {
						textValue = "是";
						if (!(Boolean) value) {
							textValue = "否";
						}
					}else if(value instanceof Date) {
						textValue = sdf.format((Date) value);
					}else{
						// 其它数据类型都当作字符串简单处理
						if (value != null) {
							textValue = value.toString();
						}
					}
					if (textValue != null) {
						matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							richString = new XSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
	}
	
	
	
	/**
	 * <p>
	 * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
	 * 此方法生成2003版本的excel,文件名后缀：xls <br>
	 * </p>
	 * 
	 * @param sheetName
	 *            表格标题名
	 * @param sheetHeaders
	 *            表格头部标题集合
	 * @param sheetList
	 *            需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
	 *            JavaBean属性的数据类型有基本数据类型及String,Date
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportExcel2003(
			String sheetName, 
			String[] sheetHeaders, 
			Collection<T> sheetList, 
			OutputStream out, 
			String pattern,
			HSSFWorkbook workbook) {
		
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体"); 
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 11);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
 
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellHeader;
		for (int i = 0; i < sheetHeaders.length; i++) {
			cellHeader = row.createCell(i);
			cellHeader.setCellStyle(style);
			cellHeader.setCellValue(new HSSFRichTextString(sheetHeaders[i]));
		}
 
		// 遍历集合数据，产生数据行
		Iterator<T> it = sheetList.iterator();
		int index = 0;
		T t;
		Field[] fields;
		Field field;
		HSSFRichTextString richString;
		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		Matcher matcher;
		String fieldName;
		String getMethodName;
		HSSFCell cell;
		Class tCls;
		Method getMethod;
		Object value;
		String textValue;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			t = (T) it.next();
			// 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
			fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style2);
				field = fields[i];
				fieldName = field.getName();
				getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					tCls = t.getClass();
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					}else if(value instanceof Float) {
						cell.setCellValue((Double) value);
					}else if(value instanceof Double) {
						cell.setCellValue((Double) value);
					}else if(value instanceof Long) {
						cell.setCellValue((Long) value);
					}else if(value instanceof Boolean) {
						textValue = "是";
						if (!(Boolean) value) {
							textValue = "否";
						}
					}else if(value instanceof Date) {
						textValue = sdf.format((Date) value);
					}else{
						// 其它数据类型都当作字符串简单处理
						if (value != null) {
							textValue = value.toString();
						}
					}
					if (textValue != null) {
						matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

