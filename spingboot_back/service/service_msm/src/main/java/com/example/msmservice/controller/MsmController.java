package com.example.msmservice.controller;


import com.example.commonutils.R;
import com.example.msmservice.service.MsmService;
import com.example.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 短信发送
     */
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){

        /**
         * 先从redis里面取,没有自动生成，有的话获取
         */
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //生成验证码随机数
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);

        boolean isSend = msmService.send(param,phone);
        if(isSend){
            //发送成功,把发送成功的验证码放到redis
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);//五分钟
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }
}
