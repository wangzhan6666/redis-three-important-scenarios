package com.wangzhan.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.wangzhan.domain.UserBank;
import com.wangzhan.mapper.UserBankMapper;
import com.wangzhan.service.CachePenetrationService;
import com.wangzhan.util.UserBankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2024/7/7 10:35:55
 */
@Service
public class CachePenetrationServiceImpl implements CachePenetrationService {

    private static final Logger logger = LoggerFactory.getLogger(UserBankServiceImpl.class);

    private static final String IMITATE_KEY = "user_bank_key:wangzhan:cachePenetration:imitate"; // 缓存key
    private static final String SOLVE1_IMITATE_KEY = "user_bank_key:wangzhan:cachePenetration:solve1"; // 解决方案的缓存key
    private static final String SOLVE2_IMITATE_KEY = "user_bank_key:wangzhan:cachePenetration:solve2"; // 解决方案的缓存key

    public static final int _1W = 10000;

    //布隆过滤器里预计要插入多少数据
    public static int size = _1W;

    //误判率,它越小误判的个数也就越少(思考，是不是可以设置的无限小，没有误判岂不更好)
    public static double fpp = 0.03;

    // 构建布隆过滤器
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    @Resource
    private UserBankMapper userBankMapper;

    @Resource
    private UserBankUtil userBankUtil;

    private StringRedisTemplate stringRedisTemplate;

    public CachePenetrationServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /*--------------------------------缓存穿透模拟-----------------------start----------------------------*/

    @Override
    public UserBank imitateCachePenetration(Integer id) {
        return userBankUtil.queryUserBankOfHash(id, IMITATE_KEY, 1, TimeUnit.HOURS);
    }

    /*--------------------------------缓存穿透模拟-----------------------end----------------------------*/

    /*--------------------------------缓存穿透解决方案-----------------------start----------------------------*/

    @Override
    public Object cacheNullKey(Integer id) {

        String idStr = String.valueOf(id);
        UserBank userBank = userBankUtil.queryUserBankOfHash(id, SOLVE1_IMITATE_KEY, 1, TimeUnit.HOURS);

        if (userBank != null) {
            return userBank;
        }

        // 缓存和数据库都不存在
        userBank = new UserBank();
        userBank.setId(id);
        userBank.setUserName("当前用户不存在");
        userBank.setAmount(0);

        stringRedisTemplate.opsForHash().put(SOLVE1_IMITATE_KEY, idStr, JSON.toJSONString(userBank));
        stringRedisTemplate.expire(SOLVE1_IMITATE_KEY, 1, TimeUnit.HOURS);
        logger.info("缓存和数据库都不存在");
        return userBank;
    }


    @PostConstruct
    @Override
    public void initCache() {
        new Thread(() -> {
            // 查询所有数据进行缓存
            List<UserBank> userBanks = userBankMapper.list();

            userBanks.forEach(userBank -> {
                // 初始化时，先添加到布隆过滤器
                bloomFilter.put(userBank.getId());
            });

            // 通过手动删除缓存（delete30And40IdData），模拟缓存失效
            //stringRedisTemplate.expire("imitateHotspotInvlid", 1, TimeUnit.SECONDS);

        }).start();
    }
    @Override
    public Object bloomFilter(Integer id) {
        if (bloomFilter.mightContain(id)) { // 可能包含
            return userBankUtil.queryUserBankOfHash(id, SOLVE2_IMITATE_KEY, 1, TimeUnit.HOURS);
        }
        logger.info("布隆过滤器中一定不存在该数据，id为：{}", id);
        return null;
    }

    /*--------------------------------缓存穿透解决方案-----------------------end----------------------------*/

}
