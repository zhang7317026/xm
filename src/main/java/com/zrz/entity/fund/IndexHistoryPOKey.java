package com.zrz.entity.fund;

public class IndexHistoryPOKey {
    /**
     * 指数代码
     */
    private String indexCode;

    /**
     * 日期
     */
    private String date0;

    /**
     * 指数代码
     * @return index_code 指数代码
     */
    public String getIndexCode() {
        return indexCode;
    }

    /**
     * 指数代码
     * @param indexCode 指数代码
     */
    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode == null ? null : indexCode.trim();
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