package vn.com.misa.cukcuk.data.unit;

import java.util.List;

import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.model.Unit;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của bảng đơn vị
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public interface IUnitDataSource {
    boolean addUnit(String unitName);

    EnumResult addUnitToDatabase(String unitName);

    boolean deleteUnit(Unit unit);

    boolean updateUnit(Unit unit);

    EnumResult updateUnitToDatabase(Unit unit);
    List<String> getAllUnitName();

    List<Unit> getAllUnit();

    boolean isUnitIfExists(String dishName);
}
