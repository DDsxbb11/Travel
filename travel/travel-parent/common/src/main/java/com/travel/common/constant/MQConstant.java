package com.travel.common.constant;

/**
 * @author: JYX
 * @description: TODO: MQ常量
 * @date: 2024-12-08 22:13
 */

public class MQConstant {
    /**
     * 定义交换机
     */
    public static final String APARTMENT_EXCHANGE="apartment.topic";

    /**
     * 定义插入或更改队列
     */
    public static final String APARTMENT_INSERT_QUEUE="apartment.insert.queue";

    /**
     * 定义删除队列
     */
    public static final String APARTMENT_DELETE_QUEUE="apartment.delete.queue";

    /**
     * 定义插入或更改routingKey
     */
    public static final String APARTMENT_INSERT_KEY="apartment.insert.key";

    /**
     * 定义删除routingKey
     */
    public static final String APARTMENT_DELETE_KEY="apartment.delete.key";

    /**
     * 定义插入或更改队列
     */
    public static final String APP_INSERT_HISTORY_QUEUE="app.insert.history.queue";

    /**
     * 定义保存浏览历史routingKey
     */
    public static final String APP_INSERT_HISTORY_KEY="app.insert.history.key";
}
