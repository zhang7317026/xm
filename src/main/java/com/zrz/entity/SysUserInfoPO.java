package com.zrz.entity;

public class SysUserInfoPO {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后操作时间
     */
    private String lastTime;

    /**
     * 最后登录时间
     */
    private String lastLogin;

    /**
     * 操作次数
     */
    private Integer optTimes;

    /**
     * 金币数
     */
    private Integer gold;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 头像地址
     */
    private String img;

    /**
     * 备注
     */
    private String marker;

    /**
     * 用户ID
     * @return id 用户ID
     */
    public String getId() {
        return id;
    }

    /**
     * 用户ID
     * @param id 用户ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 用户名
     * @return name 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 用户名
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 账号
     * @return account 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 最后操作时间
     * @return last_time 最后操作时间
     */
    public String getLastTime() {
        return lastTime;
    }

    /**
     * 最后操作时间
     * @param lastTime 最后操作时间
     */
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }

    /**
     * 最后登录时间
     * @return last_login 最后登录时间
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * 最后登录时间
     * @param lastLogin 最后登录时间
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin == null ? null : lastLogin.trim();
    }

    /**
     * 操作次数
     * @return opt_times 操作次数
     */
    public Integer getOptTimes() {
        return optTimes;
    }

    /**
     * 操作次数
     * @param optTimes 操作次数
     */
    public void setOptTimes(Integer optTimes) {
        this.optTimes = optTimes;
    }

    /**
     * 金币数
     * @return gold 金币数
     */
    public Integer getGold() {
        return gold;
    }

    /**
     * 金币数
     * @param gold 金币数
     */
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    /**
     * 等级
     * @return level 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 等级
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 头像地址
     * @return img 头像地址
     */
    public String getImg() {
        return img;
    }

    /**
     * 头像地址
     * @param img 头像地址
     */
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    /**
     * 备注
     * @return marker 备注
     */
    public String getMarker() {
        return marker;
    }

    /**
     * 备注
     * @param marker 备注
     */
    public void setMarker(String marker) {
        this.marker = marker == null ? null : marker.trim();
    }
}