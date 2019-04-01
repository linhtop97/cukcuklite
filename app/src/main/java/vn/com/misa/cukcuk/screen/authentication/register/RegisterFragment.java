package vn.com.misa.cukcuk.screen.authentication.register;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.model.Account;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsHelper;
import vn.com.misa.cukcuk.databinding.FragmentRegisterBinding;
import vn.com.misa.cukcuk.screen.authentication.login.LoginFragment;
import vn.com.misa.cukcuk.screen.main.MainActivity;
import vn.com.misa.cukcuk.utils.Navigator;

/**
 * Màn hình đăng kí
 * Created_by Nguyễn Bá Linh on 22/03/2019
 */
public class RegisterFragment extends Fragment implements IRegisterContract.IView, View.OnClickListener {
    private FragmentRegisterBinding mBinding;
    private RegisterPresenter mPresenter;
    private Navigator mNavigator;
    private MainActivity mActivity;
    private SharedPrefsHelper mPrefsHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
        mPrefsHelper = SharedPrefsHelper.getInstance(mActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        mNavigator = new Navigator(this);
        mPresenter = new RegisterPresenter();
        mPresenter.setView(this);
        initEvents();
        return mBinding.getRoot();
    }

    /**
     * Phương thức gắn các sự kiện cho view
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    private void initEvents() {
        mBinding.btnRegister.setOnClickListener(this);
    }

    /**
     * Phương thức gọi khi đăng kí thành công và chuyển sang trang đăng nhập
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param account - tài khoản đăng kí thành công
     */
    @Override
    public void registerSuccess(Account account) {
        try {
            if (account != null) {
                mActivity.getSupportFragmentManager().popBackStackImmediate();
                //EventBus.getDefault().postSticky(account);
                mNavigator.addFragment(R.id.rlMainContainer, LoginFragment.newInstance(account),
                        false, Navigator.NavigateAnim.LEFT_RIGHT, LoginFragment.class.getSimpleName());
                return;
            }
            mNavigator.showToast(R.string.something_went_wrong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerFailed(int message) {
        mNavigator.showToast(message);
    }

    /**
     * Phương thức thông báo khi người dùng không điền tên tài khoản vào ô nhập liệu
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    @Override
    public void userNameEmpty() {
        mNavigator.showToast(R.string.username_not_allow_empty);
        mBinding.etUsername.requestFocus();
    }

    /**
     * Phương thức thông báo khi người dùng không điền mật khẩu vào ô nhập liệu
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    @Override
    public void passwordEmpty() {
        mNavigator.showToast(R.string.password_not_allow_empty);
        mBinding.etPassword.requestFocus();
    }

    /**
     * Phương thức thông báo khi người dùng không nhập lại mật khẩu vào ô nhập liệu
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    @Override
    public void confirmPasswordEmpty() {
        mNavigator.showToast(R.string.you_have_to_confirm_passwod);
        mBinding.etConfirmPassword.requestFocus();
    }

    /**
     * Phương thức thông báo cho người dùng biết là đã nhập mật khẩu không khớp
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    @Override
    public void confirmPasswordError() {
        mNavigator.showToast(R.string.confirm_password_is_incorrect);
        mBinding.etConfirmPassword.requestFocus();
    }

    /**
     * Thông báo 1 thông điệp
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param message - thông điệp
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToast(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                try {
                    mPresenter.registerAccount(mBinding.etUsername.getText().toString().trim(),
                            mBinding.etPassword.getText().toString().trim(),
                            mBinding.etConfirmPassword.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
