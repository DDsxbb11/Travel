package com.travel.web.admin.vo.record;

import com.travel.model.pojo.RecordInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 19:11
 */
@Data
public class RecordVo extends RecordInfo {
    private String username;
    private String nickname;
    private String phone;
    private String description;
}
