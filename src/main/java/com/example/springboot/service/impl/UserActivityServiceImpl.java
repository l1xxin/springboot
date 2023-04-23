package com.example.springboot.service.impl;

import cn.hutool.log.Log;
import com.example.springboot.controller.dto.ActUserDTO;
import com.example.springboot.entity.UserActivity;
import com.example.springboot.mapper.UserActivityMapper;
import com.example.springboot.service.IUserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcx
 * @since 2023-04-23
 */
@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements IUserActivityService {
    private static final Log LOG = Log.get();

    @Resource
    private UserActivityMapper userActivityMapper;

    /**
     * 用于查询未选择某个活动的学生用户
     * @param actId
     * @return
     */
    @Override
    public List<ActUserDTO> getUnselectedUserByActId(Integer actId){
        List<ActUserDTO> Users = userActivityMapper.selectByActId(actId);
        return Users;
    }
}
