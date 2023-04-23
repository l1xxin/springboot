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
@TableName("stu_task")
@ApiModel(value = "StuTask对象", description = "")
public class StuTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学生选题任务id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生选题id")
    private Integer stuTopicId;

    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("任务文件")
    private String file;

    @ApiModelProperty("审核状态")
    private Integer status;

    @ApiModelProperty("指导意见")
    private String comment;

    private Integer state;


}
