package com.example.springboot.controller.dto;
import com.example.springboot.entity.Menu;
import lombok.Data;

import java.util.List;
/**
 * @author lcx
 * @Description: 用于教务为活动添加用户
 * @date 2023/4/23 20:40
 */

@Data
public class ActUserDTO {
    //学号或工号
    private String num;
    //名字
    private String name;
}
