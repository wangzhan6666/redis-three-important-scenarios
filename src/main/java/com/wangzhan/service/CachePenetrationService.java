package com.wangzhan.service;

import com.wangzhan.domain.UserBank;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/7 10:35:45
 */
public interface CachePenetrationService {

    /***
     * @description 初始化缓存
     * @return void
     * @author wangzhan
     * @date 2024/7/3 17:01:01
     */
    void initCache();

    /***
     * @description 模拟缓存穿透
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 16:59:45
     */
    UserBank imitateCachePenetration(Integer id);

    /***
     * @description 解决方案一：缓存空对象
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 16:59:45
     */
    Object cacheNullKey(Integer id);

    /***
     * @description 解决方案二：布隆过滤器
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 16:59:45
     */
    Object bloomFilter(Integer id);
}
