package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName city_info
 */
@TableName(value ="city_info")
@Data
public class CityInfo extends BaseEntity{

    private String name;

    private Integer provinceId;

}