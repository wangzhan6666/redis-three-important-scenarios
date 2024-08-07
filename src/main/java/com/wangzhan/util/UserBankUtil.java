package com.wangzhan.util;

import com.alibaba.fastjson.JSON;
import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/11 16:54:36
 */
@Component
public class UserBankUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserBankUtil.class);

    @Resource
    private UserBankMapper userBankMapper;

    private StringRedisTemplate stringRedisTemplate;

    public UserBankUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * @param id       主键id
     * @param key      缓存key
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @description: 查询缓存，如果缓存不存在，则查询数据库，并将数据缓存
     * @return: com.wangzhan.domain.UserBank
     * @author: wangzhan
     * @date: 2024/7/7 11:04:04
     */
    public UserBank queryUserBankOfStr(Integer id, String key, int timeout, TimeUnit timeUnit) {
        String userBankJson = stringRedisTemplate.opsForValue().get(key);
        UserBank userBank = JSON.parseObject(userBankJson, UserBank.class);

        if (userBank == null) {
            userBank = userBankMapper.load(id);
            if (userBank != null) {
                logger.info("==========设置数据到缓存=======");

                // 设置缓存
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(userBank), timeout, timeUnit);
            }
            logger.info("从【数据库】中获取数据，id为：{}，结果为：{}", id, userBank);
            return userBank;
        }
        logger.info("从【缓存】中获取数据，id为：{}，结果为：{}", id, userBank);

        return userBank;
    }

    /**
     * @param id       主键id
     * @param key      缓存key
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @description: 查询缓存，如果缓存不存在，则查询数据库，并将数据缓存
     * @return: com.wangzhan.domain.UserBank
     * @author: wangzhan
     * @date: 2024/7/7 11:04:04
     */
    public UserBank queryUserBankOfHash(Integer id, String key, int timeout, TimeUnit timeUnit) {
        String idStr = String.valueOf(id);
        String userBankJson = (String) stringRedisTemplate.opsForHash().get(key, idStr);
        UserBank userBank = JSON.parseObject(userBankJson, UserBank.class);
        if (userBank == null) {
            // 查询数据库
            userBank = userBankMapper.load(id);
            if (userBank != null) {
                logger.info("==========设置数据到缓存=======");
                stringRedisTemplate.opsForHash().put(key, idStr, JSON.toJSONString(userBank));
                stringRedisTemplate.expire(key, timeout, timeUnit);
            }

            logger.info("从【数据库】中获取数据，id为：{}，结果为：{}", idStr, userBank);
            return userBank;
        }

        logger.info("从【缓存】中获取数据，id为：{}，结果为：{}", idStr, userBank);
        return userBank;
    }
}
