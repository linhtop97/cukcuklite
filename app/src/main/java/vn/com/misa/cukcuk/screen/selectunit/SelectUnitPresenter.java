package vn.com.misa.cukcuk.screen.selectunit;

import java.util.List;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.dish.DishDataSource;
import vn.com.misa.cukcuk.data.model.Dish;
import vn.com.misa.cukcuk.data.model.Unit;
import vn.com.misa.cukcuk.data.unit.UnitDataSource;

/**
 * Presenter cho Select Screen
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class SelectUnitPresenter implements IUnitContract.IPresenter {

    private IUnitContract.IView mView;
    private UnitDataSource mUnitDataSource;
    private DishDataSource mDishDataSource;

    /**
     * Phương thức khởi tạo presenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    SelectUnitPresenter() {
        mUnitDataSource = new UnitDataSource();
        mDishDataSource = new DishDataSource();
    }

    @Override
    public void setView(IUnitContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    /**
     * Phương thức khởi tạo đầu tiên khi vào màn hình, lấy các thông số mặc định để view hiển thị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - tên đơn vị
     */
    @Override
    public void onStart(String unitName) {
        try {
            List<String> units = mUnitDataSource.getAllUnitName();
            int size = units.size();
            int lastSelectPosition = 0;
            for (int i = 0; i < size; i++) {
                if (units.get(i).equals(unitName)) {
                    lastSelectPosition = i;
                    break;
                }
            }
            mView.showUnit(mUnitDataSource.getAllUnit(), lastSelectPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm mới đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - tên đơn vị
     */
    @Override
    public void addUnit(String unitName) {
        try {
            if (unitName.equals("")) {
                mView.unitNameEmpty();
                return;
            }
            switch (mUnitDataSource.addUnitToDatabase(unitName)) {
                case Exists:
                    mView.addUnitFailed(R.string.unit_name_is_exists);
                    break;
                case Success:
                    mView.addUnitSuccess(unitName);
                    break;
                case SomethingWentWrong:
                    mView.addUnitFailed(R.string.something_went_wrong);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật tên đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void updateUnit(Unit unit) {
        try {
            if (unit.getUnit().equals("")) {
                mView.unitNameEmpty();
                return;
            }
            switch (mUnitDataSource.updateUnitToDatabase(unit)) {
                case Exists:
                    mView.receiveMessage(R.string.unit_name_is_exists);
                    break;
                case Success:
                    mView.updateUnitSuccess(unit.getUnit());
                    break;
                case SomethingWentWrong:
                    mView.receiveMessage(R.string.something_went_wrong);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa đơn vị có kiểm tra đơn vị đã được sử dụng hay chưa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void deleteUnit(Unit unit) {
        try {
            List<Dish> dishes = mDishDataSource.getAllDish();
            int size = dishes.size();
            boolean unitIsUsed = false;
            for (int i = 0; i < size; i++) {
                if (unit.getUnit().toLowerCase().equals(dishes.get(i).getUnit().toLowerCase())) {
                    unitIsUsed = true;
                }
            }
            if (unitIsUsed) {
                mView.receiveMessage(R.string.unit_name_is_used);
            } else {
                if (mUnitDataSource.deleteUnit(unit)) {
                    mView.deleteUnitSuccess();
                } else {
                    mView.receiveMessage(R.string.something_went_wrong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
