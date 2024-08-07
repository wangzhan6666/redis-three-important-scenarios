package com.wangzhan.controller;

import com.wangzhan.domain.UserBank;
import com.wangzhan.service.HotspotInvalidService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangzhan
 * @version 1.0
 * @description 缓存击穿
 * @date 2024/7/6 11:10:17
 */
@RestController
@RequestMapping("/hotspotInvalid")
public class HotspotInvalidController {

    @Resource
    private HotspotInvalidService hotspotInvalidService;

    /***
     * @description 1、模拟缓存击穿-初始化缓存
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("initCache")
    public void initCache() {
        hotspotInvalidService.initCache();
    }

    /***
     * @description 2、模拟缓存击穿(数据从缓存中获取)    4、模拟缓存击穿(数据从数据库中获取)
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("imitateHotspotInvlid")
    public UserBank imitateHotspotInvlid(Integer id) {
        return hotspotInvalidService.imitateHotspotInvlid(id);
    }

    /***
     * @description 3、模拟缓存击穿-删除缓存id为88的数据
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("delete88IdData")
    public void delete88IdData() {
        hotspotInvalidService.delete88IdData();
    }

    /***
     * @description 解决方案一：采用互斥锁
     * @param id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/9 16:50:24
     */
    @GetMapping("lock")
    public UserBank lock(Integer id) {
        return hotspotInvalidService.lock(id);
    }

    /***
     * @description 解决方案二：异步定时任务
     * @return void
     * @author wangzhan
     * @date 2024/7/9 16:50:24
     */
    @GetMapping("cornJob")
    public UserBank cornJob(Integer id) {
        return hotspotInvalidService.cornJob(id);
    }

}
