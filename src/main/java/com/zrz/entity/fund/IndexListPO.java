package com.zrz.entity.fund;

public class IndexListPO {
    /**
     * 指数编码
     */
    private String indexCode;

    /**
     * 指数名称
     */
    private String indexName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 指数编码
     * @return index_code 指数编码
     */
    public String getIndexCode() {
        return indexCode;
    }

    /**
     * 指数编码
     * @param indexCode 指数编码
     */
    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode == null ? null : indexCode.trim();
    }

    /**
     * 指数名称
     * @return index_name 指数名称
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * 指数名称
     * @param indexName 指数名称
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName == null ? null : indexName.trim();
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