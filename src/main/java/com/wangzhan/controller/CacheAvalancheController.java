package com.wangzhan.controller;

import com.wangzhan.domain.UserBank;
import com.wangzhan.service.CacheAvalancheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangzhan
 * @version 1.0
 * @description 缓存雪崩
 * @date 2024/7/6 11:08:19
 */
@RestController
@RequestMapping("/cacheAvalanche")
public class CacheAvalancheController {

    @Resource
    private CacheAvalancheService cacheAvalancheService;

    /***
     * @description 1、模拟缓存击穿-初始化缓存
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("initCache")
    public void initCache() {
        cacheAvalancheService.initCache();
    }


    /***
     * @description 2、模拟缓存雪崩(数据从缓存中获取)    4、模拟缓存雪崩(数据从数据库中获取)
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("imitateCacheAvalanche")
    public UserBank imitateCacheAvalanche(Integer id) {
        return cacheAvalancheService.imitateCacheAvalanche(id);
    }

    /***
     * @description 3、模拟缓存雪崩-删除30-40的数据
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("delete30_40IdData")
    public void delete30_40IdData() {
        cacheAvalancheService.delete30_40IdData();
    }

    /***
     * @description 解决方案一：采用互斥锁
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/10 22:28:42
     */
    @GetMapping("lock")
    public UserBank lock(Integer id) {
        return cacheAvalancheService.lock(id);
    }

    /***
     * @description 解决方案二：异步定时任务
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/10 22:29:04
     */
    @GetMapping("cornJob")
    public UserBank cornJob(Integer id) {
        return cacheAvalancheService.cornJob(id);
    }

    /***
     * @description 解决方案三：随机过期时间
     * @return int
     * @author wangzhan
     * @date 2024/7/10 22:29:04
     */
    @GetMapping("randomExpire")
    public UserBank randomExpireTime(Integer id) {
        return cacheAvalancheService.randomExpireTime(id);
    }

}
