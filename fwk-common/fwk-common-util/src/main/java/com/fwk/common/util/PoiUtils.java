package com.fwk.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Apache POI 的工具类
 * 
 * @author wangjunwen
 * @date 2018年4月3日 下午3:37:40
 *
 */
public class PoiUtils {

	/**
	 * 去掉字符串两端的空格
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 下午2:36:32
	 *
	 * @param src
	 * @return
	 */
	public static final String trim(String src) {
		return (null == src ? null : src.trim());
	}

	/**
	 * 读取 Excel 中的数据
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 下午2:27:07
	 *
	 * @param excelFile
	 *            Excel 文件对象
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<Object>> readExcelData(File excelFile, int... args) throws Exception {

		if ((null == excelFile)) {
			return null;
		}

		final ArrayList<ArrayList<Object>> excelData = new ArrayList<ArrayList<Object>>();

		try (InputStream inputStream = new FileInputStream(excelFile);
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);) {

			XSSFRow xssfRow = null;
			XSSFCell cell = null;
			ArrayList<Object> rowDataList = null;

			// 遍历xlsx中的sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 对于每个sheet，读取其中的每一行
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					xssfRow = xssfSheet.getRow(rowNum);

					if (null == xssfRow) {
						continue;
					}

					rowDataList = new ArrayList<Object>();

					for (int columnNum = 0; columnNum < args.length; columnNum++) {
						cell = xssfRow.getCell(args[columnNum]);

						rowDataList.add(getCellValue(cell));
					}

					excelData.add(rowDataList);
				}
			}

		}

		return excelData;
	}

	/**
	 * 获取 Excel 单元格中的值
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 下午3:17:20
	 *
	 * @param cell
	 *            Cell对象
	 * @return
	 */
	private static Object getCellValue(Cell cell) {

		if (null == cell) {
			return null;
		}

		Object cellValue = null;

		CellType cellType = cell.getCellTypeEnum();

		switch (cellType) {
		case BOOLEAN: {
			cellValue = Boolean.valueOf(cell.getBooleanCellValue());
			break;
		}
		case NUMERIC: {
			cellValue = Double.valueOf(cell.getNumericCellValue());
			break;
		}
		case BLANK: {
			cellValue = null;
			break;
		}
		default: {
			cellValue = String.valueOf(cell.getStringCellValue());
			cellValue = ("null".equals(cellValue) ? null : cellValue.toString().trim());
			break;
		}
		}// end switch

		return cellValue;
	}

}
