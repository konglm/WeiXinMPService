/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：SendMsg
 * 文件功能描述：微信群发功能
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.func;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.goldeneyes.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class SendMsg
{
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
    public static final String UPLOAD_IMAGE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";// 上传多媒体文件
    public static final String UPLOAD_FODDER_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
    public static final String GET_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/get"; // url
    public static final String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
    public static final String APP_ID = "wx52e245a4d6342f42";
    public static final String SECRET = "5513eaa0e9dc2f78d80567c23fd85a23";


    /**
     * 发送消息
     * 
     * @param uploadurl
     *            apiurl
     * @param access_token
     * @param data
     *            发送数据
     * @return
     */
    public static String sendMsg(String url, String token, String data)
    {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        String sendurl = String.format("%s?access_token=%s", url, token);
        PostMethod post = new UTF8PostMethod(sendurl);
        post
                .setRequestHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");

        post.setRequestHeader("Host", "file.api.weixin.qq.com");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        String result = null;
        try
        {
            post.setRequestBody(data);
            int status = client.executeMethod(post);
            if (status == HttpStatus.SC_OK)
            {
                String responseContent = post.getResponseBodyAsString();
                System.out.println(responseContent);
                JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
                JsonObject json = jsonparer.parse(responseContent)
                        .getAsJsonObject();
                if (json.get("errcode") != null
                        && json.get("errcode").getAsString().equals("0"))
                {
                    result = json.get("errmsg").getAsString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }
    
    public static class UTF8PostMethod extends PostMethod {//防止发送的消息产生乱码
        public UTF8PostMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {//文本编码格式
            return "UTF-8";
        }
    }

    public static void main(String[] args) throws Exception
    {
        String accessToken = getAccessToken(GET_TOKEN_URL, APP_ID, SECRET);// 获取token在微信接口之一中获取
//        if (accessToken != null)// token成功获取
//        {
//            System.out.println(accessToken);
//            File file = new File("e:" + File.separator + "damingh3u.jpg"); // 获取本地文件
//            String id = uploadImage(UPLOAD_IMAGE_URL, accessToken, "image",
//                    file);// java微信接口之三—上传多媒体文件 可获取
//            if (id != null)
//            {
//                // 构造数据
//                Map map = new HashMap();
//                map.put("thumb_media_id", id);
//                map.put("author", "wxx");
//                map.put("title", "标题");
//                map.put("content", "测试fdsfdsfsdfssfdsfsdfsdfs");
//                map.put("digest", "digest");
//                map.put("show_cover_pic", "0");
//
//                Map map2 = new HashMap();
//                map2.put("thumb_media_id", id);
//                map2.put("author", "wxx");
//                map2.put("content_source_url", "www.google.com");
//                map2.put("title", "标题");
//                map2.put("content", "测试fdsfdsfsdfssfdsfsdfsdfs");
//                map2.put("digest", "digest");
//
//                Map map3 = new HashMap();
//                List<Map> list = new ArrayList<Map>();
//                list.add(map);
//                list.add(map2);
//                map3.put("articles", list);
//
//                Gson gson = new Gson();
//                String result = gson.toJson(map3);// 转换成json数据格式
//                String mediaId = uploadFodder(UPLOAD_FODDER_URL, accessToken,
//                        result);
//                if (mediaId != null)
//                {
//                    String ids = getGroups(GET_USER_GROUP, accessToken);// 在java微信接口之二—获取用户组
//                    if (ids != null)
//                    {
//                        String[] idarray = ids.split(",");// 用户组id数组
//                        for (String gid : idarray)
//                        {
//                            
//                            JsonObject jObj = new JsonObject();
//                            JsonObject filter = new JsonObject();
//                            filter.addProperty("group_id", gid);
//                            jObj.add("filter", filter);
// 
// 
//                            JsonObject mpnews = new JsonObject();
//                            mpnews.addProperty("media_id", mediaId);
//                            jObj.add("mpnews", mpnews);
// 
//                            jObj.addProperty("msgtype", "mpnews"); 
//                            System.out.println(jObj.toString());
//
//                            String result2 = sendMsg(SEND_MESSAGE_URL,
//                                    accessToken, jObj.toString());
//                            System.out.println(result2);
//                        }
//                    }
//                }
//
//            }
//        }
    }
    
    /**
     * 上传素材
     * 
     * @param uploadurl
     *            apiurl
     * @param access_token
     *            访问token
     * @param data
     *            提交数据
     * @return
     */
    public static String uploadFodder(String uploadurl, String access_token,
            String data)
    {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        String posturl = String.format("%s?access_token=%s", uploadurl,
                access_token);
        PostMethod post = new PostMethod(posturl);
        post
                .setRequestHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Host", "file.api.weixin.qq.com");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        String result = null;
        try
        {
            post.setRequestBody(data);
            int status = client.executeMethod(post);
            if (status == HttpStatus.SC_OK)
            {
                String responseContent = post.getResponseBodyAsString();
                System.out.println(responseContent);
                JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
                JsonObject json = jsonparer.parse(responseContent)
                        .getAsJsonObject();
                if (json.get("errcode") == null)
                {// 正确 { "type":"news", "media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ","created_at":1391857799}
                    result = json.get("media_id").getAsString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }
    
    /**
     * 上传多媒体文件
     * 
     * @param url
     *            访问url
     * @param access_token
     *            access_token
     * @param type
     *            文件类型
     * @param file
     *            文件对象
     * @return
     */
    public static String uploadImage(String url, String access_token,
            String type, File file)
    {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        String uploadurl = String.format("%s?access_token=%s&type=%s", url,
                access_token, type);
        PostMethod post = new PostMethod(uploadurl);
        post
                .setRequestHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Host", "file.api.weixin.qq.com");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        String result = null;
        try
        {
            if (file != null && file.exists())
            {
                FilePart filepart = new FilePart("media", file, "image/jpeg",
                        "UTF-8");
                Part[] parts = new Part[] { filepart };
                MultipartRequestEntity entity = new MultipartRequestEntity(
                        parts,

                        post.getParams());
                post.setRequestEntity(entity);
                int status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK)
                {
                    String responseContent = post.getResponseBodyAsString();
                    JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
                    JsonObject json = jsonparer.parse(responseContent)
                            .getAsJsonObject();
                    if (json.get("errcode") == null)// {"errcode":40004,"errmsg":"invalid media type"}
                    { // 上传成功  {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
                        result = json.get("media_id").getAsString();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }
    
    /**
     * 获取用户组信息
     * 
     * @param url
     *            访问url
     * @param token
     *            access_token
     * @return id字符串，每个id以,分割
     */
    public static String getGroups(String url, String token)
    {
        String groupurl = String.format("%s?access_token=%s", url, token);
        System.out.println(groupurl);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(groupurl);
        String result = null;
        try
        {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
            JsonObject json = jsonparer.parse(responseContent)
                    .getAsJsonObject();// 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)// 成功返回消息
            {
                if (json.get("errcode") == null)// 不存在错误消息，成功返回
                {
                    JsonArray groups = json.getAsJsonArray("groups"); // 返回对象数组
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < groups.size(); i++)
                    {
                        buffer.append(groups.get(i).getAsJsonObject().get("id")
                                .getAsString()
                                + ",");
                    }
                    result = buffer.toString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        { // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }
    
 // 获取token
    public static String getToken(String apiurl, String appid, String secret)
    {
        String turl = String.format(
                "%s?grant_type=client_credential&appid=%s&secret=%s", apiurl,
                appid, secret);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(turl);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        String result = null;
        try
        {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            JsonObject json = jsonparer.parse(responseContent)
                    .getAsJsonObject();
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                if (json.get("errcode") != null)
                {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                }
                else
                {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    result = json.get("access_token").getAsString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }
    
    public static String getToken_getTicket(String apiurl, String appid, String secret) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", appid);
        params.put("secret", secret);
        String jstoken = HttpUtils.sendGet(
        		apiurl, params);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        String access_token = jsonparer.parse(jstoken).getAsJsonObject().get("access_token").getAsString(); // 获取到token并赋值保存
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
        return access_token;
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
        System.out.println(accessToken);;
        return accessToken;  
    }  
}
