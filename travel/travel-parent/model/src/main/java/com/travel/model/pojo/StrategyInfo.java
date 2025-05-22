package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName strategy_info
 */
@TableName(value ="strategy_info")
@Data
public class StrategyInfo extends BaseEntity{

    private String name;

    private String province;

    private String address;

    private String imgUrl;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal price;

    private Integer sale;

    private BigDecimal rating;
}