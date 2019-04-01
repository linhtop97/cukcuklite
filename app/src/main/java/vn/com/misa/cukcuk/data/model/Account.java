package vn.com.misa.cukcuk.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Lớp tài khoản
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class Account implements Parcelable {
    private int mId;
    private String mUsername;
    private String mPassword;

    public Account(int id, String username, String password) {
        mId = id;
        mUsername = username;
        mPassword = password;
    }

    private Account(Builder builder) {
        mId = builder.mId;
        mUsername = builder.mUsername;
        mPassword = builder.mPassword;
    }

    protected Account(Parcel in) {
        mId = in.readInt();
        mUsername = in.readString();
        mPassword = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mUsername);
        dest.writeString(mPassword);
    }

    public static class Builder {
        private int mId;
        private String mUsername;
        private String mPassword;

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setUsername(String username) {
            mUsername = username;
            return this;
        }

        public Builder setPassword(String password) {
            mPassword = password;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
