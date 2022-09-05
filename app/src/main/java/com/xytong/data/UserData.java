package com.xytong.data;

import java.io.Serializable;

public class UserData implements Serializable {
    private String name = "null";
    private Integer id = -1;
    private String phoneNumber = "null";
    private int gender = -1;
    private Long birthday = -1L;
    private String email = "null";
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getGender() {
        return gender;
    }

    public String getSexString() {
        String str = "未知";
        switch (gender) {
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

    public void setId(Integer id) {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
