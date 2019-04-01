package vn.com.misa.cukcuk.data.account;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.db.IDBUtils;
import vn.com.misa.cukcuk.data.db.SQLiteDBManager;
import vn.com.misa.cukcuk.data.model.Account;

/**
 * Lớp thao tác với các tài khoản trong cơ sở dữ liệu
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class AccountDataSource implements IAccountDataSource, IDBUtils.ITableAccountUtils {
    private static final String TAG = "AccountDataSource";
    private SQLiteDBManager mSQLiteDBManager;

    /**
     * Phương thức khởi tạo lớp
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public AccountDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
    }

    /**
     * Phương thức thêm tài khoản
     * Created_by Nguyễn Bá Linh on 20/03/2019
     *
     * @param account - Tài khoản
     * @return - Thêm tài khoản thành công hay thất bại
     */
    @Override
    public boolean addAccount(Account account) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_USER_NAME, account.getUsername());
            contentValues.put(COLUMN_PASSWORD, account.getPassword());
            return mSQLiteDBManager.addNewRecord(ACCOUNT_TBL_NAME, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức xóa tài khoản qua Id
     * Created_by Nguyễn Bá Linh on 20/03/2019
     *
     * @param accountId - id của tài khoản
     * @return - tài khoản được xóa thành công hay thất bại
     */
    @Override
    public boolean deleteAccountById(int accountId) {
        try {
            return mSQLiteDBManager.deleteRecord(ACCOUNT_TBL_NAME,
                    COLUMN_ACCOUNT_ID + "=?", new String[]{String.valueOf(accountId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * phương thức lấy tất cả tài khoản
     * Created_by Nguyễn Bá Linh on 20/03/2019
     *
     * @return - danh sách tài khoản
     */
    @Override
    public List<Account> getAllAccount() {
        List<Account> accounts = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + ACCOUNT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Account account = new Account.Builder().setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ACCOUNT_ID)))
                        .setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)).toLowerCase())
                        .setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)).toLowerCase()).build();
                accounts.add(account);
                cursor.moveToNext();
            }
            cursor.close();
            Log.d(TAG, "getAllAccount: " + accounts.get(0).getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * Phương thức cập nhật mật khẩu cho tài khoản
     * Created_by Nguyễn Bá Linh on 20/03/2019
     *
     * @param account - Tài khoản
     * @return - Mật khẩu có được cập nhật thành công hay thất bại
     */
    @Override
    public boolean updatePassword(Account account) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PASSWORD, account.getPassword());
            return mSQLiteDBManager.updateRecord(ACCOUNT_TBL_NAME, contentValues,
                    COLUMN_ACCOUNT_ID + "=?", new String[]{String.valueOf(account.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra tài khoản này đã hay chưa tồn tại trong cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 20/03/2019
     *
     * @param userName - Tên tài khoản
     * @return - Tài khoản đã được tạo hay chưa được tạo
     */
    @Override
    public boolean isAccountIfExists(String userName) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords(ACCOUNT_TBL_NAME, new String[]{COLUMN_USER_NAME},
                    COLUMN_USER_NAME + " = ? ", new String[]{userName},
                    null, null, null);
            int accoundNameRecord = cursor.getCount();
            cursor.close();
            return accoundNameRecord > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức đăng nhập cho user
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param userName   - tên tài khoản
     * @param password   - mật khẩu
     * @param isRemember - có lưu tài khoản hay không
     * @return - đăng nhập thành công hay thất bại
     */
    @Override
    public boolean signIn(String userName, String password, boolean isRemember) {
        boolean signInSuccess = false;
        try {
            List<Account> accounts = getAllAccount();
            int accountSize = accounts.size();
            for (int i = 0; i < accountSize; i++) {
                Account account = accounts.get(i);
                if (account.getUsername().toLowerCase().equals(userName.toLowerCase()) &&
                        account.getPassword().toLowerCase().equals(password.toLowerCase())) {
                    signInSuccess = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signInSuccess;
    }

    /**
     * Phương thức đăng kí tài khoản có kiểm tra sự tồn tại của tài khoản
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param account - tài khoản đăng kí
     * @return - đăng kí tài khoản thành công/thất bại/xảy ra lỗi không mong muốn
     */
    @Override
    public EnumResult registerAccount(Account account) {
        try {
            if (isAccountIfExists(account.getUsername())) {
                return EnumResult.Exists;
            }
            if (addAccount(account)) {
                return EnumResult.Success;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EnumResult.SomethingWentWrong;
    }
}
