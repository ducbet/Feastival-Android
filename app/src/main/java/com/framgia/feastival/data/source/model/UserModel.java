package com.framgia.feastival.data.source.model;
/**
 * Created by thanh.tv on 8/7/2017.
 */
import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class UserModel {
    @SerializedName("message")
    @Expose
    private String mMessage;
    @SerializedName("user_info")
    @Expose
    private UserInfo mUserInfo;

    /**
     * No args constructor for use in serialization
     */
    public UserModel() {
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.mUserInfo = userInfo;
    }

    /**
     *
     */
    public class Profile extends BaseObservable {
        @SerializedName("id")
        @Expose
        private int mId;
        @SerializedName("user_id")
        @Expose
        private int mUserId;
        @SerializedName("name")
        @Expose
        private String mName;
        @SerializedName("birthday")
        @Expose
        private String mBirthday;
        @SerializedName("phonenumber")
        @Expose
        private String mPhonenumber;
        @SerializedName("gender")
        @Expose
        private String mGender;
        @SerializedName("job")
        @Expose
        private String mJob;
        @SerializedName("avatar")
        @Expose
        private String mAvatar;
        @SerializedName("description")
        @Expose
        private String mDescription;
        @SerializedName("created_at")
        @Expose
        private String mCreatedAt;
        @SerializedName("updated_at")
        @Expose
        private String mUpdatedAt;

        public Profile() {
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public int getUserId() {
            return mUserId;
        }

        public void setUserId(int userId) {
            mUserId = userId;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getBirthday() {
            return mBirthday;
        }

        public void setBirthday(String birthday) {
            mBirthday = birthday;
        }

        public String getPhonenumber() {
            return mPhonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            mPhonenumber = phonenumber;
        }

        public String getGender() {
            return mGender;
        }

        public void setGender(String gender) {
            mGender = gender;
        }

        public String getJob() {
            return mJob;
        }

        public void setJob(String job) {
            mJob = job;
        }

        public String getAvatar() {
            return mAvatar;
        }

        public void setAvatar(String avatar) {
            mAvatar = avatar;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }
    }

    /**
     *
     */
    public class UserInfo extends BaseObservable {
        @SerializedName("id")
        @Expose
        private int mId;
        @SerializedName("email")
        @Expose
        private String mEmail;
        @SerializedName("profile")
        @Expose
        private Profile mProfile;

        public UserInfo() {
        }

        public int getId() {
            return mId;
        }

        public void setId(Integer id) {
            mId = id;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        public Profile getProfile() {
            return mProfile;
        }

        public void setProfile(Profile profile) {
            mProfile = profile;
        }
    }
}


