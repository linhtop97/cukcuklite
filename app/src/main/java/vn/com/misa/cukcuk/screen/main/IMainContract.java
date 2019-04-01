package vn.com.misa.cukcuk.screen.main;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;

public interface IMainContract {
    interface IView extends IBaseView {
        void showLoginFragment();
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
