package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.CollectionsInfo;

import java.util.List;

/**
* @author 15101
* @description 针对表【collections_info】的数据库操作Service
* @createDate 2025-04-18 15:59:20
*/
public interface CollectionsInfoService extends IService<CollectionsInfo> {

    IPage<CollectionsInfo> getCollectionsPage(String token,Integer pageNum, Integer pageSize);

    Long addCollections(String token,CollectionsInfo collectionsInfo);

    void deleteCollections(String token, Long id);

    boolean isCollect(String token, Long id,String type);

}
