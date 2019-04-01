package vn.com.misa.cukcuk.screen.adddish;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.base.adapters.ListAdapter;
import vn.com.misa.cukcuk.base.listeners.OnItemClickListener;
import vn.com.misa.cukcuk.data.model.MyColor;

/**
 * Adapter cho danh sách màu nền món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class ColorAdapter extends ListAdapter<MyColor> {

    private int mLastSelectedPosition;
    private OnItemClickListener<Integer> mListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public ColorAdapter(Context context, List<MyColor> myColors, int lastSelectedPosition) {
        super(context);
        mLastSelectedPosition = lastSelectedPosition;
        mListData = myColors;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ColorViewHolder colorViewHolder = (ColorViewHolder) holder;
        if (position == mLastSelectedPosition) {
            colorViewHolder.btnColor.setImageResource(R.drawable.ic_check_white);
        } else {
            colorViewHolder.btnColor.setImageResource(0);
        }
        colorViewHolder.bind(mListData.get(position).getColor());
        colorViewHolder.btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }


    public void setListener(OnItemClickListener<Integer> listener) {
        mListener = listener;
    }

    /**
     * Lớp Item cho danh sách màu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public class ColorViewHolder extends RecyclerView.ViewHolder {

        private ImageButton btnColor;
        private Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);

        ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            btnColor = itemView.findViewById(R.id.btnColor);
        }

        /**
         * Phương thức gán màu cho ImageButton
         * @param color - mã màu
         */
        void bind(String color) {
            if (color != null) {
                drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC);
                btnColor.setBackground(drawable);
            }
        }
    }
}