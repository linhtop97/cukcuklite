package vn.com.misa.cukcuk.screen.menu;

import java.util.List;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;
import vn.com.misa.cukcuk.data.model.Dish;

public interface IMenuContract {
    interface IView extends IBaseView {
        void showDish(List<Dish> dishes);
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
