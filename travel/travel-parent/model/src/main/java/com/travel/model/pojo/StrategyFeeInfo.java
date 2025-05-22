package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName strategy_fee_info
 */
@TableName(value ="strategy_fee_info")
@Data
public class StrategyFeeInfo extends BaseEntity{
    private Long strategyId;

    private String title;

    private String description;

    private Integer sort;
}