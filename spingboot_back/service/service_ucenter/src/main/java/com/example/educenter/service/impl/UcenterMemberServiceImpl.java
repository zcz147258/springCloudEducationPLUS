package com.example.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtUtils;
import com.example.commonutils.MD5;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.entity.vo.RegisterVo;
import com.example.educenter.mapper.UcenterMemberMapper;
import com.example.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.servicebase.exception.GuliExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author mikasa
 * @since 2020-11-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     * 登录
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliExecption(20001,"手机号密码不能为空");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断是否为空
        if(mobileMember == null){
            throw new GuliExecption(20001,"手机号不存在");
        }

        //判断密码  加密
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliExecption(20001,"密码错误");
        }

        //判断被禁用
        if(mobileMember.getIsDisabled() == 1){
            throw new   GuliExecption(20003,"用户被禁用");
        }
        //生成token
        String token = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)){
            throw new GuliExecption(20001,"用户名密码验证码昵称不能为空");
        }

        //判断验证码是否正确
        //和redis里面的验证码是否一样
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
           throw new GuliExecption(20001,"验证码错误");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliExecption(20001,"手机号已经注册");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(0);
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
