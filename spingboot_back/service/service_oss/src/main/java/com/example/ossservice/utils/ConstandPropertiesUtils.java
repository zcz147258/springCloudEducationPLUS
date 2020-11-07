package com.example.ossservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstandPropertiesUtils implements InitializingBean {
    //读取配置文件

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecrect;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;


    //定义公开静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRECT;
    public static String BUCKET_NAME;

    /**
     * 赋值完毕会执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRECT = keySecrect;
        BUCKET_NAME = bucketname;
    }
}
