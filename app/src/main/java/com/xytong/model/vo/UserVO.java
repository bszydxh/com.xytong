package com.xytong.model.vo;

import android.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xytong.model.dto.UserResponseDTO;

import java.io.Serializable;

public class UserVO implements Serializable {
    private String name = "未登录";
    private Integer id = -1;
    private String phoneNumber = "null";
    private String signature = "登录/注册";
    private int gender = -1;
    private Long birthday = -1L;
    private String email = "null";
    private String userAvatarUrl = "";

    public UserVO() {
    }

    public static UserVO init(UserResponseDTO userResponseDTO) {
        if (userResponseDTO == null) {
            Log.i("init", "get user dto error");
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setName(userResponseDTO.getUsername());
        userVO.setBirthday(userResponseDTO.getBirthday_timestamp());
        userVO.setSexInteger(userResponseDTO.getGender());
        userVO.setEmail(userResponseDTO.getEmail());
        userVO.setUserAvatarUrl(userResponseDTO.getAvatar());
        userVO.setPhoneNumber(userResponseDTO.getPhone());
        userVO.setSignature(userResponseDTO.getSignature());
        return userVO;
    }

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

    public String setSexInteger(String str) {
        switch (str) {
            case "unknown":
                gender = -1;
                break;
            case "female":
                gender = 0;
                break;
            case "male":
                gender = 1;
                break;
        }
        return str;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getSignature() {
        return this.signature;
    }

    public int getGender() {
        return this.gender;
    }

    public Long getBirthday() {
        return this.birthday;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUserAvatarUrl() {
        return this.userAvatarUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserVO)) return false;
        final UserVO other = (UserVO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$signature = this.getSignature();
        final Object other$signature = other.getSignature();
        if (this$signature == null ? other$signature != null : !this$signature.equals(other$signature)) return false;
        if (this.getGender() != other.getGender()) return false;
        final Object this$birthday = this.getBirthday();
        final Object other$birthday = other.getBirthday();
        if (this$birthday == null ? other$birthday != null : !this$birthday.equals(other$birthday)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$userAvatarUrl = this.getUserAvatarUrl();
        final Object other$userAvatarUrl = other.getUserAvatarUrl();
        if (this$userAvatarUrl == null ? other$userAvatarUrl != null : !this$userAvatarUrl.equals(other$userAvatarUrl))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $signature = this.getSignature();
        result = result * PRIME + ($signature == null ? 43 : $signature.hashCode());
        result = result * PRIME + this.getGender();
        final Object $birthday = this.getBirthday();
        result = result * PRIME + ($birthday == null ? 43 : $birthday.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $userAvatarUrl = this.getUserAvatarUrl();
        result = result * PRIME + ($userAvatarUrl == null ? 43 : $userAvatarUrl.hashCode());
        return result;
    }

    public String toString() {
        return "UserVO(name=" + this.getName() + ", id=" + this.getId() + ", phoneNumber=" + this.getPhoneNumber() + ", signature=" + this.getSignature() + ", gender=" + this.getGender() + ", birthday=" + this.getBirthday() + ", email=" + this.getEmail() + ", userAvatarUrl=" + this.getUserAvatarUrl() + ")";
    }
}
