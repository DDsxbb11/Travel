package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName collections_info
 */
@TableName(value ="collections_info")
@Data
public class CollectionsInfo extends BaseEntity{
    private Long userId;

    private Long itemId;

    private String itemType;

    private String itemName;

    private String itemImg;

    private String itemDesc;

    private Date collectionsTime;
}