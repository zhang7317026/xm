package com.zrz.entity.fund;

public class IndexHistoryPO extends IndexHistoryPOKey {
    /**
     * 收盘点数
     */
    private Double close0;

    /**
     * 加权PE
     */
    private Double peAdd;

    /**
     * 加权PE百分位
     */
    private Double peAddRate;

    /**
     * 加权PE机会值(30分位)
     */
    private Double peAdd30;

    /**
     * 加权PE危险值(70分位)
     */
    private Double peAdd70;

    /**
     * 均权PE
     */
    private Double peAvg;

    /**
     * 均权PE百分位
     */
    private Double peAvgRate;

    /**
     * 均权PE机会值(30分位)
     */
    private Double peAvg30;

    /**
     * 均权PE危险值(70分位)
     */
    private Double peAvg70;

    /**
     * 加权PB
     */
    private Double pbAdd;

    /**
     * 加权PB百分位
     */
    private Double pbAddRate;

    /**
     * 加权PB机会值(30分位)
     */
    private Double pbAdd30;

    /**
     * 加权PB危险值(70分位)
     */
    private Double pbAdd70;

    /**
     * 均权PB
     */
    private Double pbAvg;

    /**
     * 均权PB百分位
     */
    private Double pbAvgRate;

    /**
     * 均权PB机会值(30分位)
     */
    private Double pbAvg30;

    /**
     * 均权PB危险值(70分位)
     */
    private Double pbAvg70;

    /**
     * 净资产收益率
     */
    private Double roe;

    /**
     * ROE百分位
     */
    private Double roeAvgRate;

    /**
     * ROE机会值(30分位)
     */
    private Double roe30;

    /**
     * ROE危险值(70分位)
     */
    private Double roe70;

    /**
     * 
     */
    private Double avg5;

    /**
     * 
     */
    private Double avg10;

    /**
     * 
     */
    private Double avg20;

    /**
     * 
     */
    private Double avg60;

    /**
     * 
     */
    private Double avg120;

    /**
     * 
     */
    private Double avg250;

    /**
     * 创建时间
     */
    private String createTime;

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
     * 加权PE
     * @return PE_add 加权PE
     */
    public Double getPeAdd() {
        return peAdd;
    }

    /**
     * 加权PE
     * @param peAdd 加权PE
     */
    public void setPeAdd(Double peAdd) {
        this.peAdd = peAdd;
    }

    /**
     * 加权PE百分位
     * @return PE_add_rate 加权PE百分位
     */
    public Double getPeAddRate() {
        return peAddRate;
    }

    /**
     * 加权PE百分位
     * @param peAddRate 加权PE百分位
     */
    public void setPeAddRate(Double peAddRate) {
        this.peAddRate = peAddRate;
    }

    /**
     * 加权PE机会值(30分位)
     * @return PE_add_30 加权PE机会值(30分位)
     */
    public Double getPeAdd30() {
        return peAdd30;
    }

    /**
     * 加权PE机会值(30分位)
     * @param peAdd30 加权PE机会值(30分位)
     */
    public void setPeAdd30(Double peAdd30) {
        this.peAdd30 = peAdd30;
    }

    /**
     * 加权PE危险值(70分位)
     * @return PE_add_70 加权PE危险值(70分位)
     */
    public Double getPeAdd70() {
        return peAdd70;
    }

    /**
     * 加权PE危险值(70分位)
     * @param peAdd70 加权PE危险值(70分位)
     */
    public void setPeAdd70(Double peAdd70) {
        this.peAdd70 = peAdd70;
    }

    /**
     * 均权PE
     * @return PE_avg 均权PE
     */
    public Double getPeAvg() {
        return peAvg;
    }

    /**
     * 均权PE
     * @param peAvg 均权PE
     */
    public void setPeAvg(Double peAvg) {
        this.peAvg = peAvg;
    }

    /**
     * 均权PE百分位
     * @return PE_avg_rate 均权PE百分位
     */
    public Double getPeAvgRate() {
        return peAvgRate;
    }

    /**
     * 均权PE百分位
     * @param peAvgRate 均权PE百分位
     */
    public void setPeAvgRate(Double peAvgRate) {
        this.peAvgRate = peAvgRate;
    }

    /**
     * 均权PE机会值(30分位)
     * @return PE_avg_30 均权PE机会值(30分位)
     */
    public Double getPeAvg30() {
        return peAvg30;
    }

    /**
     * 均权PE机会值(30分位)
     * @param peAvg30 均权PE机会值(30分位)
     */
    public void setPeAvg30(Double peAvg30) {
        this.peAvg30 = peAvg30;
    }

    /**
     * 均权PE危险值(70分位)
     * @return PE_avg_70 均权PE危险值(70分位)
     */
    public Double getPeAvg70() {
        return peAvg70;
    }

    /**
     * 均权PE危险值(70分位)
     * @param peAvg70 均权PE危险值(70分位)
     */
    public void setPeAvg70(Double peAvg70) {
        this.peAvg70 = peAvg70;
    }

    /**
     * 加权PB
     * @return PB_add 加权PB
     */
    public Double getPbAdd() {
        return pbAdd;
    }

    /**
     * 加权PB
     * @param pbAdd 加权PB
     */
    public void setPbAdd(Double pbAdd) {
        this.pbAdd = pbAdd;
    }

    /**
     * 加权PB百分位
     * @return PB_add_rate 加权PB百分位
     */
    public Double getPbAddRate() {
        return pbAddRate;
    }

