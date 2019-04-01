package vn.com.misa.cukcuk.screen.adddish;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.dish.DishDataSource;
import vn.com.misa.cukcuk.data.model.Dish;
import vn.com.misa.cukcuk.data.unit.UnitDataSource;

public class AddDishPresenter implements IAddDishContract.IPresenter {
    private IAddDishContract.IView mView;
    private DishDataSource mDishDataSource;
    private UnitDataSource mUnitDataSource;

    /**
     * Phương thức khởi tạo AddDishPresenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    AddDishPresenter() {
        mDishDataSource = new DishDataSource();
        mUnitDataSource = new UnitDataSource();
    }

    /**
     * Phương thức thêm mới món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param name       - tên món ăn
     * @param price      - giá món ăn
     * @param unit-      đơn vị
     * @param background - màu của nền món ăn
     * @param icon       - con của món ăn
     */
    @Override
    public void addDish(String name, String price, String unit, String background, String icon) {
        try {
            if (name.equals("")) {
                mView.dishNameEmpty();
                return;
            }
            Dish dish = new Dish.Builder().setName(name)
                    .setPrice(Integer.parseInt(price))
                    .setUnit(unit)
                    .setColor(background)
                    .setIcon(icon).build();
            switch (mDishDataSource.addDishToDatabase(dish)) {
                case Exists:
                    mView.receiveMessage(R.string.dish_name_is_exists);
                    break;
                case Success:
                    mView.addDishSuccess();
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
     * Phương thức chỉnh sửa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param id         - id của món ăn
     * @param name       - tên món ăn
     * @param price      - giá món ăn
     * @param unit       - đơn vị
     * @param background - màu của nền món ăn
     * @param icon       - con của món ăn
     * @param isSale     - món ăn có bán nữa hay không
     */
    @Override
    public void updateDish(int id, String name, String price, String unit, String background, String icon, boolean isSale) {
        try {
            if (name.equals("")) {
                mView.dishNameEmpty();
                return;
            }
            Dish dish = new Dish.Builder().setId(id)
                    .setName(name)
                    .setPrice(Integer.parseInt(price))
                    .setUnit(unit)
                    .setColor(background)
                    .setIcon(icon)
                    .setSale(!isSale)
                    .build();
            switch (mDishDataSource.updateDishToDatabase(dish)) {
                case Exists:
                    mView.receiveMessage(R.string.dish_name_is_exists);
                    break;
                case Success:
                    mView.upDateDishSuccess();
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
     * Phương thức xóa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param id - id của món ăn
     */
    @Override
    public void deleteDish(int id) {
        if (mDishDataSource.deleteDishById(id)) {
            mView.deleteDishSuccess();
        } else {
            mView.receiveMessage(R.string.something_went_wrong);
        }
    }

    @Override
    public void setView(IAddDishContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        //gán đơn vị mặc định cho món ăn khi vào tính năng thêm mới
        String unit = mUnitDataSource.getAllUnitName().get(0);
        mView.setUnit(unit);
    }

    @Override
    public void onStop() {

    }
}
