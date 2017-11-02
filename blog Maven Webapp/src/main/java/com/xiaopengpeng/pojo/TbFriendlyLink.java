package com.xiaopengpeng.pojo;

import java.util.Date;

public class TbFriendlyLink {
    private Integer id;

    private String link_name;

    private String link_url;

    private Date link_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink_name() {
        return link_name;
    }

    public void setLink_name(String link_name) {
        this.link_name = link_name == null ? null : link_name.trim();
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url == null ? null : link_url.trim();
    }

    public Date getLink_time() {
        return link_time;
    }

    public void setLink_time(Date link_time) {
        this.link_time = link_time;
    }
}