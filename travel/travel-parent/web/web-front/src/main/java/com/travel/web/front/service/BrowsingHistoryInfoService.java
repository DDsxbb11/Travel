package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.BrowsingHistoryInfo;
import com.travel.web.front.vo.attraction.AttractionVo;

/**
* @author 15101
* @description 针对表【browsing_history_info】的数据库操作Service
* @createDate 2025-04-18 00:44:49
*/
public interface BrowsingHistoryInfoService extends IService<BrowsingHistoryInfo> {

    /**
     * @author: Amoretto
     * 分页获取浏览历史1
     */
    IPage<BrowsingHistoryInfo> getPage(Integer pageNum, Integer pageSize,String token);

    /**
     * @author: Amoretto
     * 添加浏览历史
     */
    void addBrowsingHistory(String token,BrowsingHistoryInfo browsingHistoryInfo);
}
