package com.xytong.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserData implements Serializable {
    private String name = "null";
    private Integer id = -1;
    private String phoneNumber = "null";
    private String signature = "null";
    private int gender = -1;
    private Long birthday = -1L;
    private String email = "null";
    private String userAvatarUrl = "";
    @JsonIgnore
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

}
