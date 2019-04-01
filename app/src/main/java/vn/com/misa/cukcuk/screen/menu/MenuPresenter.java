package vn.com.misa.cukcuk.screen.menu;

import vn.com.misa.cukcuk.data.dish.DishDataSource;

public class MenuPresenter implements IMenuContract.IPresenter {
    private IMenuContract.IView mView;
    private DishDataSource mDishDataSource;

    /**
     * Phương thức khởi tạo presenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    MenuPresenter() {
        mDishDataSource = new DishDataSource();
    }

    @Override
    public void setView(IMenuContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        //lấy danh sách các món ăn và hiển thị ra màn hình
        try {
            mView.showDish(mDishDataSource.getAllDish());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
