package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @TableName hotel_order_info
 */
@TableName(value ="hotel_order_info")
@Data
public class HotelOrderInfo extends BaseEntity{
    private Long hotelId;

    private Long roomId;

    private Long userId;

    private Integer number;

    private String paymentType;

    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;

    private Integer state;
    private String note;

}