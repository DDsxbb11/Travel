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
 * @TableName strategy_order_info
 */
@TableName(value ="strategy_order_info")
@Data
public class StrategyOrderInfo extends BaseEntity{
    private Long strategyId;

    private Long userId;

    private Integer number;

    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date appointmentTime;

    private String paymentType;
    private Integer state;

    private String note;
}