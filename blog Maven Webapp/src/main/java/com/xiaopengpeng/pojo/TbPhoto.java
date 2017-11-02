package com.xiaopengpeng.pojo;

import java.util.Date;

public class TbPhoto {
    private Integer id;

    private Date create_time;

    private String photo_url;

    private String photo_alt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url == null ? null : photo_url.trim();
    }

    public String getPhoto_alt() {
        return photo_alt;
    }

    public void setPhoto_alt(String photo_alt) {
        this.photo_alt = photo_alt == null ? null : photo_alt.trim();
    }
}