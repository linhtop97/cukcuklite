package vn.com.misa.cukcuk.screen.adddish.adddishdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.base.listeners.OnItemClickListener;
import vn.com.misa.cukcuk.databinding.DialogDishIconSelectorBinding;
import vn.com.misa.cukcuk.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuk.screen.adddish.IconAdapter;
import vn.com.misa.cukcuk.utils.AppConstants;
import vn.com.misa.cukcuk.utils.ImageUtils;

/**
 * Dialog hiển thị danh sách icon cho món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DishIconSelectorDialog extends DialogFragment implements OnItemClickListener<String> {
    private DialogDishIconSelectorBinding mBinding;
    private List<String> mStringList;
    private AddDishActivity mAddDishActivity;

    /**
     * Phương thức khởi tạo DishIconSelectorDialog
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - DishIconSelectorDialog
     */
    public static DishIconSelectorDialog newInstance() {
        return new DishIconSelectorDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStringList = ImageUtils.getAllImage(mAddDishActivity);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_dish_icon_selector, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        Bundle bundle = getArguments();
        int lastPosition = 0;
        if (bundle != null) {
            lastPosition = bundle.getInt(AppConstants.ARG_DISH_COLOR_ID);
        }
        IconAdapter adapter = new IconAdapter(getContext(), mStringList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 5);
        mBinding.rvDishIcon.setLayoutManager(linearLayoutManager);
        adapter.setItemClickListener(this);
        mBinding.rvDishIcon.setAdapter(adapter);
        mBinding.getRoot().findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.color_dialog_height);
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Disable Back key and Search key
                switch (keyCode) {
                    case KeyEvent.KEYCODE_SEARCH:
                        return true;
                    case KeyEvent.KEYCODE_BACK:
                        return true;
                    default:
                        return false;
                }
            }
        });
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //gán, khởi tạo AddDishActivity
        mAddDishActivity = (AddDishActivity) context;
    }

    /**
     * Phương thức xử lý khi icon được lựa chọn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param fileName - tên file icon
     */
    @Override
    public void onItemClick(String fileName) {
        mAddDishActivity.setIconForDish(fileName);
        dismiss();
    }
}