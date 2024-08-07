package com.wangzhan.task;

import com.alibaba.fastjson.JSON;
import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhan
 * @version 1.0
 * @description 异步定时更新缓存
 * @date 2024/7/10 08:53:18
 */
@Component
@EnableScheduling
public class HotspotInvlidTask {

    @Resource
    private UserBankMapper userBankMapper;

    private StringRedisTemplate stringRedisTemplate;

    public HotspotInvlidTask(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private volatile boolean enableScheduledUpdate = false;

    private static final String SOLVE2_IMITATE_KEY = "user_bank_key:wangzhan:hotspotInvalid:solve2:";


    /***
     * @description 间隔55秒更新缓存
     *
     * @return void
     * @author wangzhan
     * @date 2024/7/10 11:12:51
     */
    @Scheduled(fixedDelay = 55, timeUnit = TimeUnit.SECONDS)
    public void scheduledUpdate() {
        if (!enableScheduledUpdate) {
            return;
        }

        updateDataAsync();
    }

    /**
     * 开启定时更新缓存
     */
    public void enableScheduledUpdate() {
        enableScheduledUpdate = true;
        scheduledUpdate();
    }

    /**
     * 异步更新缓存
     */
    public void updateDataAsync() {
        UserBank userBank = userBankMapper.load(88);
        stringRedisTemplate.opsForValue().set(SOLVE2_IMITATE_KEY + 88, JSON.toJSONString(userBank), 1, TimeUnit.MINUTES);
    }

}
