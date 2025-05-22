package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.model.enums.FacilityTypeEnum;
import com.travel.model.enums.ItemEnum;
import com.travel.model.pojo.*;
import com.travel.web.front.dto.hotel.HotelDTO;
import com.travel.web.front.mapper.*;
import com.travel.web.front.service.CollectionsInfoService;
import com.travel.web.front.service.HotelInfoService;
import com.travel.web.front.vo.hotel.HotelDetailVo;
import com.travel.web.front.vo.hotel.HotelVo;
import com.travel.web.front.vo.hotel.RoomVo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* @author 15101
* @description 针对表【hotel_info(酒店信息)】的数据库操作Service实现
* @createDate 2025-03-15 22:16:56
*/
@Service
public class HotelInfoServiceImpl extends ServiceImpl<HotelInfoMapper, HotelInfo>
    implements HotelInfoService {

    @Autowired
    private HotelInfoMapper hotelInfoMapper;
    @Autowired
    private PolicyInfoMapper policyInfoMapper;
    @Autowired
    private ItemFacilityMapper itemFacilityMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private CollectionsInfoService collectionsInfoService;
    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void init(){
        threadPoolExecutor= new ThreadPoolExecutor(
                9, // 核心线程数
                17, // 最大线程数
                60L, // 空闲线程存活时间
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>(10), // 任务队列，最大容量为 10
                Executors.defaultThreadFactory(), // 线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );

    }
    /**
     * 分页查询酒店信息
     */
    @Override
    public IPage<HotelVo> getPage(IPage<HotelVo> page, HotelDTO hotelDTO) {
        return hotelInfoMapper.getPage(page,hotelDTO);
    }

    /**
     * 根据id查询酒店信息
     */
    @Override
    public HotelDetailVo getHotelById(Long id,String token) {
        HotelDetailVo vo = new HotelDetailVo();
        HotelInfo hotelInfo = hotelInfoMapper.selectById(id);
        if (hotelInfo==null){
            throw new GlobalException(ResultCodeEnum.DATA_ERROR);
        }
        BeanUtils.copyProperties(hotelInfo,vo);
        //查询房间
        List<RoomVo> roomVoList=roomInfoMapper.selectRoomList(id);
        vo.setRoomList(roomVoList);
        //查询政策
        List<PolicyInfo> policyInfoList = policyInfoMapper.selectList(
                                                     new LambdaQueryWrapper<PolicyInfo>()
                                                             .eq(PolicyInfo::getItemId, id)
                                                             .orderByDesc(PolicyInfo::getSort));
        vo.setPolicyList(policyInfoList);
        //查询酒店设施
        List<FacilityInfo> facilityList=itemFacilityMapper
                .getItemFacility(id, FacilityTypeEnum.HOTEL_FACILITY.getType());
        vo.setFacilityList(facilityList);
        //是否收藏
        if (StringUtils.hasText(token)){
            vo.setCollect(collectionsInfoService.isCollect(token,id, ItemEnum.HOTEL.getLabel()));
        }
        return vo;
    }
}




