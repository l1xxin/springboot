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

    List<ActUserDTO> getUnselectedUserByActId(Integer actId);

}
