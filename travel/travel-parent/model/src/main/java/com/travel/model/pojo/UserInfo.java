package com.travel.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo extends BaseEntity{
    private String username;
    private String phone;

    private String password;

    private String avatarUrl;

    private String nickname;

    private String email;

    private Integer status;
}