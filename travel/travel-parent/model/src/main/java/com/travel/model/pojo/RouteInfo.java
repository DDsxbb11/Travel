package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName route_info
 */
@TableName(value ="route_info")
@Data
public class RouteInfo extends BaseEntity{

    private Long itemId;

    private String type;

    private String title;

    private String description;

    private String imgUrl;

    private Integer sort;
}