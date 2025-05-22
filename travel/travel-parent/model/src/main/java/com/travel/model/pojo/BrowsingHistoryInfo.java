package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName browsing_history_info
 */
@TableName(value ="browsing_history_info")
@Data
public class BrowsingHistoryInfo extends BaseEntity{
    private Long userId;

    private Long itemId;

    private String itemType;

    private String itemName;

    private String itemImg;

    private String itemDesc;
}