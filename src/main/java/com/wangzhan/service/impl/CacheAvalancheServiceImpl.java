package com.wangzhan.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import com.wangzhan.service.CacheAvalancheService;
import com.wangzhan.task.CacheAvalancheTask;
import com.wangzhan.util.UserBankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/7 10:34:57
 */
@Service
public class CacheAvalancheServiceImpl implements CacheAvalancheService {

    private static final Logger logger = LoggerFactory.getLogger(UserBankServiceImpl.class);

    private static final String IMITATE_KEY = "user_bank_key:wangzhan:cacheAvalanche:imitate:";
    // 解决方案1的key - 互斥锁
    private static final String SOLVE1_IMITATE_KEY = "user_bank_key:wangzhan:cacheAvalanche:solve1:";
    // 解决方案2的key  -  异步定时更新缓存
    private static final String SOLVE2_IMITATE_KEY = "user_bank_key:wangzhan:cacheAvalanche:solve2:";
    // 解决方案3的key  -  随机缓存失效时间
    private static final String SOLVE3_IMITATE_KEY = "user_bank_key:wangzhan:cacheAvalanche:solve3:";

    // 可重入锁
    private static final Lock lock = new ReentrantLock();

    @Resource
    private UserBankMapper userBankMapper;

    @Resource
    private UserBankUtil userBankUtil;

    @Resource
    private CacheAvalancheTask cacheAvalancheTask;

    private StringRedisTemplate stringRedisTemplate;

    public CacheAvalancheServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /*--------------------------------缓存雪崩模拟-----------------------start----------------------------*/
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
    public UserBank imitateCacheAvalanche(Integer id) {
        return userBankUtil.queryUserBankOfStr(id, IMITATE_KEY + id, 1, TimeUnit.HOURS);
    }

    @Override
    public void delete30_40IdData() {
        for (int i = 30; i < 40; i++) {
            stringRedisTemplate.delete(IMITATE_KEY + i);
        }
    }
    /*--------------------------------缓存雪崩模拟-----------------------end----------------------------*/

    /*--------------------------------缓存雪崩解决方案-----------------------start----------------------------*/
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
            // 再次检查数据，确保在获取锁期间没有其他线程更新缓存
            userBankUtil.queryUserBankOfStr(id, SOLVE1_IMITATE_KEY + id, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return userBank;
    }

    // 初始化所有数据的缓存，同时启动定时任务
    @PostConstruct
    public void initData() {
        cacheAvalancheTask.enableScheduledUpdate();
    }

    @Override
    public UserBank cornJob(Integer id) {
        return userBankUtil.queryUserBankOfStr(id, SOLVE2_IMITATE_KEY + id, 1, TimeUnit.MINUTES);
    }

    // TODO 这里只是展示 随机过期时间的方案，并不保证线程安全。
    @Override
    public UserBank randomExpireTime(Integer id) {
        return userBankUtil.queryUserBankOfStr(id, SOLVE3_IMITATE_KEY + id, randomExpireTime(), TimeUnit.SECONDS);
    }

    // 随机生成过期时间
    public int randomExpireTime() {
        // 基本时间为60秒
        int baseExpireTime = 60;
        // 随机偏移量范围
        int offsetRange = 40;
        // 生成随机偏移量
        int randomOffset = ThreadLocalRandom.current().nextInt(-offsetRange, offsetRange + 1);

        // 返回最终的过期时间
        return baseExpireTime + randomOffset;
    }

    /*--------------------------------缓存雪崩解决方案-----------------------end----------------------------*/

}
