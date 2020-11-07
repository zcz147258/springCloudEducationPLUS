package com.example.eduorder.service.impl;

import com.example.commonutils.ordervo.CourseWebVoOrder;
import com.example.commonutils.ordervo.UcenterMemberOrder;
import com.example.eduorder.client.EduClient;
import com.example.eduorder.client.UcenterClient;
import com.example.eduorder.entity.TOrder;
import com.example.eduorder.mapper.TOrderMapper;
import com.example.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author mikasa
 * @since 2020-11-04
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    EduClient eduClient;

    @Autowired
    UcenterClient ucenterClient;

    /**
     * 生成订单得方法
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过用户id调用获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);

        //通过课程id远程调用获取课程信息
        CourseWebVoOrder courseInfo = eduClient.getCourseInfo(courseId);

        //添加到数据库
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);//支付状态 未支付
        order.setPayType(1);//支付类型 微信
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
