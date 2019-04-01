package vn.com.misa.cukcuk.screen.adddish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.model.Dish;
import vn.com.misa.cukcuk.data.model.MyColor;
import vn.com.misa.cukcuk.databinding.ActivityAddDishBinding;
import vn.com.misa.cukcuk.screen.adddish.adddishdialogs.CalculatorDialog;
import vn.com.misa.cukcuk.screen.adddish.adddishdialogs.ColorSelectorDialog;
import vn.com.misa.cukcuk.screen.adddish.adddishdialogs.DishIconSelectorDialog;
import vn.com.misa.cukcuk.screen.dialogs.ConfirmDeleteDialog;
import vn.com.misa.cukcuk.screen.dialogs.IConfirmDeleteCallBack;
import vn.com.misa.cukcuk.screen.menu.MenuActivity;
import vn.com.misa.cukcuk.screen.selectunit.SelectUnitActivity;
import vn.com.misa.cukcuk.utils.AppConstants;
import vn.com.misa.cukcuk.utils.ImageUtils;
import vn.com.misa.cukcuk.utils.Navigator;

import static vn.com.misa.cukcuk.screen.selectunit.SelectUnitActivity.ACTION_UNIT_SELECTED;
import static vn.com.misa.cukcuk.screen.selectunit.SelectUnitActivity.EXTRA_UNIT_SELECTED;
import static vn.com.misa.cukcuk.utils.AppConstants.COLOR_DEFAULT;
import static vn.com.misa.cukcuk.utils.AppConstants.ICON_DEFAULT;

/**
 * Màn hình thêm, sửa món ăn
 * Created_by Nguyễn Bá Linh on 25/03/2019
 */
public class AddDishActivity extends AppCompatActivity implements IAddDishContract.IView, View.OnClickListener, IConfirmDeleteCallBack {

