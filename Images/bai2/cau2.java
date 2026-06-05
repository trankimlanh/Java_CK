package Images.bai2;


import java.sql.Connection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class cau2 {
    public static void nhapDuLieu(Connection con, String data1, String data2) {
        List<String> danhSachDongVat = new ArrayList<>();
        List<String> danhSachThucAn = new ArrayList<>();

        // 1. Nhập dữ liệu ĐỘNG VẬT và lưu vào List
        try {
            BufferedReader br = new BufferedReader(new FileReader(data1));
            String line;
            String sql = "insert into DONGVAT (TenDongVat,Loai) values(?,?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                while ((line = br.readLine()) != null) {
                    // Bỏ qua dòng trống
                    if (line.trim().isEmpty())
                        continue;

                    String[] data = line.split(",");
                    if (data.length >= 2) {
                        pstmt.setString(1, data[0].trim());
                        pstmt.setString(2, data[1].trim());
                        pstmt.executeUpdate();
                        danhSachDongVat.add(data[0]); // Lưu tên con vật
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Nhập dữ liệu THỨC ĂN và lưu vào List
        try {
            BufferedReader br = new BufferedReader(new FileReader(data2));
            String line;
            String sql = "insert into THUCAN (TenThucAn,Loai) values(?,?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                while ((line = br.readLine()) != null) {
                    // Bỏ qua dòng trống
                    if (line.trim().isEmpty())
                        continue;

                    String[] data = line.split(",");
                    if (data.length >= 2) {
                        pstmt.setString(1, data[0]);
                        pstmt.setString(2, data[1]);
                        pstmt.executeUpdate();
                        danhSachThucAn.add(data[0]); // Lưu tên thức ăn
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Ghép cặp kiểm tra TẤT CẢ động vật với TẤT CẢ thức ăn
        String sqlAN = "INSERT INTO AN (TenDongVat, TenThucAn, CoTheAnDuoc) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sqlAN)) {
            for (String tenDongVat : danhSachDongVat) {
                for (String tenThucAn : danhSachThucAn) {
                    String ketQua = kiemTraThucAn(tenDongVat, tenThucAn);
                    pstmt.setString(1, tenDongVat);
                    pstmt.setString(2, tenThucAn);
                    pstmt.setString(3, ketQua);
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Xử lý dữ liệu hoàn tất!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String kiemTraThucAn(String conVat, String thucAn) {
        // Chuyển về chữ thường hết để so sánh cho chắc chắn
        conVat = conVat.toLowerCase().trim();
        thucAn = thucAn.toLowerCase().trim();

        if (conVat.equals("bo") && thucAn.equals("co"))
            return "DUOC";
        if (conVat.equals("meo") && thucAn.equals("thit"))
            return "DUOC";
        if (conVat.equals("tho") && (thucAn.equals("ca rot") || thucAn.equals("cà rốt")))
            return "DUOC";

        return "KHONG DUOC";
    }

    public static void main(String[] args) {
        Connection con = ConnectDB.getInstance().getConnection();
        nhapDuLieu(con, "Images\\bai2\\data1.txt", "Images\\bai2\\data2.txt");
    }
}
