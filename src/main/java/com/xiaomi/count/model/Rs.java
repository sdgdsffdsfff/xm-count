package com.xiaomi.count.model;

import javax.persistence.*;

/**
 * 资源栏目
 * Created by lijie on 2015-07-15.
 */
@Entity
@Table(name = "resources")
public class Rs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    @Column(name = "[name]")
    private String name;
    private Integer orderNo;

    private String cls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
