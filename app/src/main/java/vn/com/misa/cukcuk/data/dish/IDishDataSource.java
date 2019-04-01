package vn.com.misa.cukcuk.data.dish;

import java.util.List;

import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.model.Dish;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của bảng món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public interface IDishDataSource {
    boolean addDish(Dish dish);

    EnumResult addDishToDatabase(Dish dish);

    EnumResult updateDishToDatabase(Dish dish);

    boolean deleteDishById(int dishId);

    boolean updateDish(Dish dish);

    List<String> getAllDishName();

    List<Dish> getAllDish();

    boolean isDishIfExists(String dishName);
}
