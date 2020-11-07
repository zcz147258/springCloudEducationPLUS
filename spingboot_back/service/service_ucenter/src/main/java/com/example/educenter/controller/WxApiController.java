package com.example.educenter.controller;

import com.example.commonutils.JwtUtils;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.service.UcenterMemberService;
import com.example.educenter.utils.ConstantWxUtils;
import com.example.educenter.utils.HttpClientUtils;
import com.example.servicebase.exception.GuliExecption;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {


    @Autowired
    private UcenterMemberService memberService;

    /**
     * 生成微信二维码
     */
    @GetMapping("login")
    public String getWxCode(){
        //固定地址
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对重定向地址编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        System.out.println(redirectUrl);

        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new GuliExecption(20001,"转码异常");
        }
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        //请求微信地址
        return "redirect:" + url;
    }


    /**
     * 获取扫描人信息,添加数据
     */
    @GetMapping("callback")
    public String callback(String code,String state){


        try {
            //获取code,类似于验证码
            //拿到code去请求微信固定的地址，得到的两个值去请求 access_token和 openid
            String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String url = String.format(
                    baseUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个地址 使用httpClient
            String accessTokenInfo = HttpClientUtils.get(url);

            //字符串转 json
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token =(String) map.get("access_token");
            String openid = (String) map.get("openid");


            UcenterMember member = memberService.getOpenIdMember(openid);
            System.out.println("查询的数据为："+member);
            if(member == null){//表里没有 就添加
                //拿着信息再去请求固定地址用户信息
                String BaseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String UserInfoUrl =  String.format(
                        BaseUserInfoUrl,
                        access_token,
                        openid
                    );
                //请求这个地址 使用httpClient
                String userInfo = HttpClientUtils.get(UserInfoUrl);
                //获取到用户信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                //添加到数据库
                //判断数据库是否存在相同的微信信息
                //根据openid判断
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                //保存
                memberService.save(member);
            }

            //把memeber信息生成token
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //拼接到后面
            return "redirect:http://localhost:3000?token="+token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliExecption(20001,"登陆失败");
        }

    }
}
