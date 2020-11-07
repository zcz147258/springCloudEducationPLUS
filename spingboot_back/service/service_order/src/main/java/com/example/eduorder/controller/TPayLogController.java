package com.example.eduorder.controller;


import com.example.commonutils.R;
import com.example.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author mikasa
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    /**
     * 生成微信支付二维码
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //包含二维码得地址，还有其他信息
        Map<String,Object> map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println(map);
        if(map == null){
            return R.error().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            //添加支付记录到支付表
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(20005).message("支付中");
    }

}

