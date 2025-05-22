package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName record_info
 */
@TableName(value ="record_info")
@Data
public class RecordInfo extends BaseEntity{
    private Long authorId;

    private String name;

    private String imgUrl;

    private Integer like;
}