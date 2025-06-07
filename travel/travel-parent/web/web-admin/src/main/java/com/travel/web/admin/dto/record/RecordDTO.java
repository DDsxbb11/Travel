package com.travel.web.admin.dto.record;

import com.travel.web.admin.dto.BasePageDTO;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 19:21
 */
@Data
public class RecordDTO extends BasePageDTO {
    private String username;
    private String recordName;
    private String phone;
}
