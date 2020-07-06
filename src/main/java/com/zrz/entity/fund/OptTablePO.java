package com.zrz.entity.fund;

public class OptTablePO {
    /**
     * 主键
     */
    private String id;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 策略id
     */
    private String policyId;

    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 日期
     */
    private String date0;

    /**
     * 前一日的close值
     */
    private Double beforeClose;

    /**
     * 当前close值
     */
    private Double nowClose;

    /**
     * 交易价格
     */
    private Double dealPrice;

    /**
     * 交易份数
     */
    private Double dealAccount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 当前持有总份数
     */
    private Double sumAccount;

    /**
     * 当前持有份额的总市值
     */
    private Double nowMoney;

    /**
     * 本周期投入总值
     */
    private Double inputMoney;

    /**
     * 本周期浮动金额
     */
    private Double floatMoney;

    /**
     * 本周期浮动比例
     */
    private Double floatRate;

    /**
     * 当日close值
     */
    private Double close0;

    /**
     * 20日最高值
     */
    private Double closeMax;

    /**
     * 回调阈值比例
     */
    private Double backRate;

    /**
     * 年线偏移比例标准值
     */
    private Double deviateStandard;

    /**
     * 年线偏移率
     */
    private Double deviateRate;

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
     * 活期余额利率
     */
    private Double surplusInterest;

    /**
     * 总盈利额
     */
    private Double makeAll;

    /**
     * 持有0天的份额
     */
    private Double day0;

    /**
     * 持有1天的份额
     */
    private Double day1;

    /**
     * 持有2天的份额
     */
    private Double day2;

    /**
     * 持有3天的份额
     */
    private Double day3;

    /**
     * 持有4天的份额
     */
    private Double day4;

    /**
     * 持有5天的份额
     */
    private Double day5;

    /**
     * 持有6天的份额
     */
    private Double day6;

    /**
     * 持有满7天的份额,可交易
     */
    private Double day7;

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
     * @return id 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 操作类型
     * @return type 操作类型
     */
    public String getType() {
        return type;
    }

    /**
     * 操作类型
     * @param type 操作类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 策略id
     * @return policy_id 策略id
     */
    public String getPolicyId() {
        return policyId;
    }

    /**
     * 策略id
     * @param policyId 策略id
     */
    public void setPolicyId(String policyId) {
        this.policyId = policyId == null ? null : policyId.trim();
    }

    /**
     * 基金代码
     * @return fund_code 基金代码
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * 基金代码
     * @param fundCode 基金代码
     */
    public void setFundCode(String fundCode) {
        this.fundCode = fundCode == null ? null : fundCode.trim();
    }

    /**
     * 日期
     * @return date0 日期
     */
    public String getDate0() {
        return date0;
    }

    /**
     * 日期
     * @param date0 日期
     */
    public void setDate0(String date0) {
        this.date0 = date0 == null ? null : date0.trim();
    }

    /**
     * 前一日的close值
     * @return before_close 前一日的close值
     */
    public Double getBeforeClose() {
        return beforeClose;
    }

    /**
     * 前一日的close值
     * @param beforeClose 前一日的close值
     */
    public void setBeforeClose(Double beforeClose) {
        this.beforeClose = beforeClose;
    }

    /**
     * 当前close值
     * @return now_close 当前close值
     */
    public Double getNowClose() {
        return nowClose;
    }

    /**
     * 当前close值
     * @param nowClose 当前close值
     */
    public void setNowClose(Double nowClose) {
        this.nowClose = nowClose;
    }

    /**
     * 交易价格
     * @return deal_price 交易价格
     */
    public Double getDealPrice() {
        return dealPrice;
    }

