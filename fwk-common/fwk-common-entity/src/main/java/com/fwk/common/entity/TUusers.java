package com.fwk.common.entity;

import java.util.Date;

public class TUusers {
    private Integer id;

    private String phone;

    private String password;

    private String status;

    private String name;

    private String email;

    private Date lastlogin;

    private String loginaddress;

    private String latestidentifyingcode;

    private String lastlogincode;

    private Date creattime;

    private Integer delstate;

    private String sex;

    private Date birthday;

    private String nickname;

    private Integer photo;

    private String jobnumber;

    private String loginorgid;

    private String addresscode;

    private String addressdetail;

    private Date edittime;

    private String supportid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getLoginaddress() {
        return loginaddress;
    }

    public void setLoginaddress(String loginaddress) {
        this.loginaddress = loginaddress == null ? null : loginaddress.trim();
    }

    public String getLatestidentifyingcode() {
        return latestidentifyingcode;
    }

    public void setLatestidentifyingcode(String latestidentifyingcode) {
        this.latestidentifyingcode = latestidentifyingcode == null ? null : latestidentifyingcode.trim();
    }

    public String getLastlogincode() {
        return lastlogincode;
    }

    public void setLastlogincode(String lastlogincode) {
        this.lastlogincode = lastlogincode == null ? null : lastlogincode.trim();
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Integer getDelstate() {
        return delstate;
    }

    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber == null ? null : jobnumber.trim();
    }

    public String getLoginorgid() {
        return loginorgid;
    }

    public void setLoginorgid(String loginorgid) {
        this.loginorgid = loginorgid == null ? null : loginorgid.trim();
    }

    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode == null ? null : addresscode.trim();
    }

    public String getAddressdetail() {
        return addressdetail;
    }

    public void setAddressdetail(String addressdetail) {
        this.addressdetail = addressdetail == null ? null : addressdetail.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getSupportid() {
        return supportid;
    }

    public void setSupportid(String supportid) {
        this.supportid = supportid == null ? null : supportid.trim();
    }
}