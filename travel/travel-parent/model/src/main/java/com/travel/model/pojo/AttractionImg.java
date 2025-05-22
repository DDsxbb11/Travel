package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName attraction_img
 */
@TableName(value ="attraction_img")
@Data
public class AttractionImg extends BaseEntity{

    private String name;

    private Integer attractionId;

    private String type;

    private String url;

}