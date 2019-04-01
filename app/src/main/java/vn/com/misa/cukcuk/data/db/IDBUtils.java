package vn.com.misa.cukcuk.data.db;

/**
 * Lớp chứa định danh cho bảng và cột dữ liệu cho database
 * Created_by Nguyễn Bá Linh on 20/03/2019
 */
public interface IDBUtils {
    String DB_NAME = "cukcuklite.sqlite";
    String DB_LOCATION = "/data/data/vn.com.misa.cukcuk/databases/";
    int DB_VERSION = 1;
    String USER_NAME_ADMIN = "admin";
    String PASSWORD_ADMIN = "12345678";
    String PRIMARY_KEY = "PRIMARY KEY";
    String AUTOINCREMENT = "AUTOINCREMENT";
    String NOT_NULL = "NOT NULL";
    String NULL = "NULL";
    String DEFAULT = "DEFAULT";

    String TEXT_DATA_TYPE = "TEXT";
    String INTEGER_DATA_TYPE = "INTEGER";
    String REAL_DATA_TYPE = "REAL";
    String BLOB_DATA_TYPE = "BLOB";

    /**
     * Tên bảng, trường của bảng tài khoản
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableAccountUtils {
        String ACCOUNT_TBL_NAME = "tblAccount";
        String COLUMN_ACCOUNT_ID = "PK_iAccountId";
        String COLUMN_USER_NAME = "sUsername";
        String COLUMN_PASSWORD = "sPassword";
    }

    /**
     * Tên bảng, trường của bảng món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableDishUtils {
        String DISH_TBL_NAME = "tblDish";
        String COLUMN_DISH_ID = "PK_iDishId";
        String COLUMN_DISH_NAME = "sName";
        String COLUMN_PRICE = "iPrice";
        String COLUMN_UNIT = "sUnit";
        String COLUMN_COLOR = "sColor";
        String COLUMN_ICON = "sIcon";
        String COLUMN_IS_SALE = "bIsSale";
    }

    /**
     * Tên bảng, trường của bảng đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableUnitUtils {
        String UNIT_TBL_NAME = "tblUnit";
        String COLUMN_UNIT_ID = "PK_iUnitId";
        String COLUMN_UNIT_NAME = "sUnitName";
    }

//    String CREATE_ACCOUNT_TABLE_QUERY =
//            "CREATE TABLE " + ACCOUNT_TBL_NAME + "("
//                    + COLUMN_ACCOUNT_ID + " " + INTEGER_DATA_TYPE + " " + PRIMARY_KEY + " " + AUTOINCREMENT + ", "
//                    + COLUMN_USER_NAME + " " + TEXT_DATA_TYPE + " " + NOT_NULL + ", "
//                    + COLUMN_PASSWORD + " " + TEXT_DATA_TYPE
//                    + ")";
}
