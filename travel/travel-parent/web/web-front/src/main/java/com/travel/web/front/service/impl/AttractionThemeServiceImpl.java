package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.AttractionTheme;
import com.travel.web.front.mapper.AttractionThemeMapper;
import com.travel.web.front.service.AttractionThemeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15101
* @description 针对表【attraction_theme(主题)】的数据库操作Service实现
* @createDate 2025-04-06 10:07:35
*/
@Service
public class AttractionThemeServiceImpl extends ServiceImpl<AttractionThemeMapper, AttractionTheme>
    implements AttractionThemeService {

    @Override
    public List<AttractionTheme> getAttractionTheme() {
        return this.list();
    }
}




