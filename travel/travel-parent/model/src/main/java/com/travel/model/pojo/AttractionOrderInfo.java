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
 * @TableName attraction_order_info
 */
@TableName(value ="attraction_order_info")
@Data
public class AttractionOrderInfo extends BaseEntity{

    private Long attractionId;

    private Long userId;

    private Integer childrenNumber;

    private Integer adultNumber;

    private BigDecimal totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date appointmentTime;

    private String paymentType;

    private Integer state;

    private String note;

}