package com.fwk.common.util;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



public class SmsApi {

//查账户信息的http地址
private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v1/user/get.json";

//智能匹配模板发送接口的http地址
private static String URI_SEND_SMS = "https://sms.yunpian.com/v1/sms/send.json";

private static String URI_MULTISEND_SMS ="https://sms.yunpian.com/v1/sms/multi_send.json";

//模板发送接口的http地址
private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v1/sms/tpl_send.json";

//发送语音验证码接口的http地址
private static String URI_SEND_VOICE = "https://voice.yunpian.com/v1/voice/send.json";

//编码格式。发送编码格式统一用UTF-8
private static String ENCODING = "UTF-8";

//银河之星KEY
private static String apikey="ef14b17e04e23065b9c73499c281df29";

/**
 * 取账户信息
 *
 * @return json格式字符串
 * @throws java.io.IOException
 */

public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("apikey", apikey);
    return post(URI_GET_USER_INFO, params);
}

/**
 * 批量智能匹配模板接口发短信
 *
 * @param apikey apikey
 * @param text   　短信内容
 * @param mobile 　接受的手机号
 * @return json格式字符串
 * @throws IOException
 */

public static String sendMultiSms(String text, String mobile) throws IOException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("apikey", apikey);
    params.put("text", text);
    params.put("mobile", mobile);
    return post(URI_MULTISEND_SMS, params);
}

/**
 * 智能匹配模板接口发短信
 *
 * @param apikey apikey
 * @param text   　短信内容
 * @param mobile 　接受的手机号
 * @return json格式字符串
 * @throws IOException
 */

public static String sendSms(String text, String mobile) throws IOException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("apikey", apikey);
    params.put("text", text);
    params.put("mobile", mobile);
    return post(URI_SEND_SMS, params);
}



/**
 * 通过模板发送短信(不推荐)
 *
 * @param apikey    apikey
 * @param tpl_id    　模板id
 * @param tpl_value 　模板变量值
 * @param mobile    　接受的手机号
 * @return json格式字符串
 * @throws IOException
 */

public static String tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("apikey", apikey);
    params.put("tpl_id", String.valueOf(tpl_id));
    params.put("tpl_value", tpl_value);
    params.put("mobile", mobile);
    return post(URI_TPL_SEND_SMS, params);
}

/**
 * 通过接口发送语音验证码
 * @param apikey apikey
 * @param mobile 接收的手机号
 * @param code   验证码
 * @return
 */

public static String sendVoice(String mobile, String code) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("apikey", apikey);
    params.put("mobile", mobile);
    params.put("code", code);
    return post(URI_SEND_VOICE, params);
}

/**
 * 基于HttpClient 4.3的通用POST方法
 *
 * @param url       提交的URL
 * @param paramsMap 提交<参数，值>Map
 * @return 提交响应
 */

public static String post(String url, Map<String, String> paramsMap) {
    CloseableHttpClient client = HttpClients.createDefault();
    String responseText = "";
    CloseableHttpResponse response = null;
    try {
        HttpPost method = new HttpPost(url);
        if (paramsMap != null) {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                paramList.add(pair);
            }
            method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
        }
        response = client.execute(method);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            responseText = EntityUtils.toString(entity);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return responseText;
}
}

