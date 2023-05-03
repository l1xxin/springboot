package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author lcx
 * @since 2023-05-03
 */
@Getter
@Setter
@TableName("task_view")
@ApiModel(value = "TaskView对象", description = "VIEW")
public class TaskView implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("任务文件")
    private String file;

    @ApiModelProperty("任务描述")
    private String des;

    @ApiModelProperty("审核状态")
    private Integer status;

    @ApiModelProperty("题目id")
    private Integer topicId;

    @ApiModelProperty("题目名称")
    private String topicName;

    @ApiModelProperty("申报人id")
    private String teaId;

    @ApiModelProperty("昵称")
    private String teaName;

    @ApiModelProperty("活动id")
    private Integer actId;

    @ApiModelProperty("活动名称")
    private String actName;

    @ApiModelProperty("学生id")
    private String stuId;

    @ApiModelProperty("昵称")
    private String stuName;


}
