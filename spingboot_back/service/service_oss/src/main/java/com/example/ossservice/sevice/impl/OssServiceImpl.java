package com.example.ossservice.sevice.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.ossservice.sevice.OssService;
import com.example.ossservice.utils.ConstandPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传头像到service
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstandPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstandPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.KEY_SECRECT;
        String bucketname = ConstandPropertiesUtils.BUCKET_NAME;
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            // InputStream inputStream = new FileInputStream("<yourlocalFile>");
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String Filename = file.getOriginalFilename();
            //让文件唯一
            String uuid = java.util.UUID.randomUUID().toString().replaceAll("-","");
            Filename = uuid + Filename;

            //获取当前日期
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            Filename = dataPath + "/" + Filename;

            /**
             * 参数1  buckname
             * 参数2  上传到oss文件路径
             */

            ossClient.putObject(bucketname, Filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            String url = "https://" + bucketname + "." + endpoint + "/" + Filename;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
