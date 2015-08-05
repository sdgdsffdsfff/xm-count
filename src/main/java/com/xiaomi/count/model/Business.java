package com.xiaomi.count.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "business")
public class Business {
    /**
     * 业务分类表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "[name]")
    private String name;

    private Integer orderNo;

    /**
     * 获取业务分类表
     *
     * @return id - 业务分类表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置业务分类表
     *
     * @param id 业务分类表
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取分类名称
     *
     * @return name - 分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分类名称
     *
     * @param name 分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

//    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "business_Id")
//    @OrderBy(value = "orderNo")
//    private List<Task> taskList ;
//
//    public List<Task> getTaskList() {
//        return taskList;
//    }
//
//    public void setTaskList(List<Task> taskList) {
//        this.taskList = taskList;
//    }
}