package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName strategy_route
 */
@TableName(value ="strategy_route")
@Data
public class StrategyRoute extends BaseEntity{
    private Long strategyId;

    private Long routeId;

    private Integer sort;
}