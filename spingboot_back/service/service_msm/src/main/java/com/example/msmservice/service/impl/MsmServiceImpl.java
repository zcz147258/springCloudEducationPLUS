package com.example.msmservice.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.example.msmservice.service.MsmService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile = DefaultProfile.getProfile("default","LTAI4GDP1QpdyiQ2eEeephUU","ySUVBLTOZJfVwa8zRUVAEL7UE63C35");

        IAcsClient cilent = new DefaultAcsClient(profile);

        //设置相关固定参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");


        //设置参数
        request.putQueryParameter("PhoneNumbers",phone);//手机号
        request.putQueryParameter("SignName","古粒在线教育网站");//签名
        request.putQueryParameter("TemplateCode","SMS_205392318");//模板
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//code json

        //最终发送
        try {
            CommonResponse response = cilent.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
