package com.example.springboot.controller.dto;

import lombok.Data;

/**
 * @author lcx
 * @Description: 根据活动id查询当前活动的拟题申请记录
 * @date 2023/4/24 4:09
 */
@Data
public class TeaTopicDTO {
    /*
    SELECT tea_topic.id as id,tea_topic.name as name,tea_topic.des as des,
tea_topic.file as file,sys_user.nickname as nickname,tea_topic.tea_id as teaId,
tea_topic.`status` as status
FROM tea_topic,sys_user
WHERE tea_topic.tea_id = sys_user.username AND tea_topic.act_id = '1'
ORDER BY id

     */
    //拟题申报id
    private Integer id;
    //题目名称
    private String name;
    //题目描述
    private String des;
    //任务书
    private String file;
    //申报人名字
    private String nickname;
    //申报人id
    private String teaId;
    //审核状态
    private int status;
}
