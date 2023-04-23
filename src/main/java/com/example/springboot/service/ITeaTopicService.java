package com.example.springboot.service;

import com.example.springboot.controller.dto.TeaTopicDTO;
import com.example.springboot.entity.TeaTopic;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lcx
 * @since 2023-04-24
 */
public interface ITeaTopicService extends IService<TeaTopic> {
    /**
     * 根据活动id查询当前活动的拟题申请记录
     * @param actId
     * @return
     */
    List<TeaTopicDTO> getApplyByActId(Integer actId);

}
