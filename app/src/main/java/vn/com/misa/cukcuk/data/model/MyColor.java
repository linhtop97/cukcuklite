package vn.com.misa.cukcuk.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp màu cho nền icon của món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class MyColor {
    private int mId;
    private String mColor;

    public MyColor(int id, String color) {
        mId = id;
        mColor = color;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    /**
     * Phương thức lấy các màu mặc định cho nền icon của món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách mã màu
     */
    public static List<MyColor> getListColor() {
        int size = COLOR_ARRAY.length;
        List<MyColor> myColors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            myColors.add(new MyColor(i, COLOR_ARRAY[i]));
        }
        return myColors;
    }

    //biến chứa các mã màu mặc định cho nền món ăn
    private static final String[] COLOR_ARRAY = {
            "#26C6DA",
            "#0097A7",
            "#0D47A1",
            "#1565C0",
            "#039BE5",
            "#64B5F6",
            "#FF6F00",
            "#FFA000",
            "#FFB300",
            "#CF9A0A",
            "#8D6E63",
            "#6D4C41",
            "#D32F2F",
            "#FF1744",
            "#F44336",
            "#EC407A",
            "#AD1457",
            "#6A1B9A",
            "#AB47BC",
            "#BA68C8",
            "#00695C",
            "#00897B",
            "#4DB6AC",
            "#2E7D32",
            "#43A047",
            "#64DD17",
            "#212121",
            "#5F7C8A",
            "#B0BEC5",
            "#455A64",
            "#607D8B",
            "#90A4AE"
    };
}
