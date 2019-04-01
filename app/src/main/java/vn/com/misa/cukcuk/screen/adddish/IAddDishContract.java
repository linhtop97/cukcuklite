package vn.com.misa.cukcuk.screen.adddish;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;

public interface IAddDishContract {
    interface IView extends IBaseView {

        void dishNameEmpty();

        void addDishSuccess();

        void addDishFailed(int error);

        void setUnit(String unit);

        void upDateDishSuccess();

        void deleteDishSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void addDish(String name, String price, String unit, String background, String icon);

        void updateDish(int id, String name, String price, String unit, String background, String icon, boolean isSale);

        void deleteDish(int id);
    }
}