package com.wangzhan.controller;

import com.wangzhan.domain.UserBank;
import com.wangzhan.service.CachePenetrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangzhan
 * @version 1.0
 * @description 缓存穿透
 * @date 2024/7/6 11:09:30
 */
@RestController
@RequestMapping("/cachePenetration")
public class CachePenetrationController {

    @Resource
    private CachePenetrationService cachepenetrationService;

    /***
     * @description 模拟缓存穿透
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("imitateCachePenetration")
    public UserBank imitateCachePenetration(Integer id) {
        return cachepenetrationService.imitateCachePenetration(id);
    }

    /***
     * @description 解决方案一：缓存空对象
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("cacheNullKey")
    public Object cacheNullKey(Integer id) {
        return cachepenetrationService.cacheNullKey(id);
    }

    /***
     * @description 解决方案二：布隆过滤器
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("bloomFilter")
    public Object bloomFilter(Integer id) {
        return cachepenetrationService.bloomFilter(id);
    }

}
