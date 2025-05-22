package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName policy_info
 */
@TableName(value ="policy_info")
@Data
public class PolicyInfo extends BaseEntity{
    private Long itemId;

    private String title;

    private String content;

    private Integer sort;

}