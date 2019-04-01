package vn.com.misa.cukcuk.screen.selectunit;

import java.util.List;

import vn.com.misa.cukcuk.base.IBasePresenter;
import vn.com.misa.cukcuk.base.IBaseView;
import vn.com.misa.cukcuk.data.model.Unit;


public interface IUnitContract {
    interface IView extends IBaseView {
        void showUnit(List<Unit> allUnit, int lastSelectPosition);

        void unitNameEmpty();

        void addUnitFailed(int unit_name_is_exists);

        void addUnitSuccess(String unitName);

        void updateUnitSuccess(String unit);

        void deleteUnitSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void onStart(String unitName);

        void addUnit(String unitName);

        void updateUnit(Unit unit);

        void deleteUnit(Unit unit);
    }
}
