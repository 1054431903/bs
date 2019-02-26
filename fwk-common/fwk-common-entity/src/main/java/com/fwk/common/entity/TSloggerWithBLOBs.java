package com.fwk.common.entity;

public class TSloggerWithBLOBs extends TSlogger {
    private String url;

    private String param;

    private String exceptioninfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public String getExceptioninfo() {
        return exceptioninfo;
    }

    public void setExceptioninfo(String exceptioninfo) {
        this.exceptioninfo = exceptioninfo == null ? null : exceptioninfo.trim();
    }
}