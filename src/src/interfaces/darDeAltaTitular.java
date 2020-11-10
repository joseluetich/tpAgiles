package interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import bd.ConexionDefault;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;


public class darDeAltaTitular {
    private JPanel panel1;
    private JButton button1;
    private JComboBox comboBoxTipoDni;
    private JTextField textFieldNroDoc;
    private JTextField textFieldApellido;
    private JTextField textFieldNombre;
    private JTextField textFieldDireccion;
    private JComboBox comboBoxDireccion;
    private JComboBox comboBoxGrupoS;
    private JCheckBox siCheckBox;
    private JButton confirmarButton;
    private JDateChooser JDateChooser1;
    private JCalendar JCalendar1;


    public darDeAltaTitular() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null,"HOLA");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new darDeAltaTitular().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //ConexionDefault conectar = new ConexionDefault();
        //Connection con = conectar.openConnection();

    }

    private void createUIComponents() {
        JDateChooser1 = new JDateChooser();
    }
}
