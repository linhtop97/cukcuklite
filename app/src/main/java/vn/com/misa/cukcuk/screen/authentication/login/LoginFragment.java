package vn.com.misa.cukcuk.screen.authentication.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.model.Account;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsHelper;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsKey;
import vn.com.misa.cukcuk.databinding.FragmentLoginBinding;
import vn.com.misa.cukcuk.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuk.screen.authentication.register.RegisterFragment;
import vn.com.misa.cukcuk.screen.main.MainActivity;
import vn.com.misa.cukcuk.utils.AppConstants;
import vn.com.misa.cukcuk.utils.Navigator;

public class LoginFragment extends Fragment implements ILoginContract.IView, View.OnClickListener {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding mBinding;
    private LoginPresenter mPresenter;
    private SharedPrefsHelper mPrefsHelper;
    private MainActivity mActivity;
    private Navigator mNavigator;
    private EventBus mEventBus;
    private Account mAccount;

    /**
     * Phương thức khởi tạo cho Login Fragment
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @return Login Fragment
     */
    public static LoginFragment newInstance(Account account) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.EXTRA_ACCOUNT, account);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Subscribe(sticky = true)
    public void onEvent(Account account) {
        if (account != null) {
            mAccount = account;
            setAccount(account);
        }
    }

    /**
     * Phương thức xảy ra khi fragment được thêm vào, khởi tạo các biến toàn cục
     *
     * @param context - context nơi fragment được khởi tạo
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
        mPrefsHelper = SharedPrefsHelper.getInstance(context);
        mNavigator = new Navigator(this);
        mEventBus = EventBus.getDefault();
    }

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.removeStickyEvent(Account.class);
        mEventBus.unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mPresenter = new LoginPresenter(mActivity);
        mPresenter.setView(this);
        mPresenter.onStart();
        initViews();
        initEvents();
        return mBinding.getRoot();
    }

    /**
     * Phương thức khởi tạo gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    private void initEvents() {
        mBinding.btnSignIn.setOnClickListener(this);
        mBinding.tvRegister.setOnClickListener(this);
    }

    /**
     * Khởi tạo view
     * Created_by Nguyễn Bá Linh on 20/03/2019
     */
    private void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Account account = bundle.getParcelable(AppConstants.EXTRA_ACCOUNT);
            if (account != null) {
                setAccount(account);
                Log.d(TAG, "initViews: " + account.getUsername());
            }

        }
        setPaddingForTextToRightOfCheckBox();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAccount != null) {
            setAccount(mAccount);
        }
    }

    /**
     * Tăng khoảng cách giữa text và icon của checkbox
     * Created_by Nguyễn Bá Linh on 20/03/2019
     */
    private void setPaddingForTextToRightOfCheckBox() {
        final float scale = this.getResources().getDisplayMetrics().density;
        mBinding.cbRemember.setPadding(mBinding.cbRemember.getPaddingLeft() + (int) (8.0f * scale + 0.5f),
                mBinding.cbRemember.getPaddingTop(),
                mBinding.cbRemember.getPaddingRight(),
                mBinding.cbRemember.getPaddingBottom());
    }

    /**
     * Phương thức sự kiện đăng nhập thành công và mở màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    @Override
    public void signInSuccess() {
        Intent intent = new Intent();
        intent.setClass(mActivity, AddDishActivity.class);
        mNavigator.startActivity(intent, Navigator.ActivityTransition.START);
    }

    /**
     * Phương thức thông báo lỗi khi đăng nhập thất bại
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param error - Thông điệp lỗi được trả về
     */
    @Override
    public void signInFailed(int error) {
        mNavigator.showToast(error);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * Phương thức khởi tạo checkbox đã được checked trước đó hay chưa
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param isRemember - checkbox đã hoặc chưa được checked trước đó khi đăng nhập thành công
     */
    @Override
    public void setSignInOptionDefault(Boolean isRemember) {
        mBinding.cbRemember.setChecked(isRemember);
        mBinding.cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPrefsHelper.put(SharedPrefsKey.PREF_KEEP_SIGN_IN, isChecked);
            }
        });
    }

    /**
     * Phương thức gán tài khoản vào Login form
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param account - tài khoản đã đăng nhập trước đó
     */
    @Override
    public void setAccount(Account account) {
        if (account != null) {
            mBinding.etUsername.setText(account.getUsername());
            mBinding.etPassword.setText(account.getPassword());
        }
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
        mBinding.etUsername.requestFocus();
    }

    /**
     * Phương thức thông báo thông điệp bất kì
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param message - thông điệp
     */
    @Override
    public void receiveMessage(int message) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                try {
                    mPresenter.signIn(mBinding.etUsername.getText().toString().trim(),
                            mBinding.etPassword.getText().toString().trim(),
                            mBinding.cbRemember.isChecked());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvRegister:
                try {
                    mNavigator.addFragment(R.id.rlMainContainer, new RegisterFragment(),
                            true, Navigator.NavigateAnim.RIGHT_LEFT, RegisterFragment.class.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
