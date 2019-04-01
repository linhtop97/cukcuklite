package vn.com.misa.cukcuk.screen.authentication.register;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;
import vn.com.misa.cukcuk.data.model.Account;

public interface IRegisterContract {
    interface IView extends IBaseView {
        void registerSuccess(Account account);

        void registerFailed(int message);

        void userNameEmpty();

        void passwordEmpty();

        void confirmPasswordEmpty();

        void confirmPasswordError();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void registerAccount(String userName, String password, String confirmPassword);
    }
}
