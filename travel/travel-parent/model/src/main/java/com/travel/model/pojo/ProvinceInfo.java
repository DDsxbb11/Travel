package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName province_info
 */
@TableName(value ="province_info")
@Data
public class ProvinceInfo extends BaseEntity{

    private String name;

    private Long countryId;

}