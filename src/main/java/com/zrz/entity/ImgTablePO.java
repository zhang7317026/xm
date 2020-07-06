package com.zrz.entity;

public class ImgTablePO {
    /**
     * 主键
     */
    private String id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * URL地址
     */
    private String url;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String creater;

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
     * 文件名称
     * @return file_name 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名称
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * URL地址
     * @return url URL地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL地址
     * @param url URL地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
     * 创建人
     * @return creater 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 创建人
     * @param creater 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}