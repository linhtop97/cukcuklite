package vn.com.misa.cukcuk.screen.authentication.login;

import android.content.Context;

import com.google.gson.Gson;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.account.AccountDataSource;
import vn.com.misa.cukcuk.data.model.Account;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsHelper;
import vn.com.misa.cukcuk.data.prefs.SharedPrefsKey;

/**
 * Lớp xử lý logic màn hình đăng nhập
 * Created_by Nguyễn Bá Linh on 22/03/2019
 */
public class LoginPresenter implements ILoginContract.IPresenter {

    private ILoginContract.IView mView;
    private SharedPrefsHelper mPrefsHelper;
    private AccountDataSource mAccountDataSource;

    /**
     * Phương thức khởi tạo lớp
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param context - context ứng dụng
     */
    LoginPresenter(Context context) {
        mPrefsHelper = SharedPrefsHelper.getInstance(context);
        mAccountDataSource = new AccountDataSource();
    }

    /**
     * Phương thức xử lý login
     * Created_by Nguyễn Bá Linh on 22/03/2019
     *
     * @param userName      - Tên đăng nhập
     * @param password      - mật khẩu
     * @param isSaveAccount - mật khẩu được nhập lại
     */
    @Override
    public void signIn(String userName, String password, boolean isSaveAccount) {
        if (userName.equals("")) {
            mView.userNameEmpty();
            return;
        }
        if (password.equals("")) {
            mView.passwordEmpty();
            return;
        }
        if (mAccountDataSource.signIn(userName, password, isSaveAccount)) {
            mView.signInSuccess();
            mPrefsHelper.put(SharedPrefsKey.PREF_KEEP_SIGN_IN, isSaveAccount);
            if (isSaveAccount) {
                mPrefsHelper.put(SharedPrefsKey.PREF_ACCOUNT_SIGN_IN,
                        new Gson().toJson(new Account.Builder().setUsername(userName).setPassword(password).build()));
            } else {
                mPrefsHelper.put(SharedPrefsKey.PREF_ACCOUNT_SIGN_IN, null);
            }
        } else {
            mView.signInFailed(R.string.account_invalidate);
        }
    }

    @Override
    public void setView(ILoginContract.IView view) {
        mView = view;
    }

    /**
     * Khởi tạo các dữ kiện ban đầu cho lớp và màn hình login
     * Created_by Nguyễn Bá Linh on 22/03/2019
     */
    @Override
    public void onStart() {
        boolean isRemember = mPrefsHelper.get(SharedPrefsKey.PREF_KEEP_SIGN_IN, Boolean.class);
        mView.setSignInOptionDefault(isRemember);
        if (isRemember) {
            String acc = mPrefsHelper.get(SharedPrefsKey.PREF_ACCOUNT_SIGN_IN, String.class);
            if (acc != null) {
                Account account = new Gson().fromJson(acc, Account.class);
                mView.setAccount(account);
            }
        }
    }

    @Override
    public void onStop() {

    }
}
