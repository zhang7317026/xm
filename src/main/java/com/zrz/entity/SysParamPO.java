package com.zrz.entity;

public class SysParamPO {
    /**
     * 编码
     */
    private String paramCode;

    /**
     * 内容值
     */
    private String paramValue;

    /**
     * 描述
     */
    private String paramDesc;

    /**
     * 编码
     * @return param_code 编码
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * 编码
     * @param paramCode 编码
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    /**
     * 内容值
     * @return param_value 内容值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 内容值
     * @param paramValue 内容值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    /**
     * 描述
     * @return param_desc 描述
     */
    public String getParamDesc() {
        return paramDesc;
    }

    /**
     * 描述
     * @param paramDesc 描述
     */
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc == null ? null : paramDesc.trim();
    }
}