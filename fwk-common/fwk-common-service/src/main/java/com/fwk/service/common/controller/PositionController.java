package com.fwk.service.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.service.PositionService;

@Controller
@RequestMapping("/position")
public class PositionController {
	
	@Autowired
	private PositionService positionService;

	/**
	 * 通过父级编码查询子集数据
	 * */
	@RequestMapping("/queryPositionByParentCode")
	public void queryPositionByParentCode(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			if(ObjectUtil.isNotBlank(requestMap.get("parentCode"))){
				if("0".equals(ObjectUtil.toString(requestMap.get("parentCode")))){
					requestMap.put("parentCode", "100000000000100000000000");
				}
				List<PositionEXT> positionEXTs = getPositionEXTs(requestMap);
				resultWrap.setData(positionEXTs);
			}else{
				resultWrap.setParamIsnotEmpty("{parentCode}");
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 通过code查询行政区划详情
	 * */
	@RequestMapping("/queryPositionDetailByCode")
	public void queryPositionDetailByCode(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			if(ObjectUtil.isNotBlank(requestMap.get("code"))){
				PositionEXT positionEXT = getPositionDetail(requestMap);
				resultWrap.setData(positionEXT);
			}else{
				resultWrap.setParamIsnotEmpty("{code}");
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 查询行政区划详情
	 * @param map
	 * map内必须存在code参数
	 * */
	public PositionEXT getPositionDetail(Map<String, Object> requestMap){
		if(ObjectUtil.isNotBlank(requestMap.get("code"))){
			return positionService.getPositionDetail(requestMap);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询行政区划列表
	 * @param map
	 * map内必须存在parentCode参数
	 * */
	public List<PositionEXT> getPositionEXTs(Map<String, Object> requestMap){
		if(ObjectUtil.isNotBlank(requestMap.get("parentCode"))){
			return positionService.queryPositionByParentCode(requestMap);
		}else{
			return null;
		}
	}
	
	/**
	 * 执行excel文件添加行政区划数据
	 * */
//	@RequestMapping("/makePosition")
//	public void makePosition(HttpServletRequest request,HttpServletResponse response){
//		
//		List<String> strList = getMapFile();
//		System.out.println(JSONObject.toJSON(strList));
//		for (int i = 0; i < strList.size(); i++) {
//			dusave(strList.get(i));
//		}
//	}
//	
//	
//	
//	public void dusave(String filename){
//		InputStream inputStream = PositionController.class.getResourceAsStream("/"+filename);
//		try {
//			int xx= 1;
//			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
//			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
//	            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
//	            if (xssfSheet == null) {
//	                continue;
//	            }
//	            // 获取当前工作薄的每一行
//	            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
//	                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
//	                if (xssfRow != null) {
//	                	//读取第一列数据
//	                    XSSFCell yi = xssfRow.getCell(0);
//	                    //读取第二列数据
//	                    XSSFCell er = xssfRow.getCell(1);
//	                    //读取第三列数据
//	                    XSSFCell san = xssfRow.getCell(2);
//	                    
//	                  //读取第一列数据
//	                    XSSFCell si = xssfRow.getCell(3);
//	                    //读取第二列数据
//	                    XSSFCell wu = xssfRow.getCell(4);
//	                    //读取第三列数据
//	                    XSSFCell liu = xssfRow.getCell(5);
//	                    
//	                  //读取第一列数据
//	                    XSSFCell qi = xssfRow.getCell(6);
//	                    //读取第二列数据
//	                    XSSFCell ba = xssfRow.getCell(7);
//	                    //读取第三列数据
//	                    XSSFCell jiu = xssfRow.getCell(8);
//	                    
//	                  //读取第一列数据
//	                    XSSFCell shi = xssfRow.getCell(9);
//	                    //读取第二列数据
//	                    XSSFCell shiyi = xssfRow.getCell(10);
//	                    //读取第三列数据
//	                    XSSFCell shier = xssfRow.getCell(11);
//	                    
//	                  //读取第一列数据
//	                    XSSFCell shisan = xssfRow.getCell(12);
//	                    //读取第二列数据
//	                    XSSFCell shisi = xssfRow.getCell(13);
//	                    //读取第三列数据
//	                    XSSFCell shiwu = xssfRow.getCell(14);
//	                    
//	                  //读取第一列数据
//	                    XSSFCell shiliu = xssfRow.getCell(15);
//	                    //读取第二列数据
//	                    XSSFCell shiqi = xssfRow.getCell(16);
//	                    //读取第三列数据
//	                    XSSFCell shiba = xssfRow.getCell(17);
//	                    
//	                    //需要转换数据的话直接调用getValue获取字符串
//	                    
//	                    Map<String,Object> saveMap = new HashMap<String,Object>();
//	                    saveMap.put("yi", yi.toString());
//	                    saveMap.put("er", er.toString());
//	                    saveMap.put("san", san.toString());
//	                    saveMap.put("si", si.toString());
//	                    saveMap.put("wu", wu.toString());
//	                    saveMap.put("liu",liu.toString() );
//	                    saveMap.put("qi", qi.toString());
//	                    saveMap.put("ba", ba.toString());
//	                    saveMap.put("jiu", jiu.toString());
//	                    saveMap.put("shi", shi.toString());
//	                    saveMap.put("shiyi", shiyi.toString());
//	                    saveMap.put("shier", shier.toString());
//	                    saveMap.put("shisan", shisan.toString());
//	                    saveMap.put("shisi", shisi.toString());
//	                    saveMap.put("shiwu", shiwu.toString());
//	                    saveMap.put("shiliu", shiliu.toString());
//	                    saveMap.put("shiqi", shiqi.toString());
//	                    saveMap.put("shiba", shiba.toString());
//	                    try {
//							int result = positionService.savePosition(saveMap);
//							if(result<=0){
//								System.out.println(filename+"失败/////////////////////////////////////////");
//								System.out.println(xx+":"+yi+":"+er+":"+san+":"+si+":"+wu+":"+liu+":"+qi+":"+ba+":"+jiu+":"+shi+":"+shiyi+":"+shier+":"+shisan+":"+shisi+":"+shiwu+":"+shiliu+":"+shiqi+":"+shiba);
//							}
//						} catch (Exception e) {
//							System.out.println(filename+"异常...........................................................");
//							System.out.println(xx+":"+yi+":"+er+":"+san+":"+si+":"+wu+":"+liu+":"+qi+":"+ba+":"+jiu+":"+shi+":"+shiyi+":"+shier+":"+shisan+":"+shisi+":"+shiwu+":"+shiliu+":"+shiqi+":"+shiba);
//						}
//	                    System.out.println();
//	                    xx++;
//	                }
//	            }
//	        }
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public List<String> getMapFile(){
//		List<String> strList = new ArrayList<String>();
//		
//		Map<String,String> map = new HashMap<String,String>();
//		strList.add("11.xlsx");
//		strList.add("12.xlsx");
//		strList.add("13.xlsx");
//		strList.add("14.xlsx");
//		strList.add("15.xlsx");
//		strList.add("21.xlsx");
//		strList.add("22.xlsx");
//		strList.add("23.xlsx");
//		strList.add("31.xlsx");
//		strList.add("32.xlsx");
//		strList.add("33.xlsx");
//		strList.add("34.xlsx");
//		strList.add("35.xlsx");
//		strList.add("36.xlsx");
//		strList.add("37.xlsx");
//		strList.add("41.xlsx");
//		strList.add("42.xlsx");
//		strList.add("43.xlsx");
//		strList.add("44.xlsx");
//		strList.add("45.xlsx");
//		strList.add("46.xlsx");
//		strList.add("50.xlsx");
//		strList.add("51.xlsx");
//		strList.add("52.xlsx");
//		strList.add("53.xlsx");
//		strList.add("54.xlsx");
//		strList.add("61.xlsx");
//		strList.add("62.xlsx");
//		strList.add("63.xlsx");
//		strList.add("64.xlsx");
//		strList.add("65.xlsx");
//		return strList;
//	}
	
}
