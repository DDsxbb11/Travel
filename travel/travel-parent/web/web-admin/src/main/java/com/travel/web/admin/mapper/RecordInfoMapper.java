package com.travel.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.RecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.admin.dto.record.RecordDTO;
import com.travel.web.admin.vo.record.RecordVo;

/**
* @author 15101
* @description 针对表【record_info(景点信息)】的数据库操作Mapper
* @createDate 2025-05-27 18:34:04
* @Entity com.travel.model.pojo.RecordInfo
*/
public interface RecordInfoMapper extends BaseMapper<RecordInfo> {

    IPage<RecordVo> getRecordInfoByPage(IPage<RecordVo> page, RecordDTO dto);
}




