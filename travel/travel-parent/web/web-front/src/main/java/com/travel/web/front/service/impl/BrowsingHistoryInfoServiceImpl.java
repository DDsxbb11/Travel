package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.BrowsingHistoryInfo;
import com.travel.web.front.mapper.BrowsingHistoryInfoMapper;
import com.travel.web.front.service.BrowsingHistoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 15101
* @description 针对表【browsing_history_info】的数据库操作Service实现
* @createDate 2025-04-18 00:44:49
*/
@Service
public class BrowsingHistoryInfoServiceImpl extends ServiceImpl<BrowsingHistoryInfoMapper, BrowsingHistoryInfo>
    implements BrowsingHistoryInfoService {

    @Autowired
    private BrowsingHistoryInfoMapper browsingHistoryInfoMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public IPage<BrowsingHistoryInfo> getPage(Integer pageNum, Integer pageSize,String token) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        LambdaQueryWrapper<BrowsingHistoryInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BrowsingHistoryInfo::getUserId, jwtUtil.getUserId(token))
                .orderByDesc(BrowsingHistoryInfo::getUpdateTime);
        return this.page(new Page<>(pageNum, pageSize),queryWrapper);
    }

    /**
     * @author: Amoretto
     * TODO: 保存浏览历史，后续做异步处理
     */
    @Override
    public void addBrowsingHistory(String token,BrowsingHistoryInfo browsingHistoryInfo) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        Long userId = jwtUtil.getUserId(token);
        browsingHistoryInfo.setUserId(userId);

        LambdaUpdateWrapper<BrowsingHistoryInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BrowsingHistoryInfo::getUserId,userId)
                .eq(BrowsingHistoryInfo::getItemId,browsingHistoryInfo.getItemId())
                .eq(BrowsingHistoryInfo::getItemType,browsingHistoryInfo.getItemType());
        boolean flag = this.saveOrUpdate(browsingHistoryInfo, updateWrapper);
        if (!flag) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }
}




