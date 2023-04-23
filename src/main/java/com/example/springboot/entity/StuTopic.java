package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lcx
 * @since 2023-04-23
 */
@Getter
@Setter
@TableName("stu_topic")
@ApiModel(value = "StuTopic对象", description = "")
public class StuTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学生选题id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生id")
    private String stuId;

    @ApiModelProperty("题目id")
    private Integer topicId;

    @ApiModelProperty("评分")
    private Integer score;

    private Integer state;


}
