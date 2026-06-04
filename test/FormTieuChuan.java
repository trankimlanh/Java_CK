package test;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FormTieuChuan extends JFrame {
    // 1. KHAI BÁO CÁC THÀNH PHẦN GIAO DIỆN CẦN LẤY DỮ LIỆU
    private JTextField txtInput1, txtInput2;
    private JTextArea txtAreaResult;
    private JButton btnProcess, btnSaveFile, btnReadFile, btnClear;

    // Tên file mặc định để lưu dữ liệu
    private final String FILE_NAME = "data.txt";

    public FormTieuChuan() {
        // Thiết lập khung cửa sổ cơ bản
        super("Mẫu Giao Diện Chuẩn");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initUI();
        initEvents();
    }

    private void initUI() {
        // --- VÙNG 1: NHẬP LIỆU (Phía trên - NORTH) ---
        JPanel panelInput = new JPanel(new GridLayout(2, 2, 5, 5)); // 2 hàng, 2 cột
        panelInput.setBorder(BorderFactory.createTitledBorder("Nhập thông tin"));
        
        txtInput1 = new JTextField();
        txtInput2 = new JTextField();
        
        panelInput.add(new JLabel("Dữ liệu 1:"));
        panelInput.add(txtInput1);
        panelInput.add(new JLabel("Dữ liệu 2:"));
        panelInput.add(txtInput2);

        // --- VÙNG 2: NÚT BẤM (Ở giữa - CENTER) ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnProcess = new JButton("Xử lý Logic");
        btnSaveFile = new JButton("Lưu File");
        btnReadFile = new JButton("Đọc File");
        btnClear = new JButton("Xóa Trắng");
        
        panelButtons.add(btnProcess);
        panelButtons.add(btnSaveFile);
        panelButtons.add(btnReadFile);
        panelButtons.add(btnClear);

        // --- VÙNG 3: HIỂN THỊ KẾT QUẢ (Phía dưới - SOUTH) ---
        txtAreaResult = new JTextArea(10, 50);
        txtAreaResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResult);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả & Lịch sử"));

        // --- RÁP TẤT CẢ VÀO FRAME BẰNG BORDERLAYOUT ---
        setLayout(new BorderLayout(10, 10));
        add(panelInput, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void initEvents() {
        // Bắt sự kiện Xóa trắng
        btnClear.addActionListener(e -> {
            txtInput1.setText("");
            txtInput2.setText("");
            txtAreaResult.setText("");
            txtInput1.requestFocus(); // Đưa con trỏ chuột về ô đầu tiên
        });

        // Bắt sự kiện Xử lý logic nghiệp vụ
        btnProcess.addActionListener(e -> processLogic());

        // Bắt sự kiện Lưu File
        btnSaveFile.addActionListener(e -> saveToFile());

        // Bắt sự kiện Đọc File
        btnReadFile.addActionListener(e -> readFromFile());
    }

    // =========================================================
    // KHU VỰC "LẮP RÁP" LOGIC - ĐỀ YÊU CẦU GÌ THÌ VIẾT VÀO ĐÂY
    // =========================================================
    
    private void processLogic() {
        String data1 = txtInput1.getText().trim();
        String data2 = txtInput2.getText().trim();
        
        if (data1.isEmpty() || data2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: Xử lý logic ở đây (Cộng trừ, tách chuỗi, tìm kiếm...)
        String result = "Đã xử lý: " + data1 + " - " + data2;
        
        // In ra màn hình
        txtAreaResult.append(result + "\n");
    }

    // =========================================================
    // KHU VỰC ĐỌC / GHI FILE CHUẨN (Dùng Character Stream)
    // =========================================================

    private void saveToFile() {
        String contentToSave = txtAreaResult.getText();
        if (contentToSave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để lưu!");
            return;
        }

        // Dùng try-with-resources để tự động đóng luồng (khỏi lo rò rỉ bộ nhớ)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(contentToSave);
            JOptionPane.showMessageDialog(this, "Lưu file thành công vào: " + FILE_NAME);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi ghi file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "File chưa tồn tại, vui lòng lưu file trước!");
            return;
        }

        txtAreaResult.setText(""); // Xóa màn hình cũ trước khi đọc mới
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                txtAreaResult.append(line + "\n");
            }
            JOptionPane.showMessageDialog(this, "Đọc file thành công!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi đọc file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm Main để chạy chương trình
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormTieuChuan().setVisible(true));
    }
}