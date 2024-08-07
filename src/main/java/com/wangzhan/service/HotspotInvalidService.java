package com.wangzhan.service;

import com.wangzhan.domain.UserBank;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/7 10:36:28
 */
public interface HotspotInvalidService {

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
     * @description 解决方案一：采用互斥锁
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 16:59:45
     */
    UserBank lock(Integer id);

    /***
     * @description 解决方案二：异步定时任务
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    UserBank cornJob(Integer id);

}
