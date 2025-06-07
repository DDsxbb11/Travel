package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.HotelInfo;
import com.travel.web.admin.dto.theme.ThemeDTO;
import com.travel.web.admin.mapper.AttractionThemeMapper;
import com.travel.web.admin.service.AttractionThemeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 15101
* @description 针对表【attraction_theme(主题)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class AttractionThemeServiceImpl extends ServiceImpl<AttractionThemeMapper, AttractionTheme>
    implements AttractionThemeService {

    @Override
    public IPage<AttractionTheme> getAttractionThemePage(IPage<AttractionTheme> page, ThemeDTO dto) {
        LambdaQueryWrapper<AttractionTheme> queryWrapper = new LambdaQueryWrapper<>();
        if (dto != null) {
            queryWrapper.like(dto.getName() != null && StringUtils.hasText(dto.getName()), AttractionTheme::getName, dto.getName());
        }
        return this.page(page, queryWrapper);
    }
}




