package vn.com.misa.cukcuk.screen.splash;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;

public interface ISplashContract {
    interface IView extends IBaseView {
        void initDataBase();

        void showMainScreen();
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
