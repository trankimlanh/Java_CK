package Images.bai1;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Panel;

public class Cau1 extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txt1;
    JTextArea txta;
    JComboBox list;
    JButton xoa;
    JButton thoat;
    JButton tachso;
    JLabel lb1;
    JLabel lb2;
    JLabel lb3;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cau1 frame = new Cau1();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Cau1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lb1 = new JLabel("Nhập số tự nhiên n: ");
        lb1.setBounds(28, 40, 113, 26);
        contentPane.add(lb1);

        txt1 = new JTextField();
        txt1.setBounds(167, 43, 113, 20);
        contentPane.add(txt1);
        txt1.setColumns(10);

        xoa = new JButton("Xóa");
        xoa.setBounds(312, 42, 89, 23);
        contentPane.add(xoa);
        xoa.addActionListener(this);

        lb2 = new JLabel("Tách theo chiều: ");
        lb2.setBounds(28, 77, 113, 26);
        contentPane.add(lb2);

        list = new JComboBox();
        list.setModel(new DefaultComboBoxModel(new String[] { "Từ trái sang phải", "Từ phải sang trái" }));
        list.setBounds(167, 79, 113, 22);
        contentPane.add(list);

        tachso = new JButton("Tách số");
        tachso.setBounds(312, 76, 89, 23);
        contentPane.add(tachso);
        tachso.addActionListener(this);

        txta = new JTextArea();
        txta.setBounds(167, 126, 234, 88);
        contentPane.add(txta);

        lb3 = new JLabel("Kết quả tách được:");
        lb3.setBounds(28, 125, 100, 26);
        contentPane.add(lb3);

        thoat = new JButton("Thoát");
        thoat.setBounds(108, 227, 89, 23);
        contentPane.add(thoat);
        thoat.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == xoa) {
            txt1.setText("");
            txta.setText("");
        } else if (e.getSource() == thoat) {
            System.exit(0);
        } else if (e.getSource() == tachso) {
            String n = txt1.getText().trim();
            String chieu = list.getSelectedItem().toString();

            String ketQua = TachSo(n, chieu);
            txta.setText(ketQua);
        }
    }

    public static String TachSo(String n, String chieu) {
        StringBuilder sb = new StringBuilder();
        if (chieu.equals("Từ phải sang trái")) {
            for (int i = n.length() - 1; i >= 0; i--) {
                sb.append(n.charAt(i));
                if (i > 0)
                    sb.append(", ");
            }
        } else {
            for (int i = 0; i < n.length(); i++) {
                sb.append(n.charAt(i));
                if (i < n.length() - 1)
                    sb.append(", ");
            }
        }
        return sb.toString();
    }

}
