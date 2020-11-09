package src.interfaces;

import src.bd.ConexionDefault;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DatosTitular extends JFrame {

    private JPanel datosTitularPanel;
    private JTextField tipoDocumentoTextField;
    private JTextField nombreTextField;
    private JTextField fechaNacimientoTextField;
    private JTextField direccionTextField;
    private JTextField claseTextField;
    private JTextField grupoSanguineoTextField;
    private JTextField donanteTextField;
    private JTextField documentoTextField;
    private JButton modificarDatosButton;
    private JButton siguienteButton;

    public DatosTitular(String tipoDoc, String  nombre, String apellido, String numDoc, String fechaNacimiento, String direccion, String claseSolicitada, String grupo_y_factor_sanguineo, String donante) {
        add(datosTitularPanel);
        setTitle("Datos del Titular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 350);

        tipoDocumentoTextField.setText(tipoDoc);
        nombreTextField.setText(apellido.toUpperCase()+", "+nombre.toUpperCase());
        fechaNacimientoTextField.setText(fechaNacimiento);
        direccionTextField.setText(direccion);
        claseTextField.setText(claseSolicitada);
        grupoSanguineoTextField.setText(grupo_y_factor_sanguineo);
        donanteTextField.setText(donante);
        documentoTextField.setText(numDoc);

        /*try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }*/

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MotivoEmision motivoEmision = new MotivoEmision();
                motivoEmision.setVisible(true);
            }
        });
    }

    public static void main (String [] args) {
        BusquedaTitular busquedaTitular = new BusquedaTitular();
        busquedaTitular.setVisible(true);

        ConexionDefault conectar = new ConexionDefault();
        Connection con = conectar.openConnection();
    }


}

