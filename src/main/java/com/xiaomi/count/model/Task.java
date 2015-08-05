package com.xiaomi.count.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task")
public class Task {
    /**
     * 任务表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "[name]")
    private String name;

    /**
     * sql执行的模式 1立即执行 2定时执行
     */
    @Column(name = "[execute]")
    private String execute;

    /**
     * 1=覆盖写入 ,其他增量写入
     */
    private String model;

    /**
     * 收益=award * 当前统计获得的总记录数
     */
//    private Long award;

    /**
     * 业务分类
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 执行sql ,原始sql
     */
    @Column(name = "[sql]")
    private String sql;

    /**
     * 执行sql 真实执行的sql ,定时任务写临时表后执行当前sql
     */
    @Column(name = "[table]")
    private String table;

    private Integer orderNo;

    private String state;

    private Double plus;

    @Transient
    private String test;

    /**
     * 任务类型 1=sql任务 2=脚本任务
     */
    @Column(name = "[type]")
    private String type;

    /**
     * 脚本文件存放位置 python/{id}/xxx.py
     */
    private String url;


    /**
     * 获取任务表
     *
     * @return id - 任务表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置任务表
     *
     * @param id 任务表
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取sql执行的模式 1立即执行 2定时执行
     *
     * @return execute - sql执行的模式 1立即执行 2定时执行
     */
    public String getExecute() {
        return execute;
    }

    /**
     * 设置sql执行的模式 1立即执行 2定时执行
     *
     * @param execute sql执行的模式 1立即执行 2定时执行
     */
    public void setExecute(String execute) {
        this.execute = execute;
    }

    /**
     * 获取1=覆盖写入 ,其他增量写入
     *
     * @return model - 1=覆盖写入 ,其他增量写入
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置1=覆盖写入 ,其他增量写入
     *
     * @param model 1=覆盖写入 ,其他增量写入
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取业务分类
     *
     * @return business_id - 业务分类
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置业务分类
     *
     * @param businessId 业务分类
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取执行sql ,原始sql
     *
     * @return sql - 执行sql ,原始sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * 设置执行sql ,原始sql
     *
     * @param sql 执行sql ,原始sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 获取执行sql 真实执行的sql ,定时任务写临时表后执行当前sql
     *
     * @return realSql - 执行sql 真实执行的sql ,定时任务写临时表后执行当前sql
     */
    public String getTable() {
        return table;
    }

    /**
     * 设置执行sql 真实执行的sql ,定时任务写临时表后执行当前sql
     *
     * @param table 执行sql 真实执行的sql ,定时任务写临时表后执行当前sql
     */
    public void setTable(String table) {
        this.table = table;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getPlus() {
        return plus;
    }

    public void setPlus(Double plus) {
        this.plus = plus;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "task")
    @JsonManagedReference
    private List<Timer> timerList;

    public List<Timer> getTimerList() {
        return timerList;
    }

    public void setTimerList(List<Timer> timerList) {
        this.timerList = timerList;
    }

}