package interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

import clases.*;
import logica.*;

public class darDeAltaTitular {
    private JPanel panel1;
    private JButton cancelarButton;
    private JComboBox comboBoxTipoDni;
    private JTextField textFieldNroDoc;
    private JTextField textFieldApellido;
    private JTextField textFieldNombre;
    private JTextField textFieldDireccion;
    private JComboBox comboBoxClase;
    private JComboBox comboBoxGrupoS;
    private JCheckBox siCheckBox;
    private JButton confirmarButton;
    private JDateChooser JDateFechaNac;


    public darDeAltaTitular() {
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String tipoDeDoc = comboBoxTipoDni.getSelectedItem().toString();
                String numeroDoc = textFieldNroDoc.getText().toString();
                String apellido = textFieldApellido.getText().toString();
                String nombre = textFieldNombre.getText().toString();
                String direccion = textFieldDireccion.getText().toString();
                String clase = comboBoxClase.getSelectedItem().toString();
                String grupoS = comboBoxGrupoS.getSelectedItem().toString();
                Boolean donante = siCheckBox.isSelected();
                Date fechaNac = JDateFechaNac.getDate();

                if(validacionesAltaTitular.validar()) {
                    JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
                }
            }
        });

        textFieldNroDoc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (Character.isDigit(c)) {
                } else {
                    evt.consume();
                }
            }
        });

        textFieldApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                } else{
                    evt.consume();
                }
            }
        });

        textFieldNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                } else{
                    evt.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dar de alta titular");
        frame.setContentPane(new darDeAltaTitular().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //ConexionDefault conectar = new ConexionDefault();
        //Connection con = conectar.openConnection();

    }

    private void createUIComponents() {
        JDateFechaNac = new JDateChooser();
    }
}
