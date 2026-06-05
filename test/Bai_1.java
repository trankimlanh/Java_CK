package test;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Bai_1 extends JFrame {
    private JTextField inputText;
    private JComboBox<String> actionSelect;
    private JTextArea resultArea;
    private JButton btnView, btnReset, btnExit;

    public Bai_1() {
        super("Xử lý chuỗi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // Tạo các thành phần
        inputText    = new JTextField(30);
        actionSelect = new JComboBox<>(new String[]{"Đếm từ", "Đếm từ trùng lặp", "Đảo chuỗi"});
        resultArea   = new JTextArea(8, 30);
        resultArea.setEditable(false);

        btnView  = new JButton("View");
        btnReset = new JButton("Reset");
        btnExit  = new JButton("Exit");

        // Hàng 1: Nhập chuỗi
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.add(new JLabel("Nhập chuỗi:"));
        row1.add(inputText);

        // Hàng 2: Chọn thao tác
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.add(new JLabel("Thực hiện:"));
        row2.add(actionSelect);

        // Hàng 3: Kết quả
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row3.add(new JLabel("Kết quả:"));
        row3.add(new JScrollPane(resultArea));

        // Hàng 4: Các nút chức năng
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row4.add(btnView);
        row4.add(btnReset);
        row4.add(btnExit);

        // Đưa tất cả vào panel chính theo BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);

        setContentPane(mainPanel);

        // Gắn sự kiện
        btnView.addActionListener(e -> doAction());
        btnReset.addActionListener(e -> {
            inputText.setText("");
            resultArea.setText("");
        });
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void doAction() {
        String text = inputText.getText().trim();
        if (text.isEmpty()) {
            resultArea.setText("Vui lòng nhập chuỗi!");
            return;
        }

        String[] words = text.split("\\s+");
        String choice = (String) actionSelect.getSelectedItem();

        switch (choice) {
            case "Đếm từ":
                resultArea.setText("Tổng số từ: " + words.length);
                break;

            case "Đếm từ trùng lặp":
                // Dùng LinkedHashMap để giữ thứ tự xuất hiện
                Map<String,Integer> freq = new LinkedHashMap<>();
                for (String w : words) {
                    freq.merge(w, 1, Integer::sum);
                }
                StringBuilder sb = new StringBuilder();
                freq.forEach((k, v) -> sb.append(k)
                                         .append(": ")
                                         .append(v)
                                         .append(" lần\n"));
                resultArea.setText(sb.toString());
                break;

            case "Đảo chuỗi":
                // Đảo ngược danh sách từ
                List<String> list = new ArrayList<>(Arrays.asList(words));
                Collections.reverse(list);
                resultArea.setText(String.join(" ", list));
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai_1().setVisible(true));
    }
}
