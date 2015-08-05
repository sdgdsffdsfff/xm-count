package com.xiaomi.count.model;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rs", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rs_id"))
    @OrderBy(value = "orderNo")
    private Set<Rs> rsSet;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @OrderBy(value = "orderNo")
    private Set<UserBz> userBzSet;


    public Set<Rs> getRsSet() {
        return rsSet;
    }

    public void setRsSet(Set<Rs> rsSet) {
        this.rsSet = rsSet;
    }

    public Set<UserBz> getUserBzSet() {
        return userBzSet;
    }

    public void setUserBzSet(Set<UserBz> userBzSet) {
        this.userBzSet = userBzSet;
    }
}