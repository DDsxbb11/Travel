package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.enums.FacilityTypeEnum;
import com.travel.model.pojo.HotelInfo;
import com.travel.model.pojo.ItemFacility;
import com.travel.model.pojo.PolicyInfo;
import com.travel.web.admin.dto.hotel.HotelDTO;
import com.travel.web.admin.mapper.HotelInfoMapper;
import com.travel.web.admin.mapper.ItemFacilityMapper;
import com.travel.web.admin.service.HotelInfoService;
import com.travel.web.admin.service.ItemFacilityService;
import com.travel.web.admin.service.PolicyInfoService;
import com.travel.web.admin.vo.hotel.HotelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @author 15101
* @description 针对表【hotel_info(酒店信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class HotelInfoServiceImpl extends ServiceImpl<HotelInfoMapper, HotelInfo>
    implements HotelInfoService {

    @Autowired
    private ItemFacilityService itemFacilityService;
    @Autowired
    private PolicyInfoService policyInfoService;

    @Override
    public IPage<HotelInfo> getHotelInfoPage(IPage<HotelInfo> page, HotelDTO dto) {
        LambdaQueryWrapper<HotelInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (dto != null) {
            queryWrapper.eq(dto.getProvince() != null && StringUtils.hasText(dto.getProvince()), HotelInfo::getProvince, dto.getProvince())
                    .eq(dto.getCity() != null && StringUtils.hasText(dto.getCity()), HotelInfo::getCity, dto.getCity())
                    .eq(dto.getDistrict() != null && StringUtils.hasText(dto.getDistrict()), HotelInfo::getDistrict, dto.getDistrict())
                    .eq(dto.getLevel() != null && StringUtils.hasText(dto.getLevel()), HotelInfo::getLevel, dto.getLevel())
                    .like(dto.getName() != null && StringUtils.hasText(dto.getName()), HotelInfo::getName, dto.getName());
        }
        return this.page(page, queryWrapper);
    }

    @Override
    public HotelVo getHotelInfoById(Long id) {
        HotelVo vo = new HotelVo();
        HotelInfo info = this.getById(id);
        BeanUtils.copyProperties(info, vo);
        //查询酒店设施
        List<Long> facilityIds = itemFacilityService.getFacilityIdListByTypeAndItemId(id,"hotel",FacilityTypeEnum.HOTEL_FACILITY.getType());
        vo.setFailityList(facilityIds);
        //查询酒店政策
        LambdaQueryWrapper<PolicyInfo> policyInfoQueryWrapper = new LambdaQueryWrapper<>();
        policyInfoQueryWrapper.eq(PolicyInfo::getItemId, id)
                .orderByDesc(PolicyInfo::getSort);
        List<PolicyInfo> policyInfoList = policyInfoService.list(policyInfoQueryWrapper);
        vo.setPolicyInfoList(policyInfoList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateHotelInfo(HotelVo vo) {
        HotelInfo info = new HotelInfo();
        BeanUtils.copyProperties(vo, info);
        this.saveOrUpdate(info);
        itemFacilityService.saveOrUpdateFacility(info.getId(),vo.getId(),vo.getFailityList(),"hotel",FacilityTypeEnum.ROOM_FOOD_FACILITY.getType());
    }
}




