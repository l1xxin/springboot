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
@TableName("activity_topic")
@ApiModel(value = "ActivityTopic对象", description = "")
public class ActivityTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("topic_id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("活动id")
    private Integer actId;

    private Integer state;


}
