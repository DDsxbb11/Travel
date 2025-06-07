package com.travel.web.admin.dto.tourist;

import com.travel.web.admin.dto.BasePageDTO;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-31 17:12
 */
@Data
public class TouristDTO extends BasePageDTO {
    private String username;
    private String phone;
}
