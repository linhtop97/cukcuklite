package vn.com.misa.cukcuk.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Lớp món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class Dish implements Parcelable {
    private int mId;
    private String mName;
    private int mPrice;
    private String mUnit;
    private String mColor;
    private String mIcon;

    private boolean mIsSale;

    public Dish() {

    }

    public Dish(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mPrice = builder.mPrice;
        mUnit = builder.mUnit;
        mColor = builder.mColor;
        mIcon = builder.mIcon;
        mIsSale = builder.mIsSale;
    }

    protected Dish(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mPrice = in.readInt();
        mUnit = in.readString();
        mColor = in.readString();
        mIcon = in.readString();
        mIsSale = in.readByte() != 0;
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mPrice);
        dest.writeString(mUnit);
        dest.writeString(mColor);
        dest.writeString(mIcon);
        dest.writeByte((byte) (mIsSale ? 1 : 0));
    }

    public static class Builder {
        private int mId;
        private String mName;
        private int mPrice;
        private String mUnit;
        private String mColor;
        private String mIcon;
        private boolean mIsSale;

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setPrice(int price) {
            mPrice = price;
            return this;
        }

        public Builder setUnit(String unit) {
            mUnit = unit;
            return this;
        }

        public Builder setColor(String color) {
            mColor = color;
            return this;
        }

        public Builder setIcon(String icon) {
            mIcon = icon;
            return this;
        }

        public Builder setSale(boolean sale) {
            mIsSale = sale;
            return this;
        }

        public Dish build() {
            return new Dish(this);
        }

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public boolean isSale() {
        return mIsSale;
    }

    public void setSale(boolean sale) {
        mIsSale = sale;
    }
}
