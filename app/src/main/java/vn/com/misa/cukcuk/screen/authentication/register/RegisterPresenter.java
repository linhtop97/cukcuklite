package vn.com.misa.cukcuk.screen.authentication.register;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.account.AccountDataSource;
import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.model.Account;

public class RegisterPresenter implements IRegisterContract.IPresenter {

    private IRegisterContract.IView mView;
    private AccountDataSource mAccountDataSource;

    RegisterPresenter() {
        mAccountDataSource = new AccountDataSource();
    }

    @Override
    public void registerAccount(String userName, String password, String confirmPassword) {
        if (userName.equals("")) {
            mView.userNameEmpty();
            return;
        }
        if (password.equals("")) {
            mView.passwordEmpty();
            return;
        }
        if (confirmPassword.equals("")) {
            mView.confirmPasswordEmpty();
            return;
        }
        if (!password.equals(confirmPassword)) {
            mView.confirmPasswordError();
            return;
        }

        try {
            Account account = new Account.Builder().setUsername(userName).setPassword(password).build();
            EnumResult result = mAccountDataSource.registerAccount(account);
            if (result == EnumResult.Success) {
                mView.registerSuccess(account);
            } else if (result == EnumResult.Exists) {
                mView.receiveMessage(R.string.account_is_exists);
            } else if (result == EnumResult.SomethingWentWrong) {
                mView.registerFailed(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setView(IRegisterContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
