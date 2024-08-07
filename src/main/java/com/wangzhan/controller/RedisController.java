package com.wangzhan.controller;

import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import com.wangzhan.service.UserBankService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wangzhan
 * @version 1.0
 * @description transactional事务
 * @date 2024/4/22 11:23:34
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private UserBankMapper userBankMapper;

    @Resource
    private UserBankService userBankService;

    /***
     * @description 模拟缓存穿透
     * @param id    主键id
     * @return com.wangzhan.domain.UserBank
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("imitateCachePenetration")
    public UserBank imitateCachePenetration(Integer id) {
        return userBankService.imitateCachePenetration(id);
    }

    /***
     * @description 1、模拟缓存击穿-初始化缓存
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("initCache")
    public void initCache() {
        userBankService.initCache();
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
        return userBankService.imitateHotspotInvlid(id);
    }

    /***
     * @description 3、模拟缓存击穿-删除缓存id为88的数据
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("delete88IdData")
    public void delete88IdData() {
        userBankService.delete88IdData();
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
        return userBankService.imitateCacheAvalanche(id);
    }

    /***
     * @description 3、模拟缓存雪崩-删除1-100的数据
     * @return void
     * @author wangzhan
     * @date 2024/7/3 20:33:11
     */
    @GetMapping("delete1_100IdData")
    public void delete1_100IdData() {
        userBankService.delete1_100IdData();
    }

}
