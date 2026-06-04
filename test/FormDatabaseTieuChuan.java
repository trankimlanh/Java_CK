package test;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class FormDatabaseTieuChuan extends JFrame {

    // 1. CẤU HÌNH KẾT NỐI SQL SERVER (Thay đổi thông tin này khi làm bài)
    private final String DB_URL = "jdbc:sqlserver://LAPTOP-HHOVEV8F\\SQLEXPRESS;databaseName=QLQNet;encrypt=true;trustServerCertificate=true;";
    private final String DB_USER = "sa"; 
    private final String DB_PASS = "123123";

    // 2. KHAI BÁO CÁC THÀNH PHẦN GIAO DIỆN
    private JTextField txtMa, txtTen;
    private JButton btnAdd, btnDelete, btnLoad, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    public FormDatabaseTieuChuan() {
        super("Mẫu Giao Diện Quản Lý CSDL Chuẩn");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Giữa màn hình

        initUI();
        initEvents();
        
        // Tự động tải dữ liệu khi vừa mở form
        // loadData(); 
    }

    // =========================================================
    // KHU VỰC THIẾT KẾ GIAO DIỆN (Layout tự động)
    // =========================================================
    private void initUI() {
        // --- VÙNG 1: NHẬP LIỆU (Phía trên - NORTH) ---
        // Dùng GridLayout(số hàng, số cột, khoảng cách ngang, khoảng cách dọc)
        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10)); 
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));
        
        txtMa = new JTextField();
        txtTen = new JTextField();
        
        panelInput.add(new JLabel("Mã:"));
        panelInput.add(txtMa);
        panelInput.add(new JLabel("Tên:"));
        panelInput.add(txtTen);

        // --- VÙNG 2: NÚT BẤM (Ở giữa - CENTER) ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm");
        btnDelete = new JButton("Xóa");
        btnLoad = new JButton("Tải Dữ Liệu");
        btnClear = new JButton("Làm Mới");
        
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnLoad);
        panelButtons.add(btnClear);

        // Gom vùng Nhập và Nút bấm vào chung một khối phía trên
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelInput, BorderLayout.NORTH);
        panelTop.add(panelButtons, BorderLayout.CENTER);

        // --- VÙNG 3: BẢNG DỮ LIỆU (Phía dưới - SOUTH) ---
        // Khởi tạo bảng với các cột cơ bản
        String[] columns = {"Mã", "Tên"}; 
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
        // Bắt buộc dùng JScrollPane để bảng có thanh cuộn và hiện tiêu đề cột
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách dữ liệu"));

        // --- RÁP VÀO FRAME CHÍNH ---
        setLayout(new BorderLayout(10, 10));
        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // Cho bảng chiếm toàn bộ không gian còn lại
    }

    // =========================================================
    // KHU VỰC BẮT SỰ KIỆN NÚT BẤM
    // =========================================================
    private void initEvents() {
        btnClear.addActionListener(e -> {
            txtMa.setText("");
            txtTen.setText("");
            txtMa.requestFocus();
        });

        btnLoad.addActionListener(e -> loadData());
        btnAdd.addActionListener(e -> insertData());
        
        // Lấy dữ liệu từ dòng được click trên bảng đẩy ngược lên TextField
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMa.setText(tableModel.getValueAt(row, 0).toString());
                    txtTen.setText(tableModel.getValueAt(row, 1).toString());
                }
            }
        });
    }

    // =========================================================
    // KHU VỰC TƯƠNG TÁC SQL SERVER (JDBC)
    // =========================================================

    // 1. Hàm dùng chung để tạo kết nối
    private Connection getConnection() throws Exception {
        // Tùy phiên bản JDBC, có thể bạn cần dòng Class.forName này
        // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // 2. Lệnh SELECT - Đổ dữ liệu vào bảng
    private void loadData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trên bảng
        String sql = "SELECT * FROM NguoiDung"; // Sửa lại tên bảng ở đây
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("MaNguoiDung")); // Sửa lại tên cột trong DB
                row.add(rs.getString("matKhau")); // Sửa lại tên cột trong DB
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage());
        }
    }

    // 3. Lệnh INSERT - Thêm dữ liệu mới
    private void insertData() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
            return;
        }

        String sql = "INSERT INTO TenBangCuaBan (MaCot1, MaCot2) VALUES (?, ?)"; // Dùng ? để chống lỗi SQL Injection
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, ma);
            pstmt.setString(2, ten);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData(); // Cập nhật lại bảng
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu: " + ex.getMessage());
        }
    }

    // Hàm Main để chạy
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormDatabaseTieuChuan().setVisible(true));
    }
}
