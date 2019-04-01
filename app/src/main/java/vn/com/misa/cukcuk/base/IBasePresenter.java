package vn.com.misa.cukcuk.base;

/**
 * Class cơ sở cho các presenter
 * Created_by Nguyễn Bá Linh on 27/03/2019
 *
 * @param <T> thể hiện của view
 */
public interface IBasePresenter<T extends IBaseView> {
    /**
     * Phương thức truyền view cho presenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param view
     */
    void setView(T view);

    /**
     * Phương thức khởi chạy đầu tiên khi presenter bắt đầu hoạt động
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    void onStart();

    /**
     * Phương thức kết thúc tương ứng khi presenter hoàn thành công việc của mình và ngừng hoạt động
     */
    void onStop();
}
