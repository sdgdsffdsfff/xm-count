package com.xiaomi.count.model;

import javax.persistence.*;

/**
 * 用户对业务分类的curd
 * Created by lijie on 2015-07-15.
 */
@Entity
@Table(name = "user_bz")
public class UserBz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userBzId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "bz_id")
    private Integer id;

    @Column(name = "[name]")
    private String name;

    private String code;

    private Integer orderNo;

    public Integer getUserBzId() {
        return userBzId;
    }

    public void setUserBzId(Integer userBzId) {
        this.userBzId = userBzId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
