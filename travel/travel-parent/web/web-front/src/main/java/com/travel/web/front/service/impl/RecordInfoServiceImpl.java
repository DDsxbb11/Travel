package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.RouteInfo;
import com.travel.model.pojo.enums.ItemEnum;
import com.travel.model.pojo.RecordInfo;
import com.travel.model.pojo.enums.RouteTypeEnum;
import com.travel.web.front.dto.record.RecordDTO;
import com.travel.web.front.dto.record.RecordSavaDTO;
import com.travel.web.front.mapper.RecordInfoMapper;
import com.travel.web.front.service.CollectionsInfoService;
import com.travel.web.front.service.RecordInfoService;
import com.travel.web.front.service.RouteInfoService;
import com.travel.web.front.vo.record.RecordDetailVo;
import com.travel.web.front.vo.record.RecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 15101
* @description 针对表【record_info(景点信息)】的数据库操作Service实现
* @createDate 2025-03-17 00:18:45
*/
@Service
public class RecordInfoServiceImpl extends ServiceImpl<RecordInfoMapper, RecordInfo>
    implements RecordInfoService {

    @Autowired
    private RecordInfoMapper recordInfoMapper;
    @Autowired
    private CollectionsInfoService collectionsInfoService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RouteInfoService routeInfoService;

    /**
     * 分页查询日记
     */
    @Override
    public IPage<RecordVo> getPage(IPage<RecordVo> page, RecordDTO recordDTO, String token) {
        return recordInfoMapper.getPage(page, recordDTO, jwtUtil.getUserId(token));
    }

    /**
     * 查询日记详情
     */
    @Override
    public RecordDetailVo getRecordDetailById(Long id, String token) {
        RecordDetailVo vo = recordInfoMapper.getRecordDetailById(id);
        if (vo != null && StringUtils.hasText(token)) {
            vo.setIsCollect(collectionsInfoService.isCollect(token,id, ItemEnum.RECORD.getLabel()));
        }
        return vo;
    }

    @Override
    public void saveRecord(RecordSavaDTO dto, String token) {
        dto.setAuthorId(jwtUtil.getUserId(token));
        this.save(dto);
        //报cun路线
        dto.getRouteList().forEach(item->{
            item.setItemId(dto.getId());
            item.setType(RouteTypeEnum.RECORD.getCode());
            routeInfoService.save(item);
        });
    }
}




