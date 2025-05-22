package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName attraction_info
 */
@TableName(value ="attraction_info")
@Data
public class AttractionInfo extends BaseEntity{
    private String name;

    private String theme;

    private String province;

    private String city;

    private String address;

    private String openTime;

    private Integer isFee;

    private BigDecimal childrenPrice;

    private BigDecimal adultPrice;

    private String longitude;

    private String latitude;

    private BigDecimal rating;

    private String description;

    private String trafficDesc;

}