package vn.com.misa.cukcuk.screen.menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import vn.com.misa.cukcuk.R;
import vn.com.misa.cukcuk.base.listeners.OnItemClickListener;
import vn.com.misa.cukcuk.data.model.Dish;
import vn.com.misa.cukcuk.databinding.ActivityMenuBinding;
import vn.com.misa.cukcuk.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuk.utils.Navigator;

/**
 * Màn hình thực đơn cho phép thêm, sửa món ăn
 * Created_by Nguyễn Bá Linh on 25/03/2019
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener, IMenuContract.IView, OnItemClickListener<Dish> {

    public static final String EXTRA_DISH = "EXTRA_DISH";
    private ActivityMenuBinding mBinding;
    private Navigator mNavigator;
    private MenuPresenter mPresenter;
    private DishAdapter mAdapter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(AddDishActivity.ACTION_OK)) {
                    mPresenter.onStart();
                }
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        mNavigator = new Navigator(this);
        mPresenter = new MenuPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        mPresenter.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(AddDishActivity.ACTION_OK));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * Phương thức gắn các sự kiện cho view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initEvents() {
        mBinding.btnAdd.setOnClickListener(this);
    }

    /**
     * Phương thức khởi tạo view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews() {
        mAdapter = new DishAdapter(this, null);
        mAdapter.setItemClickListener(this);
        mBinding.rvDish.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvDish.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                mNavigator.startActivity(AddDishActivity.class);
                break;
        }
    }

    /**
     * Phương thức hiển thị danh sách món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishes - danh sách món ăn
     */
    @Override
    public void showDish(List<Dish> dishes) {
        mAdapter.setListData(dishes);
    }

    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức xử lý sự kiện khi item món ăn được click
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param data là tham số được truyền vào khi View được click
     */
    @Override
    public void onItemClick(Dish data) {
        Intent intent = new Intent();
        intent.setClass(this, AddDishActivity.class);
        intent.putExtra(EXTRA_DISH, data);
        mNavigator.startActivity(intent);
    }
}
