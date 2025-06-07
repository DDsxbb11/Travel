package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.enums.PictureTypeEnum;
import com.travel.model.pojo.AttractionImg;
import com.travel.model.pojo.AttractionInfo;
import com.travel.web.admin.dto.attraction.AttractionDTO;
import com.travel.web.admin.dto.attraction.AttractionInfoDTO;
import com.travel.web.admin.mapper.AttractionImgMapper;
import com.travel.web.admin.mapper.AttractionInfoMapper;
import com.travel.web.admin.service.AttractionInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 15101
 * @description 针对表【attraction_info(景点信息)】的数据库操作Service实现
 * @createDate 2025-04-14 19:25:36
 */
@Service
public class AttractionInfoServiceImpl extends ServiceImpl<AttractionInfoMapper, AttractionInfo>
        implements AttractionInfoService {

    @Autowired
    private AttractionImgMapper attractionImgMapper;

    @Override
    public IPage<AttractionInfo> getAttractionInfoPage(IPage<AttractionInfo> page, AttractionDTO dto) {
        LambdaQueryWrapper<AttractionInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (dto != null) {
            queryWrapper.eq(dto.getProvince() != null && StringUtils.hasText(dto.getProvince()), AttractionInfo::getProvince, dto.getProvince())
                    .eq(dto.getCity() != null && StringUtils.hasText(dto.getCity()), AttractionInfo::getCity, dto.getCity())
                    .eq(dto.getTheme() != null && StringUtils.hasText(dto.getTheme()), AttractionInfo::getTheme, dto.getTheme())
                    .like(dto.getName() != null && StringUtils.hasText(dto.getName()), AttractionInfo::getName, dto.getName());
        }
        queryWrapper.orderByDesc(AttractionInfo::getCreateTime);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateAttraction(AttractionInfoDTO dto) {
        AttractionInfo info = new AttractionInfo();
        BeanUtils.copyProperties(dto, info);
        this.saveOrUpdate(info);
        //待保存图片
        AtomicInteger flag = new AtomicInteger(0);
        dto.getImageList().forEach(item -> {
            LambdaQueryWrapper<AttractionImg> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(AttractionImg::getAttractionId, dto.getId());
            attractionImgMapper.delete(deleteWrapper);
            item.setId(null);
            item.setAttractionId(info.getId());
            item.setName(info.getName());
            item.setType(flag.get()==0 ? PictureTypeEnum.HEADER.getCode() : PictureTypeEnum.SIMPLE.getCode());
            flag.getAndAdd(1);
            attractionImgMapper.insert(item);
        });
    }

    @Override
    public AttractionInfoDTO getAttractionInfoById(Long id) {
        AttractionInfoDTO dto = new AttractionInfoDTO();
        AttractionInfo info = this.getById(id);
        BeanUtils.copyProperties(info, dto);
        LambdaQueryWrapper<AttractionImg> queryWrapper = new LambdaQueryWrapper<AttractionImg>().
                eq(AttractionImg::getAttractionId, id);
        List<AttractionImg> attractionImgList = attractionImgMapper.selectList(queryWrapper);
        dto.setImageList(attractionImgList);
        return dto;
    }
}




