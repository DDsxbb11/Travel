package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.CityInfo;
import com.travel.model.pojo.DistrictInfo;
import com.travel.model.pojo.ProvinceInfo;
import com.travel.web.front.mapper.CityInfoMapper;
import com.travel.web.front.mapper.DistrictInfoMapper;
import com.travel.web.front.mapper.ProvinceInfoMapper;
import com.travel.web.front.service.ProvinceInfoService;
import com.travel.web.front.vo.location.OptionVo;
import com.travel.web.front.vo.province.ProvinceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15101
 * @description 针对表【province_info】的数据库操作Service实现
 * @createDate 2025-03-14 19:44:06
 */
@Service
public class ProvinceInfoServiceImpl extends ServiceImpl<ProvinceInfoMapper, ProvinceInfo>
        implements ProvinceInfoService {

    @Autowired
    private ProvinceInfoMapper provinceInfoMapper;
    @Autowired
    private CityInfoMapper cityInfoMapper;
    @Autowired
    private DistrictInfoMapper districtInfoMapper;


    @Override
    public List<ProvinceVo> getProvinceList() {
        List<ProvinceInfo> provinceInfoList = this.list();
        return provinceInfoList.stream()
                .map(provinceInfo -> {
                    ProvinceVo vo = new ProvinceVo();
                    BeanUtils.copyProperties(provinceInfo, vo);
                    return vo;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 获取地区信息
     */
    @Override
    public List<OptionVo> getLocationOptions() {
        List<OptionVo> result=new ArrayList<>();
        this.list()
                .forEach(provinceInfo -> {
                    OptionVo provinceVo = new OptionVo();
                    provinceVo.setValue(provinceInfo.getName());
                    provinceVo.setLabel(provinceInfo.getName());
                    //省份包含城市
                    List<OptionVo> cityList=new ArrayList<>();
                    cityInfoMapper.selectList(new LambdaQueryWrapper<CityInfo>()
                            .eq(CityInfo::getProvinceId, provinceInfo.getId()))
                            .forEach( cityInfo -> {
                                OptionVo cityVo = new OptionVo();
                                cityVo.setValue(cityInfo.getName());
                                cityVo.setLabel(cityInfo.getName());
                                //城市包含区县
                                List<OptionVo> districtList=new ArrayList<>();
                                districtInfoMapper.selectList(new LambdaQueryWrapper<DistrictInfo>()
                                        .eq(DistrictInfo::getCityId, cityInfo.getId()))
                                        .forEach(districtInfo -> {
                                            OptionVo districtVo = new OptionVo();
                                            districtVo.setValue(districtInfo.getName());
                                            districtVo.setLabel(districtInfo.getName());
                                            districtList.add(districtVo);
                                        });
                                cityVo.setChildren(districtList);
                                cityList.add(cityVo);
                            });
                    provinceVo.setChildren(cityList);
                    result.add(provinceVo);
                });
        return result;
    }
}




