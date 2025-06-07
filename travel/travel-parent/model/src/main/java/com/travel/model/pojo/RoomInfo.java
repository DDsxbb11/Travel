package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName room_info
 */
@TableName(value ="room_info")
@Data
public class RoomInfo extends BaseEntity{
    private Long hotelId;

    private String name;

    private String imgUrl;

    private BigDecimal price;

    private Integer number;

    private Integer status;

}