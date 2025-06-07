package com.travel.web.admin.dto.facility;

import com.travel.web.admin.dto.BasePageDTO;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-01 13:17
 */
@Data
public class FacilityDTO extends BasePageDTO {
    private String name;
    private String type;
}
