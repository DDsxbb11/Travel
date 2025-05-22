package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.AttractionInfo;
import com.travel.web.front.dto.attraction.AttractionDTO;
import com.travel.web.front.vo.attraction.AttractionVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【attraction_info(景点信息)】的数据库操作Mapper
* @createDate 2025-03-14 22:46:38
* @Entity com.travel.model.pojo.AttractionInfo
*/
public interface AttractionInfoMapper extends BaseMapper<AttractionInfo> {

    IPage<AttractionVo> getAttractionInfoPage(IPage<AttractionVo> page,@Param("attractionDto") AttractionDTO attractionDto);
}




