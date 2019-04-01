package vn.com.misa.cukcuk.data.dish;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.db.IDBUtils;
import vn.com.misa.cukcuk.data.db.SQLiteDBManager;
import vn.com.misa.cukcuk.data.model.Dish;

/**
 * Lớp thao tác với các món ăn trong cơ sở dữ liệu
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DishDataSource implements IDishDataSource, IDBUtils.ITableDishUtils {

    private static final String TAG = "DishDataSource";
    private SQLiteDBManager mSQLiteDBManager;

    public DishDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
    }

    /**
     * Phương thức thêm món ăn vào cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     * @return - thêm món ăn thành công hay thất bại
     */
    @Override
    public boolean addDish(Dish dish) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DISH_NAME, dish.getName());
            contentValues.put(COLUMN_PRICE, dish.getPrice());
            contentValues.put(COLUMN_UNIT, dish.getUnit());
            contentValues.put(COLUMN_COLOR, dish.getColor());
            contentValues.put(COLUMN_ICON, dish.getIcon());
            contentValues.put(COLUMN_IS_SALE, 1);
            return mSQLiteDBManager.addNewRecord(DISH_TBL_NAME, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức thêm mới món ăn vào cơ sở dữ liệu có kiểm tra sự tồn tại của món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     * @return - thêm món ăn thành công, thất bại, hay món ăn đã tồn tại trong thực đơn
     */
    @Override
    public EnumResult addDishToDatabase(Dish dish) {
        String dishName;
        if (dish != null) {
            dishName = dish.getName();
            //kiểm tra tên món ăn đã tồn tại hay chưa
            if (isDishIfExists(dishName)) {
                return EnumResult.Exists;
            } else {
                if (addDish(dish)) {
                    return EnumResult.Success;
                }
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức cập nhật món ăn có kiểm tra tên của món ăn
     *
     * @param dish - món ăn
     * @return - cập nhật món ăn thành công, thất bại
     */
    @Override
    public EnumResult updateDishToDatabase(Dish dish) {
        List<Dish> dishes = getAllDish();
        int size = dishes.size();
        boolean dishNameIsExists = false;
        for (int i = 0; i < size; i++) {
            if (dishes.get(i).getName().toLowerCase().equals(dish.getName())
                    && (dishes.get(i).getId() != dish.getId())) {
                dishNameIsExists = true;
                break;
            }
        }
        if (dishNameIsExists) {
            return EnumResult.Exists;
        } else {
            if (updateDish(dish)) {
                return EnumResult.Success;
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức xóa món ăn thông qua Id của món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishId - id của món ăn
     * @return - xóa món ăn thành công hay thất bại
     */
    @Override
    public boolean deleteDishById(int dishId) {
        try {
            return mSQLiteDBManager.deleteRecord(DISH_TBL_NAME,
                    COLUMN_DISH_ID + "=?", new String[]{String.valueOf(dishId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     * @return - cập nhật món ăn thành công hay thất bại
     */
    @Override
    public boolean updateDish(Dish dish) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DISH_NAME, dish.getName());
            contentValues.put(COLUMN_PRICE, dish.getPrice());
            contentValues.put(COLUMN_UNIT, dish.getUnit());
            contentValues.put(COLUMN_COLOR, dish.getColor());
            contentValues.put(COLUMN_ICON, dish.getIcon());
            contentValues.put(COLUMN_IS_SALE, dish.isSale() ? 1 : 0);
            return mSQLiteDBManager.updateRecord(DISH_TBL_NAME, contentValues,
                    COLUMN_DISH_ID + "=?", new String[]{String.valueOf(dish.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức lấy toàn bộ tên các món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách tên các món ăn
     */
    @Override
    public List<String> getAllDishName() {
        List<String> dishNames = new ArrayList<>();
        Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_DISH_NAME + " from " + DISH_TBL_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dishNames.add(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_NAME)).toLowerCase());
            cursor.moveToNext();
        }
        cursor.close();
        return dishNames;
    }

    @Override
    public List<Dish> getAllDish() {
        List<Dish> dishes = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + DISH_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Dish dish = new Dish.Builder().setId(cursor.getInt(cursor.getColumnIndex(COLUMN_DISH_ID)))
                        .setName(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_NAME)))
                        .setPrice(cursor.getInt((cursor.getColumnIndex(COLUMN_PRICE))))
                        .setUnit(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)))
                        .setColor(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR)))
                        .setIcon(cursor.getString(cursor.getColumnIndex(COLUMN_ICON)))
                        .setSale(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SALE)) == 1)
                        .build();
                dishes.add(dish);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dishes;
    }

    /**
     * Phương thức kiểm tra rằng tên món ăn đã tồn tại hay chưa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishName - tên món ăn được thêm mới
     * @return - tên món ăn đã có hay chưa có
     */
    @Override
    public boolean isDishIfExists(String dishName) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords(DISH_TBL_NAME, new String[]{COLUMN_DISH_NAME},
                    "lower(" + COLUMN_DISH_NAME + ")" + " = ? ", new String[]{dishName.toLowerCase()},
                    null, null, null);
            int dishNameRecord = cursor.getCount();
            cursor.close();
            return dishNameRecord > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
