package com.zrz.entity.fund;

public class FundHistoryPO extends FundHistoryPOKey {
    /**
     * 及时点数
     */
    private Double nowClose;

    /**
     * 收盘点数
     */
    private Double close0;

    /**
     * 及时浮动百分之
     */
    private Double nowFloat;

    /**
     * 浮动百分之
     */
    private Double float0;

    /**
     * 过去交易日平均值5日线
     */
    private Double avg5;

    /**
     * 过去交易日平均值10日线
     */
    private Double avg10;

    /**
     * 过去交易日平均值10日线
     */
    private Double avg20;

    /**
     * 过去交易日平均值60日线
     */
    private Double avg60;

    /**
     * 过去交易日平均值120日线
     */
    private Double avg120;

    /**
     * 年线过去250交易日平均值
     */
    private Double avg250;

    /**
     * 添加时间
     */
    private String createTime;

    /**
     * 及时点数
     * @return now_close 及时点数
     */
    public Double getNowClose() {
        return nowClose;
    }

    /**
     * 及时点数
     * @param nowClose 及时点数
     */
    public void setNowClose(Double nowClose) {
        this.nowClose = nowClose;
    }

    /**
     * 收盘点数
     * @return close0 收盘点数
     */
    public Double getClose0() {
        return close0;
    }

    /**
     * 收盘点数
     * @param close0 收盘点数
     */
    public void setClose0(Double close0) {
        this.close0 = close0;
    }

    /**
     * 及时浮动百分之
     * @return now_float 及时浮动百分之
     */
    public Double getNowFloat() {
        return nowFloat;
    }

    /**
     * 及时浮动百分之
     * @param nowFloat 及时浮动百分之
     */
    public void setNowFloat(Double nowFloat) {
        this.nowFloat = nowFloat;
    }

    /**
     * 浮动百分之
     * @return float0 浮动百分之
     */
    public Double getFloat0() {
        return float0;
    }

    /**
     * 浮动百分之
     * @param float0 浮动百分之
     */
    public void setFloat0(Double float0) {
        this.float0 = float0;
    }

    /**
     * 过去交易日平均值5日线
     * @return avg5 过去交易日平均值5日线
     */
    public Double getAvg5() {
        return avg5;
    }

    /**
     * 过去交易日平均值5日线
     * @param avg5 过去交易日平均值5日线
     */
    public void setAvg5(Double avg5) {
        this.avg5 = avg5;
    }

    /**
     * 过去交易日平均值10日线
     * @return avg10 过去交易日平均值10日线
     */
    public Double getAvg10() {
        return avg10;
    }

    /**
     * 过去交易日平均值10日线
     * @param avg10 过去交易日平均值10日线
     */
    public void setAvg10(Double avg10) {
        this.avg10 = avg10;
    }

    /**
     * 过去交易日平均值10日线
     * @return avg20 过去交易日平均值10日线
     */
    public Double getAvg20() {
        return avg20;
    }

    /**
     * 过去交易日平均值10日线
     * @param avg20 过去交易日平均值10日线
     */
    public void setAvg20(Double avg20) {
        this.avg20 = avg20;
    }

    /**
     * 过去交易日平均值60日线
     * @return avg60 过去交易日平均值60日线
     */
    public Double getAvg60() {
        return avg60;
    }

    /**
     * 过去交易日平均值60日线
     * @param avg60 过去交易日平均值60日线
     */
    public void setAvg60(Double avg60) {
        this.avg60 = avg60;
    }

    /**
     * 过去交易日平均值120日线
     * @return avg120 过去交易日平均值120日线
     */
    public Double getAvg120() {
        return avg120;
    }

    /**
     * 过去交易日平均值120日线
     * @param avg120 过去交易日平均值120日线
     */
    public void setAvg120(Double avg120) {
        this.avg120 = avg120;
    }

    /**
     * 年线过去250交易日平均值
     * @return avg250 年线过去250交易日平均值
     */
    public Double getAvg250() {
        return avg250;
    }

    /**
     * 年线过去250交易日平均值
     * @param avg250 年线过去250交易日平均值
     */
    public void setAvg250(Double avg250) {
        this.avg250 = avg250;
    }

    /**
     * 添加时间
     * @return create_time 添加时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 添加时间
     * @param createTime 添加时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}