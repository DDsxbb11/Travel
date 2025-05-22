package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.AttractionInfo;
import com.travel.web.front.dto.attraction.AttractionDTO;
import com.travel.web.front.vo.attraction.AttractionDetailVo;
import com.travel.web.front.vo.attraction.AttractionVo;

/**
* @author 15101
* @description 针对表【attraction_info(景点信息)】的数据库操作Service
* @createDate 2025-03-14 22:46:38
*/
public interface AttractionInfoService extends IService<AttractionInfo> {

    /**
     * 分页获取景点信息
     */
    IPage<AttractionVo> getAttractionInfoPage(IPage<AttractionVo> page, AttractionDTO from);

    /**
     * 获取景点详情
     */
    AttractionDetailVo getAttractionDetail(String token,Long id);
}
