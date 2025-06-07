package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.model.pojo.enums.ItemEnum;
import com.travel.model.pojo.enums.PictureTypeEnum;
import com.travel.model.pojo.AttractionImg;
import com.travel.model.pojo.AttractionInfo;
import com.travel.web.front.dto.attraction.AttractionDTO;
import com.travel.web.front.mapper.AttractionImgMapper;
import com.travel.web.front.service.CollectionsInfoService;
import com.travel.web.front.vo.attraction.AttractionDetailVo;
import com.travel.web.front.mapper.AttractionInfoMapper;
import com.travel.web.front.service.AttractionInfoService;
import com.travel.web.front.vo.attraction.AttractionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 15101
* @description 针对表【attraction_info(景点信息)】的数据库操作Service实现
* @createDate 2025-03-14 22:46:38
*/
@Service
public class AttractionInfoServiceImpl extends ServiceImpl<AttractionInfoMapper, AttractionInfo>
    implements AttractionInfoService {

    @Autowired
    private AttractionInfoMapper attractionInfoMapper;
    @Autowired
    private AttractionImgMapper attractionImageMapper;
    @Autowired
    private CollectionsInfoService collectionsInfoService;

    /**
     * 分页获取景点信息
     */
    @Override
    public IPage<AttractionVo> getAttractionInfoPage(IPage<AttractionVo> page, AttractionDTO attractionDTO) {
        return attractionInfoMapper.getAttractionInfoPage(page,attractionDTO);
    }

    /**
     * 获取景点详情
     */
    @Override
    public AttractionDetailVo getAttractionDetail(String token,Long id) {
        AttractionDetailVo vo = new AttractionDetailVo();
        AttractionInfo attractionInfo = attractionInfoMapper.selectById(id);
        if (attractionInfo==null){
            throw new GlobalException(ResultCodeEnum.DATA_ERROR);
        }
        BeanUtils.copyProperties(attractionInfo,vo);
        //获取图片
        LambdaQueryWrapper<AttractionImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttractionImg::getAttractionId,id)
                .in(AttractionImg::getType, PictureTypeEnum.HEADER.getCode(),PictureTypeEnum.SIMPLE.getCode());
        List<AttractionImg> pictureInfoList = attractionImageMapper.selectList(queryWrapper);
        vo.setPictureList(pictureInfoList);
        //获取收藏ID
        if (StringUtils.hasText(token)){
            vo.setIsCollect(collectionsInfoService.isCollect(token,id, ItemEnum.ATTRACTION.getLabel()));
        }
        return vo;
    }
}




