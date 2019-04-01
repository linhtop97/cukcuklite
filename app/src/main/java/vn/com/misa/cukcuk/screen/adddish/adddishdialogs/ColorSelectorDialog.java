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
import java.util.Objects;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.base.listeners.OnItemClickListener;
import vn.com.misa.cukcuk.data.model.MyColor;
import vn.com.misa.cukcuk.databinding.DialogColorSelectorBinding;
import vn.com.misa.cukcuk.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuk.screen.adddish.ColorAdapter;
import vn.com.misa.cukcuk.utils.AppConstants;

/**
 * Dialog hiển thị lựa chọn background cho icon của món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class ColorSelectorDialog extends DialogFragment implements OnItemClickListener<Integer> {
    private DialogColorSelectorBinding mBinding;
    private List<MyColor> mListColor;
    private AddDishActivity mAddDishActivity;

    public static ColorSelectorDialog newInstance(int dishColorId) {
        ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ARG_DISH_COLOR_ID, dishColorId);
        colorSelectorDialog.setArguments(bundle);
        return colorSelectorDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListColor = MyColor.getListColor();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_color_selector, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    /**
     * Phương thức khởi tạo các view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews() {
        Bundle bundle = getArguments();
        int lastPosition = 0;
        if (bundle != null) {
            lastPosition = bundle.getInt(AppConstants.ARG_DISH_COLOR_ID);
        }
        ColorAdapter adapter = new ColorAdapter(getContext(), mListColor, lastPosition);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 4);
        mBinding.rvColor.setLayoutManager(linearLayoutManager);
        adapter.setListener(this);
        mBinding.rvColor.setAdapter(adapter);
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
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
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
     * Phương thức xử lý khi item color được click
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param position - vị trí của item color click
     */
    @Override
    public void onItemClick(Integer position) {
        mAddDishActivity.setColorImageButton(mListColor.get(position));
        dismiss();
    }
}