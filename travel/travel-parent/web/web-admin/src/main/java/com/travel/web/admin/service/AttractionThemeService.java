package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.HotelInfo;
import com.travel.web.admin.dto.theme.ThemeDTO;

/**
* @author 15101
* @description 针对表【attraction_theme(主题)】的数据库操作Service
* @createDate 2025-04-06 10:07:35
*/
public interface AttractionThemeService extends IService<AttractionTheme> {

    IPage<AttractionTheme> getAttractionThemePage(IPage<AttractionTheme> page, ThemeDTO dto);
}
