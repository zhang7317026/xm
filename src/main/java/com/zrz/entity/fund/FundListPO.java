package com.zrz.entity.fund;

public class FundListPO {
    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 基金代码
     */
    private String fundName;

    /**
     * 数据创建时间
     */
    private String createTime;

    /**
     * 基金规模(亿元)
     */
    private Double scale;

    /**
     * 追踪误差(%)
     */
    private Double errorRanger;

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
     * 基金代码
     * @return fund_name 基金代码
     */
    public String getFundName() {
        return fundName;
    }

    /**
     * 基金代码
     * @param fundName 基金代码
     */
    public void setFundName(String fundName) {
        this.fundName = fundName == null ? null : fundName.trim();
    }

    /**
     * 数据创建时间
     * @return create_time 数据创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 数据创建时间
     * @param createTime 数据创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 基金规模(亿元)
     * @return scale 基金规模(亿元)
     */
    public Double getScale() {
        return scale;
    }

    /**
     * 基金规模(亿元)
     * @param scale 基金规模(亿元)
     */
    public void setScale(Double scale) {
        this.scale = scale;
    }

    /**
     * 追踪误差(%)
     * @return error_ranger 追踪误差(%)
     */
    public Double getErrorRanger() {
        return errorRanger;
    }

    /**
     * 追踪误差(%)
     * @param errorRanger 追踪误差(%)
     */
    public void setErrorRanger(Double errorRanger) {
        this.errorRanger = errorRanger;
    }
}