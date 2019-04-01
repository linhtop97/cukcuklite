package vn.com.misa.cukcuk.screen.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.databinding.ActivitySplashBinding;
import vn.com.misa.cukcuk.screen.main.MainActivity;
import vn.com.misa.cukcuk.utils.Navigator;

/**
 * Màn hình Splash
 * Created_by Nguyễn Bá Linh on 19/03/2019
 */
public class SplashActivity extends AppCompatActivity implements ISplashContract.IView {

    private static final String TAG = "SplashActivity";
    private static final long SPLASH_DISPLAY_LENGTH = 2500;
    private ActivitySplashBinding mBinding;
    private SplashPresenter mPresenter;
    private Navigator mNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mNavigator = new Navigator(this);
        mPresenter = new SplashPresenter();
        mPresenter.setView(this);
        initViews();
    }

    /**
     * Phương thức khởi tạo các view
     * Created_by Nguyễn Bá Linh on 19/03/2019
     */
    private void initViews() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.onStart();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cung cấp 1 thông điệp
     *
     * @param message thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức khởi tạo Database khi màn hình ứng dụng được hiển thị
     * Created_by Nguyễn Bá Linh on 19/03/2019
     */
    @Override
    public void initDataBase() {

    }

    /**
     * Phương thức hiển thị màn hình main của ứng dụng
     * Created_by Nguyễn Bá Linh on 19/03/2019
     */
    @Override
    public void showMainScreen() {
        try {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            mNavigator.startActivity(intent, Navigator.ActivityTransition.START);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
