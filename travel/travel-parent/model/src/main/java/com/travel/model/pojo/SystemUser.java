package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName system_user
 */
@TableName(value ="system_user")
@Data
public class SystemUser extends BaseEntity{

    private String nickname;

    private String username;

    private String password;

    private String gender;

    private String header;

    private String phone;

    private String idcard;

    private Integer status;

}