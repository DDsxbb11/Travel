package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.LableInfo;
import com.travel.web.admin.dto.strategy.LableDTO;

/**
* @author 15101
* @description 针对表【lable_info(攻略标签)】的数据库操作Service
* @createDate 2025-03-16 17:11:18
*/
public interface LableInfoService extends IService<LableInfo> {

    IPage<LableInfo> getLableInfoPage(IPage<LableInfo> page, LableDTO dto);
}
