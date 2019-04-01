package vn.com.misa.cukcuk.base.listeners;

/**
 * Là interface lắng nghe xứ lý khi lấy dữ liệu
 * Created_by Nguyễn Bá Linh on 25/03/2019
 *
 * @param <T> là kiểu dữ liệu kết quả trả về
 */
public interface DataCallBack<T> {

    /**
     * Được gọi khi lấy dữ liệu về thành công
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param data là dữ liệu được trả về
     */
    void onDataSuccess(T data);

    /**
     * Created_by Nguyễn Bá Linh on 25/03/2019
     * Mô tả:  được gọi khi việc lấy dữ liệu bị thất bại
     *
     * @param msg là thông điệp muốn trả về để xử lý
     */
    void onDataFailed(String msg);
}
