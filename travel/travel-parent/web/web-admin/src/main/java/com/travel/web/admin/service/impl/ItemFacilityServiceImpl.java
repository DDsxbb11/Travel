package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.ItemFacility;
import com.travel.web.admin.mapper.ItemFacilityMapper;
import com.travel.web.admin.service.ItemFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 15101
* @description 针对表【item_facility(实体设施对照表)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class ItemFacilityServiceImpl extends ServiceImpl<ItemFacilityMapper, ItemFacility>
    implements ItemFacilityService {

    @Autowired
    private ItemFacilityMapper itemFacilityMapper;

    @Override
    public void saveOrUpdateFacility(Long infoId, Long voId, List<Long> facilityIds, String type,String facilityType) {
        ItemFacility itemFacility = new ItemFacility();
        itemFacility.setItemId(infoId);
        itemFacility.setType(type);
        itemFacility.setSort(1);
        if (voId == null){
            //新增
            facilityIds.forEach(facilityId -> {
                itemFacility.setFacilityId(facilityId);
                this.save(itemFacility);
            });
        }else {
            //修改
            List<Long> existFacilityIds = this.getFacilityIdListByTypeAndItemId(voId, type,facilityType);
            List<Long> deleteIds = new ArrayList<>();
            for (Long id : facilityIds) {
                if (!existFacilityIds.contains(id)){
                    //新增
                    itemFacility.setFacilityId(id);
                    this.save(itemFacility);
                }else {
                    //删除
                    deleteIds.add(id);
                }
            }
            //批量删除
            if (!deleteIds.isEmpty()){
                this.removeBatchByIds(deleteIds);
            }
        }
    }

    @Override
    public List<Long> getFacilityIdListByTypeAndItemId(Long id, String type,String facilityType) {
        return itemFacilityMapper.getFacilityIdListByTypeAndItemId(id, type ,facilityType);
    }
}




