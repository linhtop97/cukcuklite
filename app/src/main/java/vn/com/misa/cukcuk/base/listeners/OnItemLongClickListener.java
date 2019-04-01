package vn.com.misa.cukcuk.base.listeners;

/**
 * Là listener chịu trách nhiệm cho sự kiện long click cho View
 * Created_by Nguyễn Bá Linh on 25/03/2019
 *
 * @param <T> là kiểu dữ liệu được truyền đi khi sự kiện long click xảy ra
 */
public interface OnItemLongClickListener<T> {
    /**
     * Phương thức được sử dụng khi sự kiện click của View được kích hoạt
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param data là tham số được truyền vào khi View được click
     */
    void onItemClick(T data);
}
