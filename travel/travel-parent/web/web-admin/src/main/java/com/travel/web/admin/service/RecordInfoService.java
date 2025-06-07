package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.RecordInfo;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.admin.dto.record.RecordDTO;
import com.travel.web.admin.vo.record.RecordVo;

/**
* @author 15101
* @description 针对表【record_info(景点信息)】的数据库操作Service
* @createDate 2025-03-17 00:18:45
*/
public interface RecordInfoService extends IService<RecordInfo> {

    IPage<RecordVo> getRecordInfoByPage(IPage<RecordVo> page, RecordDTO dto);
}
