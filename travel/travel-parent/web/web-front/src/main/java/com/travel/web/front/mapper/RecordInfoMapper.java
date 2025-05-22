package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.RecordInfo;
import com.travel.web.front.dto.record.RecordDTO;
import com.travel.web.front.vo.record.RecordDetailVo;
import com.travel.web.front.vo.record.RecordVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【record_info(景点信息)】的数据库操作Mapper
* @createDate 2025-03-17 00:18:45
* @Entity com.travel.model.pojo.RecordInfo
*/
public interface RecordInfoMapper extends BaseMapper<RecordInfo> {

    RecordDetailVo getRecordDetailById(Long id);

    IPage<RecordVo> getPage(IPage<RecordVo> page, @Param("recordDTO") RecordDTO recordDTO,@Param("userId") Long userId);
}




