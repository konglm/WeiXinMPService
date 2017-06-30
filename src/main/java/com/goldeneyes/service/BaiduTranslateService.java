/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：
 * 文件功能描述：
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.goldeneyes.pojo.TranslateResult;
import com.goldeneyes.util.MD5;
import com.google.gson.Gson;

public class BaiduTranslateService {  
    /** 
     * 发起http请求获取返回结果 
     *  
     * @param requestUrl 
     *            请求地址 
     * @return 
     */  
    public static String httpRequest(String requestUrl) {  
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
  
            httpUrlConn.setDoOutput(false);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
  
        } catch (Exception e) {  
        }  
        return buffer.toString();  
    }  
  
    /** 
     * utf编码 
     *  
     * @param source 
     * @return 
     */  
    public static String urlEncodeUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLEncoder.encode(source, "utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    /** 
     * 翻译（中->英 英->中 日->中 ） 
     *  
     * @param source 
     * @return 
     */  
    public static String translate(String source) {  
        String dst = null;  
  
        // 组装查询地址  
        String requestUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate?q={keyWord}&from=auto&to=auto&appid=20170630000061025&salt={salt}&sign={sign}";  
        // 对参数q的值进行urlEncode utf-8编码  
        requestUrl = requestUrl.replace("{keyWord}", source); 
        String salt = String.valueOf(System.currentTimeMillis());
        requestUrl = requestUrl.replace("{salt}", salt);  
        String src = "20170630000061025" + source + salt + "_beGaEJ4Wf9pbfAtAqrV"; // 加密前的原文
        String sign = "";
		try {
			sign = MD5.md5(src);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        requestUrl = requestUrl.replace("{sign}", sign);  
        // 查询并解析结果  
        try {  
            // 查询并获取返回结果  
            String json = httpRequest(requestUrl);  
            // 通过Gson工具将json转换成TranslateResult对象  
            TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);  
            // 取出translateResult中的译文  
            dst = translateResult.getTrans_result().get(0).getDst();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        if (null == dst) {  
            dst = "翻译系统异常，请稍候尝试！";  
        }  
        return dst;  
    }  
  
    public static void main(String[] args) {  
        // System.setProperty("http.proxySet", "true");  
        // System.setProperty("http.proxyHost", "proxy.jpn.hp.com");  
        // System.setProperty("http.proxyPort", "8080");  
  
        // 翻译结果：The network really powerful  
        // System.out.println(translate("网络真强大"));  
        System.out.println(translate("明天"));  
    }  
} 
