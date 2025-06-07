package com.travel.web.admin.dto.attraction;

import com.travel.model.pojo.AttractionImg;
import com.travel.model.pojo.AttractionInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 0:55
 */
@Data
public class AttractionInfoDTO extends AttractionInfo {

    private List<AttractionImg> imageList;
}
