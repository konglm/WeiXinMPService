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

package com.goldeneyes.func;

import com.goldeneyes.pojo.Button;  
import com.goldeneyes.pojo.CommonButton;  
import com.goldeneyes.pojo.ComplexButton;  
import com.goldeneyes.pojo.Menu;
import com.goldeneyes.pojo.ViewButton;
import com.goldeneyes.util.WeixinUtil;  
  
/**
 * 菜单创建方法
 * 无需上传到微信的服务器url，直接执行main函数即可
 * @author Administrator
 *
 */
public class MenuManager {  
	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
	public static final String APP_ID = "wx52e245a4d6342f42";
    public static final String SECRET = "5513eaa0e9dc2f78d80567c23fd85a23";  
    
    public static void main(String[] args) {  
        // 调用接口获取access_token  
        String at = WeixinUtil.getAccessToken(GET_TOKEN_URL, APP_ID, SECRET);
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at);
            System.out.println(result);
        }  
    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("天气预报");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("公交查询");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("周边搜索");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("历史上的今天");  
        btn14.setType("click");  
        btn14.setKey("14");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("歌曲点播");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("经典游戏");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("美女电台");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("人脸识别");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        CommonButton btn25 = new CommonButton();  
        btn25.setName("聊天唠嗑");  
        btn25.setType("click");  
        btn25.setKey("25");  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("Q友圈");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        ViewButton btn32 = new ViewButton();  
        btn32.setName("使用帮助");  
        btn32.setType("view");  
        btn32.setUrl("http://baidu.com"); 
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("幽默笑话");  
        btn33.setType("click");  
        btn33.setKey("33");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("生活助手");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("休闲驿站");  
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多体验");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
        
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}  