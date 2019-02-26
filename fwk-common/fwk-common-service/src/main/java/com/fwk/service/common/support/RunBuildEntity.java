package com.fwk.service.common.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;

/**
 * 说明：通过JsonSchemaFile文件夹内的所有文件生成对应实体
 * 规则：实体文件生成规则：例：\base\area.json --> base_area.java
 * 
 * */
public class RunBuildEntity {
	
	private static String rootPath = "JsonSchemaFile";//文件跟目录
	
	private static String suffix = ".java";//实体文件的后缀
	
//	private static String entityFilePackage = "com.fwk.service.common.support.entity";
	
	private static String entityFilePackage = "/src/main/java/com/fwk/service/common/support/entity";
	
	private static String packageImport = "package com.fwk.service.common.support.entity;"
										+ "\n\n"
										+ "import java.sql.Array;";
	
	//存放所有请求路径地址文件
	private static String supportEntityUrlFile = "AllJsonRequestUrl";
	
	private static List<File> fileList = new ArrayList<File>();

	public static void main(String[] args) {
		try {
			getJsonSchemaFiles();
			if(ObjectUtil.isNotBlank(fileList) && fileList.size() >= 1){
				for (int i = 0; i < fileList.size(); i++) {
					String entityName = getEntityName(fileList.get(i));
					creatEntityFile(entityName+suffix,fileList.get(i));
				}
				creatUrlJavaFile(supportEntityUrlFile+suffix);
			}else{
				System.out.println("没有文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//创建存放文件的路径
	private static void creatUrlJavaFile(String supportEntityUrlFile2) throws Exception{
		//创建实体信息文件
		URL url = RunBuildEntity.class.getResource("../");
		//entity实体的真实存放路径
		String entityFilePath = url.getPath().substring(0, url.getPath().indexOf("/target"))+""+entityFilePackage+"/"+supportEntityUrlFile2;
		File file1 = new File(entityFilePath);
		if(!file1.exists()){
			file1.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file1);
		//开始写入
		write("package com.fwk.service.common.support.entity;\n",out);
		write("public class "+supportEntityUrlFile+" {\n",out);
		//将全部 json 文件内容读取出来写入到 file1 中
		for(File file : fileList){
			//读取所有文件的全部内容
			JSONObject jo = getJsonByFile(file);
			
			if(ObjectUtil.isNotBlank(jo)){
				writeAnnotationToEntity(out,jo.get("title")+"","\t");
			}
			String pretityString = getEntityName(file);
			//json文件的请求路径
			String jsonUrl = getFileJsonUrl(file);
			
			write("\t"+"public static String "+pretityString+" = "+"\""+jsonUrl+"\";",out);
			write("",out);
		}
		write("}",out);
	}

	//将文件的名称及备注写入路径实体文件
	private static void writeToUrlFile(FileOutputStream out, JSONObject jo) {
		if(ObjectUtil.isNotBlank(jo)){
//			System.out.println(jo);
			writeAnnotationToEntity(out,jo.get("title")+"","\t");
			
		}
	}

	/**
	 * 获取所有需要生成实体的JSON文件
	 * */
	private static void getJsonSchemaFiles() throws Exception{
		URL url = RunBuildEntity.class.getResource("/"+rootPath);
		getFileByDirectory(url.getPath());
	}
	
	/**
	 * 获取文件夹下的所有文件
	 * @Path directoryPath
	 * */
	private static void getFileByDirectory(String directoryPath){
		File file = new File(directoryPath);
		if(file.exists()){//文件/文件夹存在
			if(!file.isFile()){
				String[] files = file.list();
				for (int i = 0; i < files.length; i++) {
					getFileByDirectory(directoryPath+"/"+files[i]);
				}
			}else{
				fileList.add(file);
			}
		}else{//不存在
			System.out.println("不存在的文件/文件夹："+file.getPath());
		}
	}
	
	/**
	 * 根据文件生成实体类名称
	 * */
	private static String getEntityName(File file){
		String name = "";
		if(ObjectUtil.isNotBlank(file)){
			String filePath = file.getPath();
			name = filePath.substring(filePath.indexOf(rootPath)+rootPath.length()+1, filePath.indexOf(".")).replaceAll("\\\\", "_");
		}
		name = name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
		return name;
	}
	
	/**
	 * 根据文件生成路径
	 * */
	private static String getFileJsonUrl(File file){
		String result = "";
		result = file.getPath().substring(file.getPath().indexOf(rootPath)+rootPath.length()+1, file.getPath().indexOf("."));
		if(ObjectUtil.isNotBlank(result)){
			result = result.replaceAll("\\\\", ".");
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * 生成实体文件
	 * */
	private static void creatEntityFile(String entityFileName,File rf) throws Exception{
		URL url = RunBuildEntity.class.getResource("../");
		//entity实体的真实存放路径
		String entityFilePath = url.getPath().substring(0, url.getPath().indexOf("/target"))+""+entityFilePackage+"/"+entityFileName;
		File file = new File(entityFilePath);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		writeToFile(out,rf);
	}
	
	/**
	 * 写入文件
	 * */
	public static void write(String str,FileOutputStream out){
        try {
            out.write((new String(str.getBytes("UTF-8"))+"\r\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	/**
	 * 根据文件获取文件内容
	 * */
	public static JSONObject getJsonByFile(File file){
		JSONObject jo = new JSONObject();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String tempString = "";
			String resultStr = "";
			while ((tempString = bufferedReader.readLine())!=null) {
				resultStr += tempString.trim();
			}
			if(ObjectUtil.isNotBlank(resultStr)){
				jo = JSONObject.parseObject(resultStr);
			}else{
				jo = null;
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jo;
	}
	
	
	
	/**
	 * 获取JSONObject对象内的指定属性的值
	 * */
	private static String getJsonKeysVal(JSONObject jo,String title){
		String result = "";
		if(ObjectUtil.isNotBlank(jo)){
			for (String str : jo.keySet()) {
				if(str.equals(title)){
					result = jo.get(title)+"";
				}
			}
		}
		return result;
	}
	
	/**
	 * 写入实体类注释内容
	 * checkBlank:空格
	 * */
	private static void writeAnnotationToEntity(FileOutputStream out,String annotation,String checkBlank){
		write(checkBlank+"/**",out);
		write(checkBlank+" * "+annotation, out);
		write(checkBlank+" * */",out);
	}
	
	/**
	 * 写入实体类属性
	 * */
	private static void writePropertiesToEntity(FileOutputStream out,String properties){
		String writePropertityStr = "private";
		String type = "";
		String propertityname = "";
		
		if(ObjectUtil.isNotBlank(properties)){
			JSONObject jo = JSONObject.parseObject(properties);
			System.out.println("properties:"+jo.keySet());
			for (String propertyName : jo.keySet()) {
				write("",out);
				propertityname = propertyName;
				String propertyNameJson = getJsonKeysVal(jo, propertyName);
				System.out.println(propertyName+"::"+propertyNameJson);
				if(ObjectUtil.isNotBlank(propertyNameJson)){
					JSONObject jo1 = JSONObject.parseObject(propertyNameJson);
					String title = getJsonKeysVal(jo1, "title");//属性注释
					writeAnnotationToEntity(out, title,"\t");
					type = getJsonKeysVal(jo1, "type");//属性类型
					System.out.println(type);
					if(ObjectUtil.isNotBlank(type) && type.length() <=10){
						if(type.length() <=10){
							write("\t"+writePropertityStr+" "+type.substring(0, 1).toUpperCase()+type.substring(1, type.length())+" "+propertityname+";", out);
						}else{
							write("//\t"+writePropertityStr+" "+type.substring(0, 1).toUpperCase()+type.substring(1, type.length())+" "+propertityname+";", out);
						}
					}
				}
			}
		}
//		write("/**",out);
//		write(" * "+title, out);
//		write(" **/",out);
	}
	
	/**
	 * 往文件写入内容
	 * */
	public static void writeToFile(FileOutputStream out,File file){
		JSONObject jo = getJsonByFile(file);
		if(ObjectUtil.isNotBlank(jo) && ObjectUtil.isNotBlank(out)){
			//标头：title,属性：properties,必须的请求参数：required,类型：type,描述：description,自定义：definitions,外键：$id,依赖：dependencies
			write(packageImport,out);
			write("",out);
			//写入文件title内容
			writeAnnotationToEntity(out,getJsonKeysVal(jo,"title"),"");
			//写入class信息
			write("public class "+getEntityName(file)+" {",out);
			//写入实体属性
			writePropertiesToEntity(out,getJsonKeysVal(jo,"properties"));
			
			write("\n",out);
			write("}",out);
			
			write("//"+jo,out);
		}
	}
}
