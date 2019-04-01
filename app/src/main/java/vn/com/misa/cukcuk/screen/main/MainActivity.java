package vn.com.misa.cukcuk.screen.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.screen.authentication.login.LoginFragment;
import vn.com.misa.cukcuk.utils.Navigator;

/**
 * Created_by Nguyễn Bá Linh on 19/03/2019
 */
public class MainActivity extends AppCompatActivity implements IMainContract.IView {

    private static final String TAG = "MainActivity";
    private MainPresenter mPresenter;
    private Navigator mNavigator;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        mNavigator = new Navigator(this);
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    /**
     * Phương thức hiển thị màn hình đăng nhập
     * Created_by Nguyễn Bá Linh on 19/03/2019
     */
    @Override
    public void showLoginFragment() {
        mNavigator.addFragment(R.id.rlMainContainer, LoginFragment.newInstance(null),
                false, Navigator.NavigateAnim.NONE, LoginFragment.class.getSimpleName());
    }

    /**
     * Phương thức nhận một thông điệp
     * Created_by Nguyễn Bá Linh on 19/03/2019
     *
     * @param message thông điệp
     */
    @Override
    public void receiveMessage(int message) {

    }
}
