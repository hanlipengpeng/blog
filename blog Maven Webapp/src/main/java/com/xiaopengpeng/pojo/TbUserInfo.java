package com.xiaopengpeng.pojo;

public class TbUserInfo {
    private Integer id;

    private String login_name;

    private String login_password;

    private String net_name;

    private String job;

    private String address;

    private String phone_num;

    private String email;

    private String head_url;

    private String introduce_myself;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name == null ? null : login_name.trim();
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password == null ? null : login_password.trim();
    }

    public String getNet_name() {
        return net_name;
    }

    public void setNet_name(String net_name) {
        this.net_name = net_name == null ? null : net_name.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num == null ? null : phone_num.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url == null ? null : head_url.trim();
    }

    public String getIntroduce_myself() {
        return introduce_myself;
    }

    public void setIntroduce_myself(String introduce_myself) {
        this.introduce_myself = introduce_myself == null ? null : introduce_myself.trim();
    }
}