package com.example.springboot.controller.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author lcx
 * @Description:教务人员添加用户到活动 新增用户活动表记录的辅助工具
 * @date 2023/4/24 0:19
 */
@Data
public class AddUserActDTO {
    //学号或工号
    private String userId;
    //名字
    private Integer actId;
}
