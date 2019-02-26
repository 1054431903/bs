package com.fwk.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtils {

	private static final String CHARSET_UTF_8 = "utf-8";

	private static final String CONTENT_TYPE = "application/json";

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ResultWrap doGet(String url) throws IOException {
		return sendGet(url);
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return String
	 * @throws IOException
	 */
	public static String doGetReturnString(String url) throws IOException {
		String resStr = null;
		HttpGet get = new HttpGet(url);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return "";
			}
			HttpEntity httpEntity = response.getEntity();
			resStr = EntityUtils.toString(httpEntity, CHARSET_UTF_8);// 取出应答字符串
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			return "";
		} catch (IOException e) {
			return "";
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return resStr;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static ResultWrap doPost(String url, String jsonParam) throws IOException {
		return sendPost(url, jsonParam);
	}

	/**
	 * 发送put请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ResultWrap doPut(String url, String jsonParam) throws IOException {
		return sendPut(url, jsonParam);
	}

	/**
	 * 发送delete请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ResultWrap doDelete(String url) throws IOException {
		return sendDelete(url);
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static ResultWrap sendGet(String url) throws ClientProtocolException, IOException {
		ResultWrap rw = new ResultWrap();
		String resStr = null;
		HttpGet get = new HttpGet(url);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(get);
			if (response == null) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage("请求失败，请确认地址正确！");
				return rw;
			}
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage(response.getStatusLine().toString());
				return rw;
			}
			HttpEntity httpEntity = response.getEntity();
			resStr = EntityUtils.toString(httpEntity, CHARSET_UTF_8);// 取出应答字符串
			resStr.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			rw = JSONObject.parseObject(resStr, ResultWrap.class);
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			EntityUtils.consume(httpEntity);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return rw;
	}

	/**
	 * Post请求
	 * 
	 * @throws ClientProtocolException
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static ResultWrap sendPost(String url, String jsonParam) throws ClientProtocolException, IOException {
		ResultWrap rw = new ResultWrap();
		CloseableHttpClient client = HttpClients.createDefault();
		String resStr = null;
		HttpPost post = new HttpPost(url);
		if (!StringUtils.isEmpty(jsonParam)) {
			HttpEntity entity = new StringEntity(jsonParam, ContentType.create(CONTENT_TYPE, CHARSET_UTF_8));
			post.setEntity(entity);
		}

		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response == null) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage("请求失败，请确认地址正确！");
				return rw;
			}
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage(response.getStatusLine().toString());
				return rw;
			}
			HttpEntity httpEntity = response.getEntity();
			resStr = EntityUtils.toString(httpEntity, CHARSET_UTF_8);// 取出应答字符串
			resStr.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			rw.setData(JSONObject.parseObject(resStr));
//			rw = JSONObject.parseObject(resStr, ResultWrap.class);
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			rw.setData(resStr);
			EntityUtils.consume(httpEntity);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return rw;
	}

	/**
	 * Put请求
	 * 
	 * @throws ClientProtocolException
	 * 
	 * @throws IOException
	 */
	private static ResultWrap sendPut(String url, String jsonParam) throws ClientProtocolException, IOException {
		ResultWrap rw = new ResultWrap();

		String resStr = null;
		HttpPut put = new HttpPut(url);
		if (!StringUtils.isEmpty(jsonParam)) {
			HttpEntity entity = new StringEntity(jsonParam, ContentType.create(CONTENT_TYPE, CHARSET_UTF_8));
			put.setEntity(entity);
		}
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {

			response = client.execute(put);
			if (response == null) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage("请求失败，请确认地址正确！");
				return rw;
			}
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage(response.getStatusLine().toString());
				return rw;
			}
			HttpEntity httpEntity = response.getEntity();
			resStr = EntityUtils.toString(httpEntity, CHARSET_UTF_8);// 取出应答字符串
			resStr.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			rw = JSONObject.parseObject(resStr, ResultWrap.class);
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			EntityUtils.consume(httpEntity);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return rw;
	}

	/**
	 * Delete请求
	 * 
	 * @throws ClientProtocolException
	 * 
	 * @throws IOException
	 */
	private static ResultWrap sendDelete(String url) throws ClientProtocolException, IOException {
		ResultWrap rw = new ResultWrap();
		String resStr = null;
		HttpDelete del = new HttpDelete(url);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(del);
			if (response == null) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage("请求失败，请确认地址正确！");
				return rw;
			}
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage(response.getStatusLine().toString());
				return rw;
			}
			HttpEntity httpEntity = response.getEntity();
			resStr = EntityUtils.toString(httpEntity, CHARSET_UTF_8);// 取出应答字符串
			resStr.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			rw = JSONObject.parseObject(resStr, ResultWrap.class);
			rw.setStatusCode(response.getStatusLine().getStatusCode());
			EntityUtils.consume(httpEntity);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return rw;
	}
	
	 /**
     * 接口调用 GET
     */
    public static String httpURLConnectionGET(String GET_URL) throws Exception{
        URL url = new URL(GET_URL);    // 把字符串转换为URL请求地址
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
        connection.connect();// 连接会话
        // 获取输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARSET_UTF_8));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {// 循环读取流
            sb.append(line + "\n");
        }
        br.close();// 关闭流
        connection.disconnect();// 断开连接
        String resultStr = sb.toString();
        
        //手动处理粘包的情况(只能处理部分，后续如果发现还有其他情况，需再增加)
        resultStr = resultStr.substring(0, resultStr.indexOf("}HTTP") > -1 ? resultStr.indexOf("}HTTP") + 1 : resultStr.length());
        resultStr = resultStr.substring(0, resultStr.indexOf("}Content") > -1 ? resultStr.indexOf("}Content") + 1 : resultStr.length());
        resultStr = resultStr.substring(0, resultStr.indexOf("}Connection") > -1 ? resultStr.indexOf("}Connection") + 1 : resultStr.length());
        resultStr = resultStr.substring(0, resultStr.indexOf("}Date") > -1 ? resultStr.indexOf("}Date") + 1 : resultStr.length());
        resultStr = resultStr.substring(0, resultStr.indexOf("}Access") > -1 ? resultStr.indexOf("}Access") + 1 : resultStr.length());
        resultStr = resultStr.substring(0, resultStr.indexOf("}application") > -1 ? resultStr.indexOf("}application") + 1 : resultStr.length());
        return resultStr;
    }
	
	/**
	 * HTTP URL请求 post (用于执行添加数据)
	 * */
	public static String httpURLConnectionPOST(String POST_URL,String dataJson) throws Exception{
//		ResultWrap resultWrap = HttpClientUtils.doPost(POST_URL, dataJson);
//		if(1==1) return resultWrap;
		
        URL url = new URL(POST_URL);
        // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中
        // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
        connection.setDoOutput(true);
        // 设置连接输入流为true
        connection.setDoInput(true);
        // 设置请求方式为post
        connection.setRequestMethod("POST");
        // post请求缓存设为false
        connection.setUseCaches(false);
        // 设置该HttpURLConnection实例是否自动执行重定向
        connection.setInstanceFollowRedirects(true);
        // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
        // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
        connection.connect();
        // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
        DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
        String parm = "storeId=" + URLEncoder.encode("32", "utf-8"); //URLEncoder.encode()方法  为字符串进行编码
        // 将参数输出到连接
        dataout.writeBytes(dataJson);
        // 输出完成后刷新并关闭流
        dataout.flush();
        dataout.close(); // 重要且易忽略步骤 (关闭流,切记!) 
        // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
        String line;
        StringBuilder sb = new StringBuilder(); // 用来存储响应数据
        // 循环读取流,若不到结尾处
        while ((line = bf.readLine()) != null) {
            sb.append(bf.readLine());
        }
        bf.close();    // 重要且易忽略步骤 (关闭流,切记!) 
        connection.disconnect(); // 销毁连接
        return null;
    }
	
	/**  
     * 发送消息体到服务端  
     *   
     * @param params  
     * @param encode  
     * @return  
     */  
    public static String sendPostMessage(String PATH,Map<String, String> params) {  
    	URL url = null;
		try {
			url = new URL(PATH);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        StringBuilder stringBuilder = new StringBuilder();  
        if (params != null && !params.isEmpty()) {  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                try {  
                    stringBuilder  
                            .append(entry.getKey())  
                            .append("=")  
                            .append(URLEncoder.encode(entry.getValue(), "UTF-8"))  
                            .append("&");  
                } catch (UnsupportedEncodingException e) {  
                    e.printStackTrace();  
                }  
            }  
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);  
            try {  
                HttpURLConnection urlConnection = (HttpURLConnection) url  
                        .openConnection();  
                urlConnection.setConnectTimeout(3000);  
                urlConnection.setRequestMethod("POST"); // 以post请求方式提交  
                urlConnection.setDoInput(true); // 读取数据  
                urlConnection.setDoOutput(true); // 向服务器写数据  
                // 获取上传信息的大小和长度  
                byte[] myData = stringBuilder.toString().getBytes();  
                // 设置请求体的类型是文本类型,表示当前提交的是文本数据  
                urlConnection.setRequestProperty("Content-Type",  
                        "application/x-www-form-urlencoded");  
                urlConnection.setRequestProperty("Content-Length",  
                        String.valueOf(myData.length));  
                // 获得输出流，向服务器输出内容  
                OutputStream outputStream = urlConnection.getOutputStream();  
                // 写入数据  
                outputStream.write(myData, 0, myData.length);  
                outputStream.close();  
                // 获得服务器响应结果和状态码  
                int responseCode = urlConnection.getResponseCode();  
                if (responseCode == 200) {  
                    // 取回响应的结果  
                    return changeInputStream(urlConnection.getInputStream(),  
                            "UTF-8");  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
  
        }  
        return "";  
    }
    
    /**  
     * 将一个输入流转换成指定编码的字符串  
     *   
     * @param inputStream  
     * @param encode  
     * @return  
     */  
    private static String changeInputStream(InputStream inputStream,  
            String encode) {  
  
        // 内存流  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
        byte[] data = new byte[1024];  
        int len = 0;  
        String result = null;  
        if (inputStream != null) {  
            try {  
                while ((len = inputStream.read(data)) != -1) {  
                    byteArrayOutputStream.write(data, 0, len);  
                }  
                result = new String(byteArrayOutputStream.toByteArray(), encode);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
    
    /**  
     * @param args  
     */  
    public static void main(String[] args) {  
    	
//    	String path = "http://47.104.162.6:9000/api/mongo/iov/tenant.staff";
    	String path = "http://10.88.20.127:9000/api/mongo/iov/tenant.staff";
        String result = "";
        
		try {
			ResultWrap resultWrap = doPost(path, "{'mobile':'xx15288121811'}");
			System.out.println(resultWrap);
//			System.out.println(JSONObject.parseObject(resultWrap));
//			result = httpURLConnectionPOST(path,"{'mobile':'xx15288121811'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println(">>>" + result);  
    } 
	
	/**
	 * HTTP URL请求put (用于执行修改数据)
	 * */
	public static String httpURLConnectionPUT(String path,String obj) throws IOException, JSONException {
		//创建连接
        URL url = new URL(path);
        HttpURLConnection connection;
        StringBuffer sbuffer=null;
    	//添加 请求内容
        connection= (HttpURLConnection) url.openConnection();
        //设置http连接属性
        connection.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
        connection.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
        connection.setRequestMethod("PUT"); // 可以根据需要 提交 GET、POST、DELETE、PUT等http提供的功能
        //connection.setUseCaches(false);//设置缓存，注意设置请求方法为post不能用缓存
        // connection.setInstanceFollowRedirects(true);

//            connection.setRequestProperty("Host", "*******");  //设置请 求的服务器网址，域名，例如***.**.***.***
        connection.setRequestProperty("Content-Type", " application/json");//设定 请求格式 json，也可以设定xml格式的
        connection.setRequestProperty("Accept-Charset", "utf-8");  //设置编码语言
        connection.setRequestProperty("X-Auth-Token", "token");  //设置请求的token
        connection.setRequestProperty("Connection", "keep-alive");  //设置连接的状态
        connection.setRequestProperty("Transfer-Encoding", "chunked");//设置传输编码
        connection.setRequestProperty("Content-Length", obj.toString().getBytes().length + "");  //设置文件请求的长度         
        connection.setReadTimeout(10000);//设置读取超时时间
        connection.setConnectTimeout(10000);//设置连接超时时间
        connection.connect();            
        OutputStream out = connection.getOutputStream();//向对象输出流写出数据，这些数据将存到内存缓冲区中          
        out.write(obj.toString().getBytes());            //out.write(new String("测试数据").getBytes());            //刷新对象输出流，将任何字节都写入潜在的流中       
        out.flush();
        // 关闭流对象,此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中          
        out.close();           
        System.out.println("connection:"+connection);
        //读取响应           
        if (connection.getResponseCode()==200){
            // 从服务器获得一个输入流
            InputStreamReader   inputStream =new InputStreamReader(connection.getInputStream());//调用HttpURLConnection连接对象的getInputStream()函数, 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
            BufferedReader reader = new BufferedReader(inputStream);                
			String lines;                
			sbuffer= new StringBuffer("");              
	  		while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sbuffer.append(lines);
			}                
			reader.close();         
	 	 }else{          
		    return "";
         }    
		 //断开连接           
 		 connection.disconnect();   
	    return sbuffer.toString();
	 }
	
	public static String httpUrlConnectionPut(String httpUrl, String jsonParam) throws Exception{  
        String result = "";  
        URL url = new URL(httpUrl);  
        if (url != null) {  
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();  
            urlConn.setRequestProperty("content-type", "application/json");  
            urlConn.setDoInput(true);  
            urlConn.setDoOutput(true);  
            urlConn.setConnectTimeout(5 * 1000);  
            //设置请求方式为 PUT  
            urlConn.setRequestMethod("PUT");  
              
            urlConn.setRequestProperty("Content-Type", "application/json");  
            urlConn.setRequestProperty("Accept", "application/json");  
              
            urlConn.setRequestProperty("Charset", "UTF-8");  
              
  
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());  
            //写入请求参数  
            //这里要注意的是，在构造JSON字符串的时候，实践证明，最好不要使用单引号，而是用“\”进行转义，否则会报错  
             // 关于这一点在上面给出的参考文章里面有说明  
//                String jsonParam = "{\"appid\":6,\"appkey\":\"0cf0vGD/ClIrVmvVT/r5hEutH5M=\",\"openid\":200}";  
            dos.writeBytes(jsonParam);  
            dos.flush();  
            dos.close();  
              
            if (urlConn.getResponseCode() == 200) {  
                InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());  
                BufferedReader br = new BufferedReader(isr);  
                String inputLine = null;  
                while ((inputLine = br.readLine()) != null) {  
                    result += inputLine;  
                }  
                isr.close();  
                urlConn.disconnect();  
            }  
        }  
  
        return result;  
  
    }
	
	public static String httpUrlConnectionPut1(String urlStr, String param) throws SocketTimeoutException, MalformedURLException, IOException {  
        HttpURLConnection conn = null;  
        BufferedReader in = null;  
        PrintWriter out = null;  
        URL url = new URL(urlStr);  
        conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5*1000);  
        conn.setReadTimeout(5*1000);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));  
        out.print(param);  
        out.print("\r\n");  
        out.flush();  
        StringBuilder sb = new StringBuilder();  
        String line;  
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
        while ((line = in.readLine()) != null) {  
            sb.append(line).append("\r\n");  
        }  
        out.flush(); 
        if (conn != null) { conn.disconnect(); }  
        if (in != null) { in.close(); }  
        return sb.toString();  
    }  
	
	/**
	 * HTTP URL请求delete (用于执行删除数据)
	 * */
	public static String httpURLConnectionDELETE(String url, byte[] parameter, String base64) throws Exception{

        java.util.Date data = new java.util.Date();
        String timestamp = String.valueOf(data.getTime()/1000);//时间戳（精确到秒）
        String signature = "*********";
       //定义stringbuffer  方便后面读取网页返回字节流信息时的字符串拼接
       StringBuffer buffer = new StringBuffer();
       //创建url_connection
       java.net.URLConnection http_url_connection = null;
       try {
           http_url_connection = (new java.net.URL(url)).openConnection();
           java.net.HttpURLConnection HttpURLConnection = (java.net.HttpURLConnection)http_url_connection;//将urlconnection类强转为httpurlconnection类
           HttpURLConnection.setDoInput(true);
           HttpURLConnection.setDoOutput(true);
           HttpURLConnection.setRequestMethod("DELETE");//设置请求方式。可以是delete put post get
           HttpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameter.length));//设置内容的长度
           HttpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置编码格式
           HttpURLConnection.setRequestProperty("accept", "application/json");//设置接收返回参数格式
           HttpURLConnection.setRequestProperty("authkey","************");
           HttpURLConnection.setRequestProperty("signature",signature);
           HttpURLConnection.setRequestProperty("timestamp",timestamp);

           HttpURLConnection.setUseCaches(false);

           if (null!=base64) {
               //如果传入的参数不为空，则通过base64将传入参数解码
//               HttpURLConnection.setRequestProperty("Authorization", "Basic "+new String(java.util.Base64.getEncoder().encode(base64.getBytes("utf-8")), "utf-8"));
           }

           // write request.
           java.io.BufferedOutputStream output_stream = new java.io.BufferedOutputStream(HttpURLConnection.getOutputStream());
           output_stream.write(parameter);
           output_stream.flush();
           output_stream.close();
           output_stream = null;

           java.io.InputStreamReader input_stream_reader = new java.io.InputStreamReader(HttpURLConnection.getInputStream(), "utf-8");
           java.io.BufferedReader buffered_reader = new java.io.BufferedReader(input_stream_reader);
           buffer = new StringBuffer();
           String line;
           while ((line = buffered_reader.readLine()) != null) {
               buffer.append(line);
           }
           line=null;
           input_stream_reader.close();
           input_stream_reader = null;
           buffered_reader.close();
           buffered_reader = null;
       //  http_url_connection.disconnect();
       } catch (Exception e) {
           e.printStackTrace();
       }

       return buffer.toString();
   }
}
