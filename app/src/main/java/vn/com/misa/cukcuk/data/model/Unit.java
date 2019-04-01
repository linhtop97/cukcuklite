package vn.com.misa.cukcuk.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Lớp Đơn vị cho món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class Unit implements Parcelable {
    private int mId;
    private String mUnit;

    public Unit(int id, String unit) {
        mId = id;
        mUnit = unit;
    }

    protected Unit(Parcel in) {
        mId = in.readInt();
        mUnit = in.readString();
    }

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mUnit);
    }
}
