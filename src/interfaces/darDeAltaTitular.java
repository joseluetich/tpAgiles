package src.interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

import src.logica.*;

public class darDeAltaTitular extends JFrame{
    private JPanel panel1;
    private JButton cancelarButton;
    private JComboBox comboBoxTipoDni;
    private JTextField textFieldNroDoc;
    private JTextField textFieldApellido;
    private JTextField textFieldNombre;
    private JTextField textFieldDireccion;
    private JComboBox comboBoxGrupoS;
    private JCheckBox siCheckBox;
    private JButton confirmarButton;
    private JDateChooser JDateFechaNac;
    private JButton atrasButton;
    private static darDeAltaTitular darDeAltaTitularUI;

    public darDeAltaTitular(JFrame frameQueLoEjecuta) {
        darDeAltaTitularUI=this;
        setTitle("Dar de alta titular");
        add(panel1);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
                String grupoS = comboBoxGrupoS.getSelectedItem().toString();
                Boolean donante = siCheckBox.isSelected();
                Date fechaNac = JDateFechaNac.getDate();

                String validacion = AltaTitular.validar(tipoDeDoc, numeroDoc, apellido, nombre, direccion, fechaNac);
                if(validacion.equals("Validado")) {
                    try {
                        AltaTitular.guardarDatos(tipoDeDoc, numeroDoc, apellido, nombre, direccion, grupoS, donante, fechaNac);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
                }
                else if(validacion.equals("errorTipoDoc")) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos una opción de tipo de documento");
                }
                else if(validacion.equals("errorNumeroDoc")) {
                    JOptionPane.showMessageDialog(null, "El numero de documento debe tener una longitud de 8");
                }
                else if(validacion.equals("errorApellido")) {
                    JOptionPane.showMessageDialog(null, "El campo apellido es un campo obligatorio y no debe superar los 50 caracteres");
                }
                else if(validacion.equals("errorNombre")) {
                    JOptionPane.showMessageDialog(null, "El campo nombre es un campo obligatorio y no debe superar los 50 caracteres");
                }
                else if(validacion.equals("errorDireccion")) {
                    JOptionPane.showMessageDialog(null, "El campo direccion es un campo obligatorio y no debe superar los 100 caracteres");
                }
                else if(validacion.equals("errorEdad17")) {
                    JOptionPane.showMessageDialog(null, "El titular debe tener al menos 17 años para seleccionar este tipo de clase");
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
                } else {
                    evt.consume();
                }
            }
        });

        textFieldNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                } else {
                    evt.consume();
                }
            }
        });
        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDeAltaTitularUI.hide();
                frameQueLoEjecuta.show();
            }
        });
    }

    private void createUIComponents() {
        JDateFechaNac = new JDateChooser();
        JDateFechaNac.setDate(new Date());
    }
}
