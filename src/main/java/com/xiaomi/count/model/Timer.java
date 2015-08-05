package com.xiaomi.count.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "timer")
public class Timer {


    public Timer() {
    }

    public Timer(String time, Task task) {
        this.time = time;
        this.task = task;
    }

    /**
     * task任务执行时间配置表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 时间 03:45 (时分)
     */
    @Column(name = "[time]")
    private String time;

    /**
     * 任务 外键
     */
//    @Column(name = "task_id")
//    private Integer taskId;

    /**
     * 获取task任务执行时间配置表
     *
     * @return id - task任务执行时间配置表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置task任务执行时间配置表
     *
     * @param id task任务执行时间配置表
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取时间 03:45 (时分)
     *
     * @return time - 时间 03:45 (时分)
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置时间 03:45 (时分)
     *
     * @param time 时间 03:45 (时分)
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取任务 外键
     *
     * @return task_id - 任务 外键
     */
//    public Integer getTaskId() {
//        return taskId;
//    }

    /**
     * 设置任务 外键
     *
     * @param taskId 任务 外键
     */
//    public void setTaskId(Integer taskId) {
//        this.taskId = taskId;
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}