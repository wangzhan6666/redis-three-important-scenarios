package com.wangzhan.mapper;

import com.wangzhan.domain.UserBank;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description user_bank
 * @author wangzhan
 * @date 2024-04-22
 */
@Mapper
//@Repository
public interface UserBankMapper {

    List<UserBank> list();

    /**
    * 新增
    * @author wangzhan
    * @date 2024/04/22
    **/
    int insert(UserBank userBank);

    /**
    * 刪除
    * @author wangzhan
    * @date 2024/04/22
    **/
    int delete(int id);

    /**
    * 更新
    * @author wangzhan
    * @date 2024/04/22
    **/
    int update(UserBank userBank);

    /**
    * 查询 根据主键 id 查询
    * @author wangzhan
    * @date 2024/04/22
    **/
    UserBank load(int id);

    /**
    * 查询 分页查询
    * @author wangzhan
    * @date 2024/04/22
    **/
    List<UserBank> pageList(int offset, int pagesize);

    /**
    * 查询 分页查询 count
    * @author wangzhan
    * @date 2024/04/22
    **/
    int pageListCount(int offset,int pagesize);

}