    public static final String EXTRA_UNIT_NAME = "EXTRA_UNIT_NAME";
    public static final String ACTION_OK = "ACTION_OK";
    private static final String COLOR_DIALOG = "COLOR_DIALOG";
    private static final String DISH_ICON_DIALOG = "DISH_ICON_DIALOG";
    private static final String DELETE_DIALOG = "DELETE_DIALOG";
    private static final String CALCULATOR_DIALOG = "CALCULATOR_DIALOG";
    private ActivityAddDishBinding mBinding;
    private Navigator mNavigator;
    private Dish mDish;
    private AddDishPresenter mPresenter;
    private boolean isEdit;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction() != null) {
                    if (intent.getAction().equals(ACTION_UNIT_SELECTED)) {
                        String unit = intent.getStringExtra(EXTRA_UNIT_SELECTED);
                        if (unit != null) {
                            mBinding.tvUnit.setText(unit);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_dish);
        mNavigator = new Navigator(this);
        mPresenter = new AddDishPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ACTION_UNIT_SELECTED));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * Phương thức khởi tạo view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews() {
        setPaddingForTextToRightOfCheckBox();
        Dish dish = getIntent().getParcelableExtra(MenuActivity.EXTRA_DISH);
        if (dish != null) {
            isEdit = true;
            mBinding.tvTitle.setText(R.string.edit_dish);
            setUpView(dish);
        } else {
            hideViews();
            isEdit = false;
            mPresenter.onStart();
            mBinding.tvTitle.setText(R.string.add_dish);
            mDish = new Dish.Builder().setColor(COLOR_DEFAULT).setIcon(ICON_DEFAULT).build();
        }
    }

    /**
     * Phương thức gán các giá trị cho view khi món ăn được chỉnh sửa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     */
    private void setUpView(Dish dish) {
        mDish = dish;
        mBinding.cbState.setChecked(!dish.isSale());
        mBinding.etDishName.setText(dish.getName());
        mBinding.tvPrice.setText(String.valueOf(dish.getPrice()));
        mBinding.tvUnit.setText(dish.getUnit());
        Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
        drawable.setColorFilter(Color.parseColor(dish.getColor()), PorterDuff.Mode.SRC);
        mBinding.ivColor.setBackground(drawable);
        mBinding.ivIcon.setBackground(drawable);
        mBinding.ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(this, dish.getIcon()));
    }

    /**
     * Ẩn view khi người dùng chọn chức năng thêm món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void hideViews() {
        mBinding.cbState.setVisibility(View.GONE);
        mBinding.tvStateTitle.setVisibility(View.GONE);
        mBinding.btnDelete.setVisibility(View.GONE);
    }

    /**
     * Phương thức gán các sự kiện cho view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initEvents() {
        mBinding.btnBack.setOnClickListener(this);
        mBinding.ivColor.setOnClickListener(this);
        mBinding.btnSelectPrice.setOnClickListener(this);
        mBinding.tvPrice.setOnClickListener(this);
        mBinding.ivIcon.setOnClickListener(this);
        mBinding.tvUnit.setOnClickListener(this);
        mBinding.btnSelectUnit.setOnClickListener(this);
        mBinding.btnTake.setOnClickListener(this);
        mBinding.tvTake.setOnClickListener(this);
        mBinding.btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                //trở về màn hình trước đó
                finish();
                break;
            case R.id.ivColor:
                showColorSelector();
                break;
            case R.id.btnSelectPrice:
            case R.id.tvPrice:
                showCalculator();
                break;
            case R.id.btnSelectUnit:
            case R.id.tvUnit:
                selectUnit();
                break;
            case R.id.ivIcon:
                showDishIconSelector();
                break;
            case R.id.btnTake:
            case R.id.tvTake:
                take();
                break;
            case R.id.btnDelete:
                showDeleteDishDialogConfirm();
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức hiện máy máy tính để nhập đơn giá cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showCalculator() {
        CalculatorDialog calculatorDialog = CalculatorDialog.newInstance();
        getSupportFragmentManager().beginTransaction().add(calculatorDialog, CALCULATOR_DIALOG).commit();
    }

    /**
     * Phương thức hiển thị dialog xác nhận việc xóa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showDeleteDishDialogConfirm() {
        ConfirmDeleteDialog f = ConfirmDeleteDialog.newInstance(mDish.getName());
        f.setCallBack(this);
        getSupportFragmentManager().beginTransaction().add(f, DELETE_DIALOG).commit();
    }

    /**
     * Khởi chạy màn hình lựa chọn đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void selectUnit() {
        Intent intent = new Intent();
        intent.setClass(this, SelectUnitActivity.class);
        intent.putExtra(EXTRA_UNIT_NAME, mBinding.tvUnit.getText().toString().trim());
        mNavigator.startActivity(intent);
    }

    /**
     * Phương thức Cất với cả 2 trường hợp là sửa món và thêm món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void take() {
        if (isEdit) {
            //sửa món
            updateDish();
        } else {
            //thêm món
            addDish();
        }
    }

    /**
     * Phương thức sửa món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void updateDish() {
        mPresenter.updateDish(mDish.getId(), mBinding.etDishName.getText().toString().trim(),
                mBinding.tvPrice.getText().toString(),
                mBinding.tvUnit.getText().toString(),
                mDish.getColor(),
                mDish.getIcon(), mBinding.cbState.isChecked());
    }

    /**
     * Phương thức thêm món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void addDish() {
        mPresenter.addDish(mBinding.etDishName.getText().toString().trim(),
                mBinding.tvPrice.getText().toString(),
                mBinding.tvUnit.getText().toString(),
                mDish.getColor(),
                mDish.getIcon());
    }

    /**
     * Tăng khoảng cách giữa text và icon của checkbox
     * Created_by Nguyễn Bá Linh on 20/03/2019
     */
    private void setPaddingForTextToRightOfCheckBox() {
        final float scale = this.getResources().getDisplayMetrics().density;
        mBinding.cbState.setPadding(mBinding.cbState.getPaddingLeft() + (int) (8.0f * scale + 0.5f),
                mBinding.cbState.getPaddingTop(),
                mBinding.cbState.getPaddingRight(),
                mBinding.cbState.getPaddingBottom());
    }

    /**
     * Phương thức hiển thị dialog lựa chọn icon cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showDishIconSelector() {
        DishIconSelectorDialog f = DishIconSelectorDialog.newInstance();
        getSupportFragmentManager().beginTransaction().add(f, DISH_ICON_DIALOG).commit();
    }

    /**
     * Phương thức hiển thị dialog lựa chọn background cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showColorSelector() {
        ColorSelectorDialog f = ColorSelectorDialog.newInstance(AppConstants.POSITION_DEFAULT);
        getSupportFragmentManager().beginTransaction().add(f, COLOR_DIALOG).commit();
    }

    /**
     * Phương thức đặt màu cho image view Color và Image View Icon
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param myColor - màu
     */
    public void setColorImageButton(MyColor myColor) {
        try {
            if (myColor != null) {
                String colorBackground = myColor.getColor();
                mDish.setColor(colorBackground);
                Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
                drawable.setColorFilter(Color.parseColor(myColor.getColor()), PorterDuff.Mode.SRC);
                mBinding.ivColor.setBackground(drawable);
                mBinding.ivIcon.setBackground(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức đặt icon cho ImageView Icon lựa chọn icon
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param sIconFileName - tên file name của icon
     */
    public void setIconForDish(String sIconFileName) {
        try {
            if (sIconFileName != null) {
                mDish.setIcon(sIconFileName);
                mBinding.ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(this, sIconFileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thông báo khi người dùng không nhập tên món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void dishNameEmpty() {
        mNavigator.showToast(R.string.dish_name_not_allow_empty);
        mBinding.etDishName.requestFocus();
    }

    /**
     * Thông báo cho người dùng đã thêm món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void addDishSuccess() {
        mNavigator.showToast(R.string.add_dish_success);
        finishTask();
    }

    /**
     * Thông báo cho người dùng đã thêm món ăn thất bại
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void addDishFailed(int error) {
        mNavigator.showToast(error);
    }

    /**
     * Đặt đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - tên đơn vị
     */
    @Override
    public void setUnit(String unit) {
        mBinding.tvUnit.setText(unit);
    }

    /**
     * Thông báo cho người dùng đã cập nhật món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void upDateDishSuccess() {
        mNavigator.showToast(R.string.update_dish_success);
        finishTask();
    }

    /**
     * Thoát màn hình và bắn sự kiện hoàn thành tác vụ
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void finishTask() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_OK));
        finish();
    }

    /**
     * Thông báo cho người dùng đã xóa món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void deleteDishSuccess() {
        mNavigator.showToast(R.string.delete_success);
        finishTask();
    }

    /**
     * Nhận thông điệp và xử lý
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param message
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToast(message);
    }

    /**
     * Phương thức xóa món ăn
     * Created_by Nguyễn Bá Linh on 28/03/2019
     */
    @Override
    public void acceptDelete() {
        mPresenter.deleteDish(mDish.getId());
    }
}