    /**
     * 加权PB百分位
     * @param pbAddRate 加权PB百分位
     */
    public void setPbAddRate(Double pbAddRate) {
        this.pbAddRate = pbAddRate;
    }

    /**
     * 加权PB机会值(30分位)
     * @return PB_add_30 加权PB机会值(30分位)
     */
    public Double getPbAdd30() {
        return pbAdd30;
    }

    /**
     * 加权PB机会值(30分位)
     * @param pbAdd30 加权PB机会值(30分位)
     */
    public void setPbAdd30(Double pbAdd30) {
        this.pbAdd30 = pbAdd30;
    }

    /**
     * 加权PB危险值(70分位)
     * @return PB_add_70 加权PB危险值(70分位)
     */
    public Double getPbAdd70() {
        return pbAdd70;
    }

    /**
     * 加权PB危险值(70分位)
     * @param pbAdd70 加权PB危险值(70分位)
     */
    public void setPbAdd70(Double pbAdd70) {
        this.pbAdd70 = pbAdd70;
    }

    /**
     * 均权PB
     * @return PB_avg 均权PB
     */
    public Double getPbAvg() {
        return pbAvg;
    }

    /**
     * 均权PB
     * @param pbAvg 均权PB
     */
    public void setPbAvg(Double pbAvg) {
        this.pbAvg = pbAvg;
    }

    /**
     * 均权PB百分位
     * @return PB_avg_rate 均权PB百分位
     */
    public Double getPbAvgRate() {
        return pbAvgRate;
    }

    /**
     * 均权PB百分位
     * @param pbAvgRate 均权PB百分位
     */
    public void setPbAvgRate(Double pbAvgRate) {
        this.pbAvgRate = pbAvgRate;
    }

    /**
     * 均权PB机会值(30分位)
     * @return PB_avg_30 均权PB机会值(30分位)
     */
    public Double getPbAvg30() {
        return pbAvg30;
    }

    /**
     * 均权PB机会值(30分位)
     * @param pbAvg30 均权PB机会值(30分位)
     */
    public void setPbAvg30(Double pbAvg30) {
        this.pbAvg30 = pbAvg30;
    }

    /**
     * 均权PB危险值(70分位)
     * @return PB_avg_70 均权PB危险值(70分位)
     */
    public Double getPbAvg70() {
        return pbAvg70;
    }

    /**
     * 均权PB危险值(70分位)
     * @param pbAvg70 均权PB危险值(70分位)
     */
    public void setPbAvg70(Double pbAvg70) {
        this.pbAvg70 = pbAvg70;
    }

    /**
     * 净资产收益率
     * @return ROE 净资产收益率
     */
    public Double getRoe() {
        return roe;
    }

    /**
     * 净资产收益率
     * @param roe 净资产收益率
     */
    public void setRoe(Double roe) {
        this.roe = roe;
    }

    /**
     * ROE百分位
     * @return ROE_avg_rate ROE百分位
     */
    public Double getRoeAvgRate() {
        return roeAvgRate;
    }

    /**
     * ROE百分位
     * @param roeAvgRate ROE百分位
     */
    public void setRoeAvgRate(Double roeAvgRate) {
        this.roeAvgRate = roeAvgRate;
    }

    /**
     * ROE机会值(30分位)
     * @return ROE_30 ROE机会值(30分位)
     */
    public Double getRoe30() {
        return roe30;
    }

    /**
     * ROE机会值(30分位)
     * @param roe30 ROE机会值(30分位)
     */
    public void setRoe30(Double roe30) {
        this.roe30 = roe30;
    }

    /**
     * ROE危险值(70分位)
     * @return ROE_70 ROE危险值(70分位)
     */
    public Double getRoe70() {
        return roe70;
    }

    /**
     * ROE危险值(70分位)
     * @param roe70 ROE危险值(70分位)
     */
    public void setRoe70(Double roe70) {
        this.roe70 = roe70;
    }

    /**
     * 
     * @return avg_5 
     */
    public Double getAvg5() {
        return avg5;
    }

    /**
     * 
     * @param avg5 
     */
    public void setAvg5(Double avg5) {
        this.avg5 = avg5;
    }

    /**
     * 
     * @return avg_10 
     */
    public Double getAvg10() {
        return avg10;
    }

    /**
     * 
     * @param avg10 
     */
    public void setAvg10(Double avg10) {
        this.avg10 = avg10;
    }

    /**
     * 
     * @return avg_20 
     */
    public Double getAvg20() {
        return avg20;
    }

    /**
     * 
     * @param avg20 
     */
    public void setAvg20(Double avg20) {
        this.avg20 = avg20;
    }

    /**
     * 
     * @return avg_60 
     */
    public Double getAvg60() {
        return avg60;
    }

    /**
     * 
     * @param avg60 
     */
    public void setAvg60(Double avg60) {
        this.avg60 = avg60;
    }

    /**
     * 
     * @return avg_120 
     */
    public Double getAvg120() {
        return avg120;
    }

    /**
     * 
     * @param avg120 
     */
    public void setAvg120(Double avg120) {
        this.avg120 = avg120;
    }

    /**
     * 
     * @return avg_250 
     */
    public Double getAvg250() {
        return avg250;
    }

    /**
     * 
     * @param avg250 
     */
    public void setAvg250(Double avg250) {
        this.avg250 = avg250;
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
}