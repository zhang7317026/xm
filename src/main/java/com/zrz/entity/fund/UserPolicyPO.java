package com.zrz.entity.fund;

public class UserPolicyPO {
    /**
     * 主键
     */
    private String policyId;

    /**
     * 策略名称
     */
    private String policyName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 总投入
     */
    private Double inputAll;

    /**
     * 当前总值
     */
    private Double nowAll;

    /**
     * 活期余额
     */
    private Double surplus;

    /**
     * 总盈利额
     */
    private Double makeAll;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 当前周期开始日期
     */
    private String startDate;

    /**
     * 上一交易日
     */
    private String lastDate;

    /**
     * 备注
     */
    private String mark;

    /**
     * 活期余额利率
     */
    private Double surplusInterest;

    /**
     * 投入周期
     */
    private String inputPeriod;

    /**
     * 每份投入
     */
    private Double inputPer;

    /**
     * 更新日期
     */
    private String updateDate;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 主键
     * @return policy_id 主键
     */
    public String getPolicyId() {
        return policyId;
    }

    /**
     * 主键
     * @param policyId 主键
     */
    public void setPolicyId(String policyId) {
        this.policyId = policyId == null ? null : policyId.trim();
    }

    /**
     * 策略名称
     * @return policy_name 策略名称
     */
    public String getPolicyName() {
        return policyName;
    }

    /**
     * 策略名称
     * @param policyName 策略名称
     */
    public void setPolicyName(String policyName) {
        this.policyName = policyName == null ? null : policyName.trim();
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 总投入
     * @return input_all 总投入
     */
    public Double getInputAll() {
        return inputAll;
    }

    /**
     * 总投入
     * @param inputAll 总投入
     */
    public void setInputAll(Double inputAll) {
        this.inputAll = inputAll;
    }

    /**
     * 当前总值
     * @return now_all 当前总值
     */
    public Double getNowAll() {
        return nowAll;
    }

    /**
     * 当前总值
     * @param nowAll 当前总值
     */
    public void setNowAll(Double nowAll) {
        this.nowAll = nowAll;
    }

    /**
     * 活期余额
     * @return surplus 活期余额
     */
    public Double getSurplus() {
        return surplus;
    }

    /**
     * 活期余额
     * @param surplus 活期余额
     */
    public void setSurplus(Double surplus) {
        this.surplus = surplus;
    }

    /**
     * 总盈利额
     * @return make_all 总盈利额
     */
    public Double getMakeAll() {
        return makeAll;
    }

    /**
     * 总盈利额
     * @param makeAll 总盈利额
     */
    public void setMakeAll(Double makeAll) {
        this.makeAll = makeAll;
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
     * 当前周期开始日期
     * @return start_date 当前周期开始日期
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 当前周期开始日期
     * @param startDate 当前周期开始日期
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    /**
     * 上一交易日
     * @return last_date 上一交易日
     */
    public String getLastDate() {
        return lastDate;
    }

    /**
     * 上一交易日
     * @param lastDate 上一交易日
     */
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate == null ? null : lastDate.trim();
    }

    /**
     * 备注
     * @return mark 备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 备注
     * @param mark 备注
     */
    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    /**
     * 活期余额利率
     * @return surplus_interest 活期余额利率
     */
    public Double getSurplusInterest() {
        return surplusInterest;
    }

    /**
     * 活期余额利率
     * @param surplusInterest 活期余额利率
     */
    public void setSurplusInterest(Double surplusInterest) {
        this.surplusInterest = surplusInterest;
    }

    /**
     * 投入周期
     * @return input_period 投入周期
     */
    public String getInputPeriod() {
        return inputPeriod;
    }

    /**
     * 投入周期
     * @param inputPeriod 投入周期
     */
    public void setInputPeriod(String inputPeriod) {
        this.inputPeriod = inputPeriod == null ? null : inputPeriod.trim();
    }

    /**
     * 每份投入
     * @return input_per 每份投入
     */
    public Double getInputPer() {
        return inputPer;
    }

    /**
     * 每份投入
     * @param inputPer 每份投入
     */
    public void setInputPer(Double inputPer) {
        this.inputPer = inputPer;
    }

    /**
     * 更新日期
     * @return update_date 更新日期
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * 更新日期
     * @param updateDate 更新日期
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * 状态
     * @return status 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}