package com.wangzhan.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import com.wangzhan.service.HotspotInvalidService;
import com.wangzhan.task.HotspotInvlidTask;
import com.wangzhan.util.UserBankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/7 10:36:40
 */
@Service
public class HotspotInvalidServiceImpl implements HotspotInvalidService {

    private static final Logger logger = LoggerFactory.getLogger(UserBankServiceImpl.class);

    private static final String IMITATE_KEY = "user_bank_key:wangzhan:hotspotInvalid:";
    private static final String SOLVE1_IMITATE_KEY = "user_bank_key:wangzhan:hotspotInvalid:solve1:";
    private static final String SOLVE2_IMITATE_KEY = "user_bank_key:wangzhan:hotspotInvalid:solve2:";

    // 可重入锁
    private static final Lock lock = new ReentrantLock();

    @Resource
    private UserBankMapper userBankMapper;

    @Resource
    private UserBankUtil userBankUtil;

    @Resource
    private HotspotInvlidTask hotspotInvlidTask;

    private StringRedisTemplate stringRedisTemplate;

    public HotspotInvalidServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /*--------------------------------缓存击穿模拟-----------------------start----------------------------*/

    @Override
    public void initCache() {
        new Thread(() -> {
            // 查询所有数据进行缓存
            List<UserBank> userBanks = userBankMapper.list();

            userBanks.forEach(userBank -> {
                stringRedisTemplate.opsForValue().set(IMITATE_KEY + userBank.getId(), JSON.toJSONString(userBank));
                stringRedisTemplate.expire(IMITATE_KEY + userBank.getId(), 1, TimeUnit.HOURS);
            });

            // 通过手动删除缓存（delete30And40IdData），模拟缓存失效
            //stringRedisTemplate.expire("imitateHotspotInvlid", 1, TimeUnit.SECONDS);

        }).start();
    }

    @Override
    public UserBank imitateHotspotInvlid(Integer id) {
        return userBankUtil.queryUserBankOfStr(id, IMITATE_KEY + id, 1, TimeUnit.MINUTES);
    }

    @Override
    public void delete88IdData() {
        // 模拟id为88 缓存失效的情况
        stringRedisTemplate.delete(IMITATE_KEY + 88);
    }

    /*--------------------------------缓存击穿模拟-----------------------end----------------------------*/

    /*--------------------------------缓存击穿解决方案-----------------------start----------------------------*/

    @Override
    public UserBank lock(Integer id) {
        String userBankJson = stringRedisTemplate.opsForValue().get(SOLVE1_IMITATE_KEY + id);
        UserBank userBank = JSON.parseObject(userBankJson, UserBank.class);
        if (userBank != null) {
            logger.info("从【缓存】中获取数据，id为：{}，结果为：{}", id, userBank);
            return userBank;
        }

        try {
            lock.lock();

            return userBankUtil.queryUserBankOfStr(id, SOLVE1_IMITATE_KEY + id, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 释放锁
            lock.unlock();
        }

        return userBank;
    }


    // 初始化88的缓存，同时启动定时任务
    @PostConstruct
    public void init88IdData() {
        hotspotInvlidTask.enableScheduledUpdate();
    }

    @Override
    public UserBank cornJob(Integer id) {
        return userBankUtil.queryUserBankOfStr(id, SOLVE2_IMITATE_KEY + id, 1, TimeUnit.MINUTES);
    }

    /*--------------------------------缓存击穿解决方案-----------------------end----------------------------*/

}
