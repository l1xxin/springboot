package com.example.springboot.service.impl;

import cn.hutool.log.Log;
import com.example.springboot.controller.dto.ActUserDTO;
import com.example.springboot.controller.dto.MyActDTO;
import com.example.springboot.entity.User;
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
    public List<ActUserDTO> getUnselectedStuByActId(Integer actId){
        List<ActUserDTO> Users = userActivityMapper.selectStuByActId(actId);
        return Users;
    }
    /**
     * 用于查询未选择某个活动的教师用户
     * @param actId
     * @return
     */
    @Override
    public List<ActUserDTO> getUnselectedTeaByActId(Integer actId) {
        List<ActUserDTO> users = userActivityMapper.selectTeaByActId(actId);
        return users;
    }

    @Override
    public List<MyActDTO> getMyActByUser(String userId) {
        List<MyActDTO> acts = userActivityMapper.selectMyActByUser(userId);
        return acts;
    }


}
