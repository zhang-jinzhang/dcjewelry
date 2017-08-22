package com.ceyi.project.dcjewelry.upload;

/**
 * Created by lingh on 2017/4/6.
 */
public class UploadResult {
    private String state;
    private String url;

    public UploadResult(String url) {
        this("SUCCESS", url);
    }

    public UploadResult(String state, String url) {
        this.state = state;
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
