package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName strategy_lable
 */
@TableName(value ="strategy_lable")
@Data
public class StrategyLable extends BaseEntity{
    private Long strategyId;

    private Long lableId;

    private Integer sort;

}