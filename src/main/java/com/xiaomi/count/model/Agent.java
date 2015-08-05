package com.xiaomi.count.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "agent")
public class Agent {
    /**
     * 代理商管理
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 显示值
     */
    @Column(name = "[text]")
    private String text;

    /**
     * 值
     */
    @Column(name = "[value]")
    private String value;

    private Integer orderNo;

    /**
     * 父Id
     */
    private Integer pid;


    /**
     * 获取代理商管理
     *
     * @return id - 代理商管理
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置代理商管理
     *
     * @param id 代理商管理
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取显示值
     *
     * @return text - 显示值
     */
    public String getText() {
        return text;
    }

    /**
     * 设置显示值
     *
     * @param text 显示值
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    @OrderBy(value = "orderNo")
    private List<Agent> children;

    public List<Agent> getChildren() {
        return children;
    }

    public void setChildren(List<Agent> children) {
        this.children = children;
    }
}