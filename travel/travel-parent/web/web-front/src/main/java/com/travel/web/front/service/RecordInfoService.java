package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.RecordInfo;
import com.travel.web.front.dto.record.RecordDTO;
import com.travel.web.front.vo.record.RecordDetailVo;
import com.travel.web.front.vo.record.RecordVo;

/**
* @author 15101
* @description 针对表【record_info(景点信息)】的数据库操作Service
* @createDate 2025-03-17 00:18:45
*/
public interface RecordInfoService extends IService<RecordInfo> {

    /**
     * 分页查询日记
     */
    IPage<RecordVo> getPage(IPage<RecordVo> page, RecordDTO recordDTO, String token);

    /**
     * 查询日记详情
     */
    RecordDetailVo getRecordDetailById(Long id,String token);
}
