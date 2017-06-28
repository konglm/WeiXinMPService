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

package com.goldeneyes.util;

import com.goldeneyes.pojo.Menu;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class WeixinUtil {
	// 菜单创建（POST） 限100（次/天）  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
	  
	/** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = HttpUtils.httpRequest(url, "POST", jsonMenu);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");    
	        }  
	    }  
	  
	    return result;  
	} 
	
	 /** 
     *  
     * @Description: 获取access_token 
     * @param appid  
     * @param appsecret  
     * @Title: getAccessToken 
     * @return AccessToken     
     */  
    public static String getAccessToken(String apiurl, String appid, String secret) {  
        String accessToken = null;  
  
        String requestUrl = String.format(
                "%s?grant_type=client_credential&appid=%s&secret=%s", apiurl,
                appid, secret); 
        JSONObject jsonObject = HttpUtils.httpRequest(requestUrl, "GET", null);  
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = jsonObject.getString(
        				"access_token");
            } catch (JSONException e) {  

            }  
        }  
        return accessToken;  
    }  
}
