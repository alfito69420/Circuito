import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Weas extends JFrame {
    private JPanel mainPanel;
    private JTextField txtName;
    private JButton btnClick;
    private JLabel txtLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JRadioButton serieRadioButton;
    private JRadioButton paraleloRadioButton;

    public static void main(String[] args) {
        Weas weas = new Weas();

        weas.setContentPane(weas.mainPanel);
        weas.setTitle("Tituloooo");
        weas.setSize(300, 300);
        weas.setVisible(true);
        weas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        weas.txtLabel.setText("Integrador");
        weas.btnClick.setText("btn");
    }// clos main

    public Weas() {
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
