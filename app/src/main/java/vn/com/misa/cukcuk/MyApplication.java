package vn.com.misa.cukcuk;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.com.misa.cukcuk.data.db.SQLiteDBManager;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsHelper;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsKey;

import static vn.com.misa.cukcuk.data.db.IDBUtils.DB_NAME;

/**
 * Lớp App cấu hình ban đâu cho ứng dụng trước khi khởi chạy
 * Created_by Nguyễn Bá Linh on 20/03/2019
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //cho phép đặt nguồn ảnh là vector
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initDatabase();
        //đặt font chữ mặc định cho thư viện
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.nunito_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    /**
     * Phương thức khởi tạo database và các dữ liệu account ban đầu cho ứng dụng
     * Created_by Nguyễn Bá Linh on 20/03/2019
     */
    private void initDatabase() {
        try {
            SharedPrefsHelper prefsHelper = SharedPrefsHelper.getInstance(this);
            SQLiteDBManager manager = SQLiteDBManager.getInstance(this);
            if (!prefsHelper.get(SharedPrefsKey.IS_FIRST_TIME_SETUP, Boolean.class)) {
                //khởi tạo tài khoản admin
                boolean copyDBOk = false;
                File file = this.getDatabasePath(DB_NAME);
                if (!file.exists()) {
                    manager.getWritableDatabase();
                    manager.close();
                    copyDBOk = manager.copyDatabase();
                }
                if (copyDBOk) {
                    prefsHelper.put(SharedPrefsKey.IS_FIRST_TIME_SETUP, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
