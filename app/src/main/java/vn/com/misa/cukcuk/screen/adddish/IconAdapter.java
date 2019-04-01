package vn.com.misa.cukcuk.screen.adddish;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.base.adapters.ListAdapter;
import vn.com.misa.cukcuk.base.listeners.OnItemClickListener;
import vn.com.misa.cukcuk.utils.ImageUtils;

/**
 * Adapter cho danh sách icon món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class IconAdapter extends ListAdapter<String> {

    private OnItemClickListener<String> mItemClickListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public IconAdapter(Context context, List<String> listImage) {
        super(context);
        mListData = listImage;
    }

    public void setItemClickListener(OnItemClickListener<String> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dish_icon, viewGroup, false);
        return new DishIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DishIconViewHolder dishIconViewHolder = (DishIconViewHolder) viewHolder;
        final String fileName = mListData.get(i);
        dishIconViewHolder.bind(fileName);
        dishIconViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(fileName);
            }
        });
    }

    /**
     * Lớp Item cho danh sách icon món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public class DishIconViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;

        DishIconViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }

        /**
         * Phương thức gán icon cho ImageView
         * Created_by Nguyễn Bá Linh on 27/03/2019
         *
         * @param imageFileName - tên file icon
         */
        void bind(String imageFileName) {
            if (imageFileName != null) {
                ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, imageFileName));
            }
        }
    }
}
