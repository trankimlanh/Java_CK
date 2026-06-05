package code;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class demo<JCombobox> extends JFrame {
    private JTextField txtInput;
    private JCombobox comboBox1;
    private JTextArea txtAreaResult;
    private JButton btnView, btnReset, btnExit;
    
    public void demo()
    {
        super("Xử lý chuỗi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        
        initUI();
        initEvents();

    }
    /**
     * 
     */
    public void initUI()
    {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        panel1.add(new JLabel("Nhập chuỗi:"));
        panel1.add(txtInput);
        
         JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel2.add(new JLabel("Thực hiện:"));
        panel2.add(comboBox1);
        
         JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel3.add(new JLabel("Kết quả:"));
        panel3.add(new JScrollPane(txtAreaResult));

        btnView  = new JButton("View");
        btnReset = new JButton("Reset");
        btnExit  = new JButton("Exit");

         JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel4.add(btnView);
        panel4.add(btnReset);   
        panel4.add(btnExit);

         JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        panel5.add(panel1);
        panel5.add(panel2); 
        panel5.add(panel3);
        panel5.add(panel4);
          
    }

     public void initEvents()
    {
        JPanel panel = new JPanel();
        
    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
