package vn.com.misa.cukcuk.screen.selectunit;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.data.model.Unit;
import vn.com.misa.cukcuk.databinding.ActivitySelectUnitBinding;
import vn.com.misa.cukcuk.screen.dialogs.ConfirmDeleteDialog;
import vn.com.misa.cukcuk.screen.dialogs.IConfirmDeleteCallBack;
import vn.com.misa.cukcuk.utils.Navigator;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static vn.com.misa.cukcuk.screen.adddish.AddDishActivity.EXTRA_UNIT_NAME;

/**
 * Màn hình chọn đơn vị
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class SelectUnitActivity extends AppCompatActivity implements IUnitContract.IView, IEditUnitListener, View.OnClickListener, ILongClickUnitListener, IConfirmDeleteCallBack {

    public static final String ACTION_UNIT_SELECTED = "ACTION_UNIT_SELECTED";
    public static final String EXTRA_UNIT_SELECTED = "EXTRA_UNIT_SELECTED";
    private static final String ADD_EDIT_DIALOG = "ADD_EDIT_DIALOG";
    private static final String DELETE_DIALOG = "DELETE_DIALOG";
    private ActivitySelectUnitBinding mBinding;
    private SelectUnitAdapter mAdapter;
    private SelectUnitPresenter mPresenter;
    private Navigator mNavigator;
    private ConfirmAddEditUnitDialog mAddEditUnitDialog;
    private Unit mUnit;
    private int mUnitPositionRemove;
    private List<Unit> mUnits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_unit);
        mNavigator = new Navigator(this);
        mPresenter = new SelectUnitPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        mPresenter.onStart(getIntent().getStringExtra(EXTRA_UNIT_NAME));

    }

    /**
     * Phương thức gắn các sự kiện cho view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initEvents() {
        mBinding.btnDone.setOnClickListener(this);
        mBinding.btnBack.setOnClickListener(this);
        mBinding.btnAdd.setOnClickListener(this);
    }


    /**
     * Phương thức khởi tạo cho view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews() {
        mUnits = new ArrayList<>();
        mAdapter = new SelectUnitAdapter(this, mUnits);
        mAdapter.setEditUnitListener(this);
        mAdapter.setItemLongClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rvUnit.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        mBinding.rvUnit.addItemDecoration(itemDecor);
        mBinding.rvUnit.setAdapter(mAdapter);
    }

    /**
     * Phương thức hiển thị danh sách đơn vị món ăn lên màn hình
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param allUnit            - danh sánh các đơn vị
     * @param lastSelectPosition - vị trí đơn vị đã được chọn trước đó
     */
    @Override
    public void showUnit(List<Unit> allUnit, int lastSelectPosition) {
        mUnits = allUnit;
        mAdapter.setLastSelectPosition(lastSelectPosition);
        mAdapter.setListData(mUnits);
    }

    /**
     * Thông báo khi người dùng không nhập tên đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void unitNameEmpty() {
        mNavigator.showToast(R.string.unit_name_not_allow_empty);
    }

    /**
     * Thông báo cho người dùng đã thêm đơn vị thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void addUnitSuccess(String unitName) {
        unitSelected(unitName);
    }

    /**
     * Phương thức đóng dialog khi thêm hoặc sửa đơn vị thành công
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - tên đơn vị
     */
    private void unitSelected(String unitName) {
        if (mAddEditUnitDialog != null && mAddEditUnitDialog.isAdded()) {
            mAddEditUnitDialog.dismiss();
        }
        mNavigator.showToast(R.string.add_unit_success);
        setUnit(unitName);
    }

    @Override
    public void updateUnitSuccess(String unitName) {
        unitSelected(unitName);
    }

    /**
     * Thông báo cho người dùng đã xóa món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void deleteUnitSuccess() {
        mNavigator.showToast(R.string.delete_success);
        int size = mUnits.size();
        int unitId  = mUnit.getId();
        for (int i = 0; i < size; i++) {
            if (mUnits.get(i).getId() == unitId) {
                mUnits.remove(i);
                break;
            }
        }
        mAdapter.notifyItemRemoved(mUnitPositionRemove);
    }


    /**
     * Thông báo cho người dùng đã thêm đơn vị thất bại
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void addUnitFailed(int error) {
        mNavigator.showToast(error);
    }


    /**
     * Phương thức nhận thông điệp
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param message - thông điệp
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToast(message);
    }

    /**
     * Phương thức hiển thị dialog sửa đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void onEditUnitItem(Unit unit) {
        showDialogEditAddUnit(unit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                setUnit(mAdapter.getUnitName());
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnAdd:
                showDialogEditAddUnit(null);
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức lựa chọn đơn vị và trở về màn hình thêm món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void setUnit(String unitName) {
        Intent intent = new Intent(ACTION_UNIT_SELECTED);
        intent.putExtra(EXTRA_UNIT_SELECTED, unitName);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    /**
     * Phương thức hiển thị dialog thêm hoặc sửa đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị tính(có thể null). Nếu null sẽ hiển thị dialog thêm đơn vị
     *             nếu khác null sẽ hiển thị dialog sửa đơn vị
     */
    private void showDialogEditAddUnit(Unit unit) {
        mAddEditUnitDialog = ConfirmAddEditUnitDialog.newInstance(unit);
        getSupportFragmentManager().beginTransaction().add(mAddEditUnitDialog, ADD_EDIT_DIALOG).commit();
    }

    /**
     * Phương thức cập nhật đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     */
    public void updateUnit(Unit unit) {
        mPresenter.updateUnit(unit);
    }

    /**
     * Phương thức thêm đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - tên đơn vị
     */
    public void addUnit(String unitName) {
        mPresenter.addUnit(unitName);
    }

    /**
     * Phương thức hiển thị popup menu xác nhận xóa đơn vị
     * Created_by Nguyễn Bá Linh on 28/03/2019
     *
     * @param unit - đơn vị
     * @param view - view hiển thị popup
     */
    @Override
    public void onLongClickUnit(final Unit unit, int position, View view) {
        mUnit = unit;
        mUnitPositionRemove = position;
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.menu_unit);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnDeleteUnit:
                        showConfirmDeleteUnitDialog(unit.getUnit(), SelectUnitActivity.this);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    /**
     * Phương thức hiển thị dialog xác nhận việc xóa đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showConfirmDeleteUnitDialog(String unitName, IConfirmDeleteCallBack callBack) {
        ConfirmDeleteDialog f = ConfirmDeleteDialog.newInstance(unitName);
        f.setCallBack(callBack);
        getSupportFragmentManager().beginTransaction().add(f, DELETE_DIALOG).commit();
    }

    /**
     * Phương thức xóa đơn vị
     * Created_by Nguyễn Bá Linh on 28/03/2019
     */
    @Override
    public void acceptDelete() {
        mPresenter.deleteUnit(mUnit);
    }
}
