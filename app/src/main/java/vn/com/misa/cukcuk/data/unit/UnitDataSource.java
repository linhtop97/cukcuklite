package vn.com.misa.cukcuk.data.unit;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.db.IDBUtils;
import vn.com.misa.cukcuk.data.db.SQLiteDBManager;
import vn.com.misa.cukcuk.data.model.Unit;

/**
 * Lớp thao tác với các đơn vị món ăn trong cơ sở dũ liệu
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class UnitDataSource implements IUnitDataSource, IDBUtils.ITableUnitUtils {

    private static final String TAG = "UnitDataSource";
    private SQLiteDBManager mSQLiteDBManager;

    /**
     * Phương thức khởi tạo cho đối tượng UnitDataSource
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public UnitDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
    }

    /**
     * Phương thức thêm đơn vị món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - tên đơn vị món ăn
     * @return - thêm đơn vị món ăn thàn công hay thất bại
     */
    @Override
    public boolean addUnit(String unitName) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UNIT_NAME, unitName);
            return mSQLiteDBManager.addNewRecord(UNIT_TBL_NAME, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức thêm mới đơn vị món ăn có kiểm tra sự tồn tại của đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - tên đơn vị
     * @return - thêm mới đơn vị món ăn thành công, thất bại hay đã tồn tại đơn vị
     */
    @Override
    public EnumResult addUnitToDatabase(String unitName) {
        if (unitName != null) {
            //kiểm tra tên đơn vị đã tồn tại hay chưa
            if (isUnitIfExists(unitName)) {
                return EnumResult.Exists;
            } else {
                if (addUnit(unitName)) {
                    return EnumResult.Success;
                }
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức xóa đơn vị thông qua id của đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     * @return - xóa đơn vị thành công hay thất bại
     */
    @Override
    public boolean deleteUnit(Unit unit) {
        try {
            return mSQLiteDBManager.deleteRecord(UNIT_TBL_NAME,
                    COLUMN_UNIT_ID + "=?", new String[]{String.valueOf(unit.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     * @return - cập nhật đơn vị thành công hay thất bại
     */
    @Override
    public boolean updateUnit(Unit unit) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UNIT_NAME, unit.getUnit());
            return mSQLiteDBManager.updateRecord(UNIT_TBL_NAME, contentValues,
                    COLUMN_UNIT_ID + "=?", new String[]{String.valueOf(unit.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật đơn vị có kiểm tra tên của đơn vị
     *
     * @param unit - đơn vị
     * @return - cập nhật đơn vị thành công, thất bại
     */
    @Override
    public EnumResult updateUnitToDatabase(Unit unit) {
        List<Unit> units = getAllUnit();
        int size = units.size();
        boolean unitNameIsExists = false;
        for (int i = 0; i < size; i++) {
            if (units.get(i).getUnit().toLowerCase().equals(unit.getUnit())
                    && (units.get(i).getId() != unit.getId())) {
                unitNameIsExists = true;
                break;
            }
        }
        if (unitNameIsExists) {
            return EnumResult.Exists;
        } else {
            if (updateUnit(unit)) {
                return EnumResult.Success;
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức lấy tất cả tên các đơn vị trong đã có trong cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách tên các đơn vị
     */
    @Override
    public List<String> getAllUnitName() {
        List<String> unitNames = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_UNIT_NAME + " from " + UNIT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String unitName = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME));
                unitNames.add(unitName);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitNames;
    }

    /**
     * Phương thức lấy ra tất cả các đơn vị có trong cở sở dữ liệu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách đơn vị
     */
    @Override
    public List<Unit> getAllUnit() {
        List<Unit> units = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + UNIT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Unit unit = new Unit(cursor.getInt(cursor.getColumnIndex(COLUMN_UNIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME)));
                units.add(unit);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return units;
    }

    /**
     * Phương thức kiểm tra đơn vị đã tồn tại trong cơ sở dữ liệu hay chưa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - đơn vị
     * @return - đơn vị đã hay chưa tồn tại
     */
    @Override
    public boolean isUnitIfExists(String unitName) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords(UNIT_TBL_NAME, new String[]{COLUMN_UNIT_NAME},
                    "lower(" + COLUMN_UNIT_NAME + ")" + " = ? ", new String[]{unitName.toLowerCase()},
                    null, null, null);
            int unitNameRecord = cursor.getCount();
            cursor.close();
            return unitNameRecord > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
