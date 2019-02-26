package com.fwk.service.common.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.Files;
import com.fwk.common.util.HttpClientUtils;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.UUIDUtils;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import sun.misc.BASE64Decoder;

@Service
public class FileService {
	
	@Autowired
	private AccessService accessService;
	
	@Autowired
	private ResourceService resourceService;

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	public ResultWrap uploadFile(Map<String,Object> requestMap){
		ResultWrap rw = new ResultWrap();
		String urlM = accessService.SERVICE_DB_UPLOADRESOURCE;
		String uuid = UUIDUtils.getUUID();
		Object pidData = requestMap.get("picData");
		if(pidData!=null){
			File f = new File(pidData.toString());
			byte[] b=GenerateImage(pidData.toString());
			List<Files> list = new ArrayList<Files>();
			Files file = new Files();
//			file.setData(org.apache.commons.codec.binary.Base64.decodeBase64(pidData.toString().getBytes()));
			file.setData(b);
			String extName = pidData.toString().substring(pidData.toString().indexOf(".")+1, pidData.toString().length());//后缀
			file.setExtName(extName);
			file.setUploadDate(new Date());
			
//			List<MyFile> list = new ArrayList<MyFile>();
//			MyFile myFile = new MyFile();
//			myFile.setFilename(uuid);
//			myFile.setData(
//					org.apache.commons.codec.binary.Base64.decodeBase64(pidData.toString().getBytes()));
			list.add(file);
			try {
				rw = HttpClientUtils.doPost(urlM, JSON.toJSONString(list));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(rw.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR){
				rw.setMessage("保存内容类型图片失败！！！");
			}
		}
		return rw;
	}
	
	/**
    * 图片上传
    * @param urlpatten
    * @return
    */
   private  String urlOperate(String urlpatten){
	   String imgpath="";
	   String headerdata="data:image"; //标识
	   List<Files> list=new LinkedList<Files>();//多文件上传,此处上传一张
	   if(urlpatten.startsWith(headerdata)){
		   // String imgsuffix=urlpatten.substring(urlpatten.indexOf("/")+1,urlpatten.indexOf(";"));//图片后缀
			byte[] b=GenerateImage(urlpatten);
			//判断文件真实类型
			boolean st=isImage(b);
			if(st==false){//不是图片
				imgpath=null;
			}else{//是图片
				Files img=new Files();
				img.setData(b);
				imgpath=UUIDUtils.getUUID();
				img.setResName(imgpath);
				img.setUploadDate(new Date());
				String format = urlpatten.substring(urlpatten.indexOf(":")+1, urlpatten.indexOf(";"));
				img.setFormat(format);
				String extName = format.substring(format.indexOf("/")+1);
				img.setExtName(extName);
				list.add(img);
				ResultWrap ob=(ResultWrap)resourceService.saveResByUUId(list); //上传图片
				if(ob.getFlag()==0){
					imgpath=null;
				}
			}
	   }else{
		   imgpath=urlpatten.substring(urlpatten.lastIndexOf("/")+1);
	   }
	   return imgpath;
   }
   
   /**
	 * 判断文件是否是图片
	 * @param file
	 * @return
	 */
	private  final boolean isImage(byte[] b){  
	   ByteArrayInputStream in = new ByteArrayInputStream(b);
       boolean flag = false;  
       try  
       {  
           BufferedImage bufreader = ImageIO.read(in);
           int width = bufreader.getWidth();  
           int height = bufreader.getHeight();  
           if(width==0 || height==0){  
               flag = false;
           }else {  
               flag = true;
           }  
       }  
       catch (IOException e)  
       {  
           flag = false;  
       }catch (Exception e) {  
           flag = false;  
       }  
       return flag;  
   }
	
	/**
    *base64 to file
    * @param imgStr
    */
   private  byte[]  GenerateImage(String imgStr){
	   byte[] b=null;
	   if (imgStr == null) // 图像数据为空
		   logger.info("base数据为空");
	   else{
		   imgStr=imgStr.substring(imgStr.indexOf(",")+1); //图片数据
		   BASE64Decoder decoder = new BASE64Decoder();
		   try {
			// Base64解码
				 b = decoder.decodeBuffer(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
		   }catch (Exception e) {
			   logger.info("base数据解析错误");
		   }
	   }
	   return b;
   }
   
   /**
    * 导出文件
    * @param fileName:EXCEL文件名称
    * @param title:EXCEL文件第一行列标题集合
    * @param content:EXCEL文件正文数据集合
    * */
   public String loadFile(String fileName,String[] title,List<Object> listContent,HttpServletResponse response){
	   String result="系统提示：Excel文件导出成功！";    
	   // 以下开始输出到EXCEL  
	   try {      
	    //定义输出流，以便打开保存对话框______________________begin  
	    OutputStream os = response.getOutputStream();// 取得输出流        
	    response.reset();// 清空输出流        
	    response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));  
	 // 设定输出文件头        
	    response.setContentType("application/msexcel");// 定义输出类型      
	    //定义输出流，以便打开保存对话框_______________________end  
	   
	    /** **********创建工作簿************ */  
	    WritableWorkbook workbook = Workbook.createWorkbook(os);  
	   
	    /** **********创建工作表************ */  
	    WritableSheet sheet = workbook.createSheet("Sheet1", 0);  
	   
	    /** **********设置纵横打印（默认为纵打）、打印纸***************** */  
	    jxl.SheetSettings sheetset = sheet.getSettings();  
	    sheetset.setProtected(false);  
	   
	    /** ************设置单元格字体************** */  
	    WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);  
	    WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);  
	   
	    /** ************以下设置三种单元格样式，灵活备用************ */  
	    // 用于标题居中  
	    WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);  
	    wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
	    wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
	    wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
	    wcf_center.setWrap(false); // 文字是否换行  
	      
	    // 用于正文居左  
	    WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);  
	    wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
	    wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
	    wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐  
	    wcf_left.setWrap(false); // 文字是否换行     
	    
	   
	    /** ***************以下是EXCEL开头大标题，暂时省略********************* */  
	    /** ***************以下是EXCEL第一行列标题********************* */  
	    for (int i = 0; i < title.length; i++) {  
	    	//设置字体为Arial，30号，加粗  
	    	sheet.addCell(new Label(i, 0,title[i],wcf_center));
	    	CellView c = new CellView();
	    	c.setAutosize(true);
	    	sheet.setColumnView(i, c);
	    	sheet.setRowView(0, 500);
	    }     
	    /** ***************以下是EXCEL正文数据********************* */  
	    Field[] fields=null;  
	    int i=1;  
	    for(Object obj:listContent){  
	        fields=obj.getClass().getDeclaredFields();  
	        int j=0;  
	        for(Field v:fields){  
	            v.setAccessible(true);  
	            Object va=v.get(obj);  
	            if(va==null){  
	                va="";  
	            }  
	            sheet.addCell(new Label(j, i,va.toString(),wcf_left)); 
	            j++;  
	        }  
	        sheet.setRowView(i, 400);
	        i++;  
	    }  
	    /** **********将以上缓存中的内容写到EXCEL文件中******** */  
	    workbook.write();  
	    /** *********关闭文件************* */  
	    workbook.close();     
	   } catch (Exception e) {  
	    result="系统提示：Excel文件导出失败，原因："+ e.toString();  
	    e.printStackTrace();  
	   }  
	   return result;
   }

}
