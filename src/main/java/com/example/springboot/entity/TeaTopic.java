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
 * @since 2023-04-24
 */
@Getter
@Setter
@TableName("tea_topic")
@ApiModel(value = "TeaTopic对象", description = "")
public class TeaTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("拟题id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("申报人id")
    private String teaId;

    @ApiModelProperty("活动id")
    private Integer actId;

    @ApiModelProperty("题目名称")
    private String name;

    @ApiModelProperty("题目描述")
    private String des;

    @ApiModelProperty("任务书")
    private String file;

    @ApiModelProperty("审批状态")
    private Integer status;

    private Integer state;


}
