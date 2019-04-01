package vn.com.misa.cukcuk.screen.main;

public class MainPresenter implements IMainContract.IPresenter {
    private IMainContract.IView mView;

    /**
     * Phương thức khởi tạo MainPresenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    MainPresenter() {
    }

    @Override
    public void setView(IMainContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        mView.showLoginFragment();
    }

    @Override
    public void onStop() {

    }
}
