package vn.com.misa.cukcuk.base.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Class base cho các adapter dành cho recyclerView
 * Created_by Nguyễn Bá Linh on 25/03/2019
 *
 * @param <T>
 */
public abstract class ListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List<T> mListData;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public ListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    /**
     * Phương thức lấy danh sách dữ liệu hiện tại
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return danh sách dữ liệu
     */
    public List<T> getListData() {
        return mListData;
    }

    /**
     * Phương thức truyền dữ liệu cho danh sách
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param listData danh sách dữ liệu được truyền vào
     */
    public void setListData(List<T> listData) {
        mListData = listData;
        notifyDataSetChanged();
    }

    /**
     * Phương thức thêm dữ liệu cho danh sách dữ liệu đã có
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param listData danh sách dữ liệu được thêm vào
     */
    public void addData(List<T> listData) {
        if (mListData == null) {
            mListData = listData;
        } else {
            mListData.addAll(listData);
        }
        notifyDataSetChanged();
    }

    /**
     * Phương thức xóa dữ liệu của danh sách
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public void clear() {
        if (mListData != null) {
            mListData.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Phương thức lấy item tại vị trí position
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param position là vị trí muốn lấy
     * @return item của list
     */
    public T getItem(int position) {
        return mListData.get(position);
    }

}
