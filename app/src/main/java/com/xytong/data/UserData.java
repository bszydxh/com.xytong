package com.xytong.data;

import java.io.Serializable;

public class UserData implements Serializable {
    enum SEX {
        female,
        male
    }

    private String name = "null";
    private String id = "null";
    private String phoneNumber = "null";
    private int sex = -1;
    private Long birthday = -1L;
    private String email = "null";
    private String password = "null";
    private String userAvatarUrl = null;

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public Long getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getSex() {
        return sex;
    }

    public String getSexString() {
        String str = "未知";
        switch (sex) {
            case -1:
                str = "未知";
                break;
            case 0:
                str = "女";
                break;
            case 1:
                str = "男";
                break;
        }
        return str;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
