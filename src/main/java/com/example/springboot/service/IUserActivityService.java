package com.example.springboot.service;

import com.example.springboot.controller.dto.ActUserDTO;
import com.example.springboot.entity.UserActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lcx
 * @since 2023-04-23
 */
public interface IUserActivityService extends IService<UserActivity> {
    /**
     * 根据活动id获取学生用户列表
     * @param actId
     * @return
     */
    List<ActUserDTO> getUnselectedStuByActId(Integer actId);

    /**
     * 根据活动id获取未选活动老师用户列表
     * @param actId
     * @return
     */
    List<ActUserDTO> getUnselectedTeaByActId(Integer actId);
}
