package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.RecordInfo;
import com.travel.web.admin.dto.record.RecordDTO;
import com.travel.web.admin.mapper.RecordInfoMapper;
import com.travel.web.admin.service.RecordInfoService;
import com.travel.web.admin.vo.record.RecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15101
* @description 针对表【record_info(景点信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class RecordInfoServiceImpl extends ServiceImpl<RecordInfoMapper, RecordInfo>
    implements RecordInfoService {

    @Autowired
    private RecordInfoMapper recordInfoMapper;


    @Override
    public IPage<RecordVo> getRecordInfoByPage(IPage<RecordVo> page, RecordDTO dto) {
        IPage<RecordVo> result=recordInfoMapper.getRecordInfoByPage(page,dto);
        List<RecordVo> records = result.getRecords();
        records.forEach(item->{

        });
        return result;
    }
}




