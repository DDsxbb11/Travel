package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName hotel_info
 */
@TableName(value ="hotel_info")
@Data
public class HotelInfo extends BaseEntity{
    private String name;

    private String imgUrl;

    private String level;

    private String province;

    private String city;

    private String district;

    private String address;

    private String longitude;

    private String latitude;

    private String description;

    private BigDecimal rating;

    private String openTime;

    private Integer roomNumber;

    private String phone;

    private String trafficDesc;
}