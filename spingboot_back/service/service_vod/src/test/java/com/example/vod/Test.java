package com.example.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class Test {
    public static void main(String[] args){
        String accessKeyId = "LTAI4GDP1QpdyiQ2eEeephUU";
        String accessKeySecret = "ySUVBLTOZJfVwa8zRUVAEL7UE63C35";
        String title = "测试视频";
        String fileName = "G:\\学习视频\\python\\001.Python介绍_特性_版本问题_应用范围.mp4";
        //上传视频
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }


    //根据视频id获取播放凭证
    public static void getPlayAuth() throws ClientException {
        //初始化对象
        DefaultAcsClient client =  InitObject.initVodClient("LTAI4GDP1QpdyiQ2eEeephUU","");
        //获取视频凭证response和request
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //设置id
        request.setVideoId("6d7b130bf04d4d48a8e7ce98e16a07d8");
        //得到视频凭证
        response = client.getAcsResponse(request);
        System.out.println("response:"+response.getPlayAuth());
    }

    //根据视频id获取视频播放地址
    public static void getPlayUrlByID() throws ClientException{
        //初始化
        DefaultAcsClient client =  InitObject.initVodClient("LTAI4GDP1QpdyiQ2eEeephUU","ySUVBLTOZJfVwa8zRUVAEL7UE63C35");
        //创建request reponse
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request中设置视频id
        request.setVideoId("6d7b130bf04d4d48a8e7ce98e16a07d8");
        //调用初始化对象里面的方法 传递request,获取数据
        response = client.getAcsResponse(request);

        //播放列表
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo  playInfo : playInfoList){
            //播放地址
            System.out.println("PlayInfoList =" + playInfo.getPlayURL());
        }
        //BaSE信息
        System.out.println("VideoBase.TiTle :" + response.getVideoBase().getTitle());
    }
}
