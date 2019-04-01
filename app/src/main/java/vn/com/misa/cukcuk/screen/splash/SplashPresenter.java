package vn.com.misa.cukcuk.screen.splash;

public class SplashPresenter implements ISplashContract.IPresenter {

    private ISplashContract.IView mView;

    public SplashPresenter() {

    }

    @Override
    public void setView(ISplashContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        mView.showMainScreen();
    }

    @Override
    public void onStop() {

    }
}
