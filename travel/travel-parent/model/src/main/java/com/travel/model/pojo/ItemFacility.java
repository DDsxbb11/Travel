package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName item_facility
 */
@TableName(value ="item_facility")
@Data
public class ItemFacility extends BaseEntity{
    private Long itemId;

    private Long facilityId;

    private String type;

    private Integer sort;

}