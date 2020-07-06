package com.zrz.entity.fund;

public class FundHistoryPOKey {
    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 日期
     */
    private String date0;

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
}