package com.wangzhan.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import com.wangzhan.service.UserBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/3 16:54:53
 */
@Service
public class UserBankServiceImpl implements UserBankService {

    private static final Logger logger = LoggerFactory.getLogger(UserBankServiceImpl.class);

    private static final String IMITATE_KEY = "user_bank_key:wangzhan:imitate:";

    @Resource
    private UserBankMapper userBankMapper;

    private StringRedisTemplate stringRedisTemplate;

    public UserBankServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public UserBank imitateCachePenetration(Integer id) {
        String idStr = String.valueOf(id);
        String userBankJson = (String) stringRedisTemplate.opsForHash().get("imitateCachePenetration", idStr);
        UserBank userBank = JSON.parseObject(userBankJson, UserBank.class);
        if (userBank == null) {
            // 查询数据库
            userBank = userBankMapper.load(id);
            if (userBank != null) {
                logger.info("==========设置数据到缓存=======");
                stringRedisTemplate.opsForHash().put("imitateCachePenetration", idStr, JSON.toJSONString(userBank));
            }
            logger.info("从【数据库】中获取数据，id为：{}，结果为：{}", idStr, userBank);
            return userBank;
        }

        logger.info("从【缓存】中获取数据，id为：{}，结果为：{}", idStr, userBank);
        return userBank;
    }

    @Override
    public UserBank imitateHotspotInvlid(Integer id) {
        String userBankJson = stringRedisTemplate.opsForValue().get(IMITATE_KEY + id);
        UserBank userBank = JSON.parseObject(userBankJson, UserBank.class);

        if (userBank == null) {
            // 根据id查询
            userBank = userBankMapper.load(id);

            if (userBank != null) {
                logger.info("==========设置数据到缓存=======");
                // 设置缓存
                stringRedisTemplate.opsForValue().set(IMITATE_KEY + id, JSON.toJSONString(userBank), 1, TimeUnit.MINUTES);
            }

            logger.info("从【数据库】中获取数据，id为：{}，结果为：{}", id, userBank);
            return userBank;
        }

        logger.info("从【缓存】中获取数据，id为：{}，结果为：{}", id, userBank);
        return userBank;
    }

    @Override
    public void delete88IdData() {
        // 模拟id为88 缓存失效的情况
        stringRedisTemplate.delete(IMITATE_KEY + 88);
    }

    @Override
    public void initCache() {
        new Thread(() -> {
            // 查询所有数据进行缓存
            List<UserBank> userBanks = userBankMapper.list();

            userBanks.forEach(userBank -> {
                stringRedisTemplate.opsForValue().set(IMITATE_KEY + userBank.getId(), JSON.toJSONString(userBank));
            });

            // 通过手动删除缓存（delete30And40IdData），模拟缓存失效
            //stringRedisTemplate.expire("imitateHotspotInvlid", 1, TimeUnit.SECONDS);

        }).start();
    }

    @Override
    public void delete1_100IdData() {
        for (int i = 1; i <= 100; i++) {
            stringRedisTemplate.delete(IMITATE_KEY + i);
        }
    }

    @Override
    public UserBank imitateCacheAvalanche(Integer id) {
        String userBankJson = stringRedisTemplate.opsForValue().get(IMITATE_KEY + id);
        UserBank userBank = JSON.parseObject(userBankJson, UserBank.class);

        if (userBank == null) {
            // 根据id查询
            userBank = userBankMapper.load(id);

            if (userBank != null) {
                logger.info("==========设置数据到缓存=======");
                // 设置缓存
                stringRedisTemplate.opsForValue().set(IMITATE_KEY + id, JSON.toJSONString(userBank), 1, TimeUnit.MINUTES);
            }

            logger.info("从【数据库】中获取数据，id为：{}，结果为：{}", id, userBank);
            return userBank;
        }

        logger.info("从【缓存】中获取数据，id为：{}，结果为：{}", id, userBank);
        return userBank;
    }


}
