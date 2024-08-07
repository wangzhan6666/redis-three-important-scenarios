package com.wangzhan.service;

import com.wangzhan.domain.UserBank;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/3 15:43:28
 */
public interface UserBankService {

    /***
     * @description 模拟缓存穿透
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 16:59:45
     */
    UserBank imitateCachePenetration(Integer id);

    /***
     * @description 模拟缓存击穿
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 16:59:45
     */
    UserBank imitateHotspotInvlid(Integer id);

    /***
     * @description 删除缓存为30~40的数据
     *
     * @return void
     * @author wangzhan
     * @date 2024/7/4 08:51:08
     */
    void delete88IdData();

    /***
     * @description 初始化缓存
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    void initCache();

    /***
     * @description 3、模拟缓存雪崩-删除1-100的数据
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    void delete1_100IdData();

    /****
     * @description 2、模拟缓存雪崩(数据从缓存中获取)    4、模拟缓存雪崩(数据从数据库中获取)
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/6 11:02:38
     */
    UserBank imitateCacheAvalanche(Integer id);

}
