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
import com.travel.model.pojo.CollectionsInfo;
import com.travel.web.front.mapper.CollectionsInfoMapper;
import com.travel.web.front.service.CollectionsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 15101
 * @description 针对表【collections_info】的数据库操作Service实现
 * @createDate 2025-04-18 15:59:20
 */
@Service
public class CollectionsInfoServiceImpl extends ServiceImpl<CollectionsInfoMapper, CollectionsInfo>
        implements CollectionsInfoService {

    @Autowired
    private CollectionsInfoMapper collectionsInfoMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public IPage<CollectionsInfo> getCollectionsPage(String token, Integer pageNum, Integer pageSize) {
        if (jwtUtil.isExpiration(token)) {
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        LambdaQueryWrapper<CollectionsInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CollectionsInfo::getUserId, jwtUtil.getUserId(token))
                .orderByAsc(CollectionsInfo::getCollectionsTime);
        return this.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public Long addCollections(String token, CollectionsInfo collectionsInfo) {
        if (jwtUtil.isExpiration(token)) {
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        collectionsInfo.setCollectionsTime(new Date());
        collectionsInfo.setUserId(jwtUtil.getUserId(token));
        int insert = collectionsInfoMapper.insert(collectionsInfo);
        if (insert == 0) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
        return collectionsInfo.getId();
    }

    @Override
    public void deleteCollections(String token, Long id) {
        if (jwtUtil.isExpiration(token)) {
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        int i = collectionsInfoMapper.deleteById(id);
        if (i == 0) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public boolean isCollect(String token, Long id,String type) {
        if (jwtUtil.isExpiration(token)) {
            // token过期
            return false;
        }
        LambdaQueryWrapper<CollectionsInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CollectionsInfo::getItemId, id)
                .eq(CollectionsInfo::getUserId, jwtUtil.getUserId(token))
                .eq(CollectionsInfo::getItemType, type);
        CollectionsInfo collectionsInfo = collectionsInfoMapper.selectOne(queryWrapper);
        return collectionsInfo != null;
    }
}




