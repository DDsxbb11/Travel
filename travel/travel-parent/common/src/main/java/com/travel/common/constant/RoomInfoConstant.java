package com.travel.common.constant;

/**
 * @author: JYX
 * @description: TODO: 房间信息索引库
 * @date: 2024-12-08 4:42
 */

public class RoomInfoConstant {

    public static final String MAPPING_TEMPLATE="{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": { \"type\": \"keyword\" },\n" +
            "      \"roomNumber\": { \"type\": \"keyword\" },\n" +
            "      \"rent\": { \"type\": \"double\" },\n" +
            "      \"graphVoList\": {\n" +
            "        \"type\": \"nested\",\n" +
            "        \"properties\": {\n" +
            "          \"name\": { \"type\": \"keyword\" },\n" +
            "          \"url\": { \"type\": \"keyword\" }\n" +
            "        }\n" +
            "      },\n" +
            "      \"labelInfoList\": {\n" +
            "        \"type\": \"nested\",\n" +
            "        \"properties\": {\n" +
            "          \"id\": { \"type\": \"keyword\" },\n" +
            "          \"type\": { \n" +
            "            \"type\": \"keyword\" \n" +
            "          },\n" +
            "          \"name\": { \"type\": \"keyword\" },\n" +
            "          \"createTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy/MM/dd HH:mm:ss||epoch_millis\"\n" +
            "          },\n" +
            "          \"updateTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy/MM/dd HH:mm:ss||epoch_millis\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"apartmentInfo\": {\n" +
            "        \"type\": \"nested\",\n" +
            "        \"properties\": {\n" +
            "          \"id\": { \"type\": \"keyword\" },\n" +
            "          \"name\": { \"type\": \"keyword\" },\n" +
            "          \"introduction\": { \"type\": \"keyword\" },\n" +
            "          \"districtId\": { \"type\": \"keyword\" },\n" +
            "          \"districtName\": { \"type\": \"keyword\" },\n" +
            "          \"cityId\": { \"type\": \"keyword\" },\n" +
            "          \"cityName\": { \"type\": \"keyword\" },\n" +
            "          \"provinceId\": { \"type\": \"keyword\" },\n" +
            "          \"provinceName\": { \"type\": \"keyword\" },\n" +
            "          \"addressDetail\": { \"type\": \"keyword\" },\n" +
            "          \"latitude\": { \"type\": \"keyword\" },\n" +
            "          \"longitude\": { \"type\": \"keyword\" },\n" +
            "          \"phone\": { \"type\": \"keyword\" },\n" +
            "          \"createTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy/MM/dd HH:mm:ss||epoch_millis\"\n" +
            "          },\n" +
            "          \"updateTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy/MM/dd HH:mm:ss||epoch_millis\"\n" +
            "          }\n" +
            "        }\n" +
            "        \n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}"
            ;
}
