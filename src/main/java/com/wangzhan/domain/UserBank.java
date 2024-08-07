package com.wangzhan.domain;

import java.io.Serializable;

/**
 * @description user_bank
 * @author wangzhan
 * @date 2024-04-22
 */
public class UserBank implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 用户名称
    */
    private String userName;

    /**
    * 用户余额
    */
    private Integer amount;


    public UserBank() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UserBank{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", amount=" + amount +
                '}';
    }
}