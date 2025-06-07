package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.AttractionInfo;
import com.travel.web.admin.dto.attraction.AttractionDTO;
import com.travel.web.admin.dto.attraction.AttractionInfoDTO;

/**
* @author 15101
* @description 针对表【attraction_info(景点信息)】的数据库操作Service
* @createDate 2025-03-14 22:46:38
*/
public interface AttractionInfoService extends IService<AttractionInfo> {

    IPage<AttractionInfo> getAttractionInfoPage(IPage<AttractionInfo> page, AttractionDTO dto);

    void saveOrUpdateAttraction(AttractionInfoDTO dto);

    AttractionInfoDTO getAttractionInfoById(Long id);
}
