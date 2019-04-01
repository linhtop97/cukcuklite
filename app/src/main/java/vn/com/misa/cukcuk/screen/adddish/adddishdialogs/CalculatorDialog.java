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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.Objects;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.databinding.DialogCalculatorBinding;
import vn.com.misa.cukcuk.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuk.utils.DimensionUtils;

/**
 * Dialog hiển thị xác nhận xóa món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class CalculatorDialog extends DialogFragment implements View.OnClickListener {
    private DialogCalculatorBinding mBinding;
    private AddDishActivity mAddDishActivity;

    public static CalculatorDialog newInstance() {
        return new CalculatorDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_calculator, container, false);
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
        mBinding.btnClear.setOnClickListener(this);
        mBinding.btnDecrease.setOnClickListener(this);
        mBinding.btnIncrease.setOnClickListener(this);
        mBinding.btnSeven.setOnClickListener(this);
        mBinding.btnEight.setOnClickListener(this);
        mBinding.btnNine.setOnClickListener(this);
        mBinding.btnSubtraction.setOnClickListener(this);
        mBinding.btnFour.setOnClickListener(this);
        mBinding.btnFive.setOnClickListener(this);
        mBinding.btnSix.setOnClickListener(this);
        mBinding.btnSummation.setOnClickListener(this);
        mBinding.btnOne.setOnClickListener(this);
        mBinding.btnTwo.setOnClickListener(this);
        mBinding.btnThree.setOnClickListener(this);
        mBinding.btnSumSub.setOnClickListener(this);
        mBinding.btnZero.setOnClickListener(this);
        mBinding.btnThousand.setOnClickListener(this);
        mBinding.btnComma.setOnClickListener(this);
        mBinding.btnEqualDone.setOnClickListener(this);
        mBinding.btnClose.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = (int) (DimensionUtils.getScreenWidthInPixels(mAddDishActivity) - DimensionUtils.convertDpToPixel(32.0f));
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
        //dialog.setCanceledOnTouchOutside(false);
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

    @Override
    public void onClick(View v) {
        String price = mBinding.etPrice.getText().toString();
        switch (v.getId()) {
            case R.id.btnClearAll:
                mBinding.etPrice.setText("");
                break;
            case R.id.btnDecrease:
                if (price.length() > 0)
                    mBinding.etPrice.setText((String.valueOf(Integer.parseInt(price) - 1)));
                break;
            case R.id.btnIncrease:
                if (price.length() > 0)
                    mBinding.etPrice.setText((String.valueOf(Integer.parseInt(price) + 1)));
                break;
            case R.id.btnClear:
                if (price.length() > 0)
                    mBinding.etPrice.setText(price.substring(0, price.length() - 1));
                break;
            case R.id.btnSeven:
                break;
            case R.id.btnEight:
                break;
            case R.id.btnNine:
                break;
            case R.id.btnSubtraction:
                break;
            case R.id.btnFour:
                break;
            case R.id.btnFive:
                break;
            case R.id.btnSummation:
                break;
            case R.id.btnOne:
                break;
            case R.id.btnTwo:
                break;
            case R.id.btnThree:
                break;
            case R.id.btnSumSub:
                break;
            case R.id.btnZero:
                break;
            case R.id.btnThousand:
                break;
            case R.id.btnComma:
                break;
            case R.id.btnEqualDone:
                break;
            case R.id.btnClose:
                dismiss();
                break;
            default:
                break;
        }
        mBinding.etPrice.setSelection(mBinding.etPrice.getText().length());
    }

    private void formatNumber(String stringNumber) {

    }
}