    /**
     * 交易价格
     * @param dealPrice 交易价格
     */
    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }

    /**
     * 交易份数
     * @return deal_account 交易份数
     */
    public Double getDealAccount() {
        return dealAccount;
    }

    /**
     * 交易份数
     * @param dealAccount 交易份数
     */
    public void setDealAccount(Double dealAccount) {
        this.dealAccount = dealAccount;
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
     * 当前持有总份数
     * @return sum_account 当前持有总份数
     */
    public Double getSumAccount() {
        return sumAccount;
    }

    /**
     * 当前持有总份数
     * @param sumAccount 当前持有总份数
     */
    public void setSumAccount(Double sumAccount) {
        this.sumAccount = sumAccount;
    }

    /**
     * 当前持有份额的总市值
     * @return now_money 当前持有份额的总市值
     */
    public Double getNowMoney() {
        return nowMoney;
    }

    /**
     * 当前持有份额的总市值
     * @param nowMoney 当前持有份额的总市值
     */
    public void setNowMoney(Double nowMoney) {
        this.nowMoney = nowMoney;
    }

    /**
     * 本周期投入总值
     * @return input_money 本周期投入总值
     */
    public Double getInputMoney() {
        return inputMoney;
    }

    /**
     * 本周期投入总值
     * @param inputMoney 本周期投入总值
     */
    public void setInputMoney(Double inputMoney) {
        this.inputMoney = inputMoney;
    }

    /**
     * 本周期浮动金额
     * @return float_money 本周期浮动金额
     */
    public Double getFloatMoney() {
        return floatMoney;
    }

    /**
     * 本周期浮动金额
     * @param floatMoney 本周期浮动金额
     */
    public void setFloatMoney(Double floatMoney) {
        this.floatMoney = floatMoney;
    }

    /**
     * 本周期浮动比例
     * @return float_rate 本周期浮动比例
     */
    public Double getFloatRate() {
        return floatRate;
    }

    /**
     * 本周期浮动比例
     * @param floatRate 本周期浮动比例
     */
    public void setFloatRate(Double floatRate) {
        this.floatRate = floatRate;
    }

    /**
     * 当日close值
     * @return close0 当日close值
     */
    public Double getClose0() {
        return close0;
    }

    /**
     * 当日close值
     * @param close0 当日close值
     */
    public void setClose0(Double close0) {
        this.close0 = close0;
    }

    /**
     * 20日最高值
     * @return close_max 20日最高值
     */
    public Double getCloseMax() {
        return closeMax;
    }

    /**
     * 20日最高值
     * @param closeMax 20日最高值
     */
    public void setCloseMax(Double closeMax) {
        this.closeMax = closeMax;
    }

    /**
     * 回调阈值比例
     * @return back_rate 回调阈值比例
     */
    public Double getBackRate() {
        return backRate;
    }

    /**
     * 回调阈值比例
     * @param backRate 回调阈值比例
     */
    public void setBackRate(Double backRate) {
        this.backRate = backRate;
    }

    /**
     * 年线偏移比例标准值
     * @return deviate_standard 年线偏移比例标准值
     */
    public Double getDeviateStandard() {
        return deviateStandard;
    }

    /**
     * 年线偏移比例标准值
     * @param deviateStandard 年线偏移比例标准值
     */
    public void setDeviateStandard(Double deviateStandard) {
        this.deviateStandard = deviateStandard;
    }

    /**
     * 年线偏移率
     * @return deviate_rate 年线偏移率
     */
    public Double getDeviateRate() {
        return deviateRate;
    }

    /**
     * 年线偏移率
     * @param deviateRate 年线偏移率
     */
    public void setDeviateRate(Double deviateRate) {
        this.deviateRate = deviateRate;
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
     * 持有0天的份额
     * @return day0 持有0天的份额
     */
    public Double getDay0() {
        return day0;
    }

    /**
     * 持有0天的份额
     * @param day0 持有0天的份额
     */
    public void setDay0(Double day0) {
        this.day0 = day0;
    }

    /**
     * 持有1天的份额
     * @return day1 持有1天的份额
     */
    public Double getDay1() {
        return day1;
    }

    /**
     * 持有1天的份额
     * @param day1 持有1天的份额
     */
    public void setDay1(Double day1) {
        this.day1 = day1;
    }

    /**
     * 持有2天的份额
     * @return day2 持有2天的份额
     */
    public Double getDay2() {
        return day2;
    }

    /**
     * 持有2天的份额
     * @param day2 持有2天的份额
     */
    public void setDay2(Double day2) {
        this.day2 = day2;
    }

    /**
     * 持有3天的份额
     * @return day3 持有3天的份额
     */
    public Double getDay3() {
        return day3;
    }

    /**
     * 持有3天的份额
     * @param day3 持有3天的份额
     */
    public void setDay3(Double day3) {
        this.day3 = day3;
    }

    /**
     * 持有4天的份额
     * @return day4 持有4天的份额
     */
    public Double getDay4() {
        return day4;
    }

    /**
     * 持有4天的份额
     * @param day4 持有4天的份额
     */
    public void setDay4(Double day4) {
        this.day4 = day4;
    }

    /**
     * 持有5天的份额
     * @return day5 持有5天的份额
     */
    public Double getDay5() {
        return day5;
    }

    /**
     * 持有5天的份额
     * @param day5 持有5天的份额
     */
    public void setDay5(Double day5) {
        this.day5 = day5;
    }

    /**
     * 持有6天的份额
     * @return day6 持有6天的份额
     */
    public Double getDay6() {
        return day6;
    }

    /**
     * 持有6天的份额
     * @param day6 持有6天的份额
     */
    public void setDay6(Double day6) {
        this.day6 = day6;
    }

    /**
     * 持有满7天的份额,可交易
     * @return day7 持有满7天的份额,可交易
     */
    public Double getDay7() {
        return day7;
    }

    /**
     * 持有满7天的份额,可交易
     * @param day7 持有满7天的份额,可交易
     */
    public void setDay7(Double day7) {
        this.day7 = day7;
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