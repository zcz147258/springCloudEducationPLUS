package com.example.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtUtils;
import com.example.commonutils.R;
import com.example.eduorder.entity.TOrder;
import com.example.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author mikasa
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    TOrderService tOrderService;

    /**
     * 生成订单方法
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){

        //生成订单号
        String orderNo = tOrderService.createOrder(courseId,JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据订单号查询订单信息
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R  getOrderInfo(@PathVariable String orderId){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = tOrderService.getOne(wrapper);

        return R.ok().data("item",order);
    }

    /**
     * 根据课程id和用户id查询订单表中的订单状态
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = tOrderService.count(wrapper);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }
}

