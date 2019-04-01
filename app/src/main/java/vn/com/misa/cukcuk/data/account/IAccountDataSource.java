package vn.com.misa.cukcuk.data.account;

import java.util.List;

import vn.com.misa.cukcuk.data.db.EnumResult;
import vn.com.misa.cukcuk.data.model.Account;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của bảng tài khoản
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public interface IAccountDataSource {
    boolean addAccount(Account account);

    boolean deleteAccountById(int accountId);

    boolean updatePassword(Account account);

    List<Account> getAllAccount();

    boolean isAccountIfExists(String userName);

    boolean signIn(String userName, String password, boolean isRemember);

    EnumResult registerAccount(Account account);
}
