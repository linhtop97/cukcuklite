package vn.com.misa.cukcuk.screen.authentication.login;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;
import vn.com.misa.cukcuk.data.model.Account;

public interface ILoginContract {
    interface IView extends IBaseView {
        void signInSuccess();

        void signInFailed(int error);

        void showLoading();

        void hideLoading();

        void setSignInOptionDefault(Boolean isRemember);

        void setAccount(Account account);

        void userNameEmpty();

        void passwordEmpty();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void signIn(String userName, String password, boolean isSaveAccount);
    }
}
