package com.travel.web.front.dto.user;

import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO: 修改密码
 * @date: 2025-05-05 12:23
 */
@Data
public class ChangePasswordDTO {

    private String currentPassword;

    private String newPassword;
}
