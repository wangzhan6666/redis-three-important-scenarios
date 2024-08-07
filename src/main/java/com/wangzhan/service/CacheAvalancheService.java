package com.wangzhan.service;

import com.wangzhan.domain.UserBank;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/7 10:34:44
 */
public interface CacheAvalancheService {

    /***
     * @description 初始化缓存
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    void initCache();

    /****
     * @description 2、模拟缓存雪崩(数据从缓存中获取)    4、模拟缓存雪崩(数据从数据库中获取)
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/6 11:02:38
     */
    UserBank imitateCacheAvalanche(Integer id);

    /***
     * @description 3、模拟缓存雪崩-删除30-40的数据
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    void delete30_40IdData();

    /***
     * @description 解决方案一：采用互斥锁
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    UserBank lock(Integer id);

    /***
     * @description 解决方案二：异步定时任务
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/6 11:02:38
     */
    UserBank cornJob(Integer id);

    /***
     * @description 解决方案三：随机过期时间
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/11 09:39:47
     */
    UserBank randomExpireTime(Integer id);

}
