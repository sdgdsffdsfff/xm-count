package com.xiaomi.count.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
public class History {
    /**
     * 计算历史表 记录没个计算任务的执行情况
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 任务开始执行时间
     */
    @Column(name = "[start]")
    private Date start;

    /**
     * 执行任务花费时间(s)
     */
    @Column(name = "[time]")
    private Long time;

    /**
     * 1=执行成功 0=执行失败 2=执行中
     */
    private String state;

    /**
     * 关联 任务
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    /**
     * 执行失败 错误记录
     */
    private String error;


    private Integer times;


    /**
     * 获取计算历史表 记录没个计算任务的执行情况
     *
     * @return id - 计算历史表 记录没个计算任务的执行情况
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置计算历史表 记录没个计算任务的执行情况
     *
     * @param id 计算历史表 记录没个计算任务的执行情况
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务开始执行时间
     *
     * @return start - 任务开始执行时间
     */
    public Date getStart() {
        return start;
    }

    /**
     * 设置任务开始执行时间
     *
     * @param start 任务开始执行时间
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * 获取执行任务花费时间(s)
     *
     * @return time - 执行任务花费时间(s)
     */
    public Long getTime() {
        return time;
    }

    /**
     * 设置执行任务花费时间(s)
     *
     * @param time 执行任务花费时间(s)
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * 获取1=执行成功 0=执行失败 2=执行中
     *
     * @return state - 1=执行成功 0=执行失败 2=执行中
     */
    public String getState() {
        return state;
    }

    /**
     * 设置1=执行成功 0=执行失败 2=执行中
     *
     * @param state 1=执行成功 0=执行失败 2=执行中
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取关联 任务
     *
     * @return task_id - 关联 任务
     */
    public Task getTask() {
        return task;
    }

    /**
     * 设置关联 任务
     *
     * @param task 关联 任务
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * 获取执行失败 错误记录
     *
     * @return error - 执行失败 错误记录
     */
    public String getError() {
        return error;
    }

    /**
     * 设置执行失败 错误记录
     *
     * @param error 执行失败 错误记录
     */
    public void setError(String error) {
        this.error = error;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}