package src.interfaces;

import src.bd.ConexionDefault;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

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

    public DatosTitular(String DatosTitular, String tipoDoc, String numDoc, String claseSolicitada) {
        add(datosTitularPanel);
        setTitle("Datos del Titular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 350);

        seteoCamposTitular(DatosTitular, tipoDoc, numDoc, claseSolicitada);

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

    public static void main (String [] args) throws SQLException {
        BusquedaTitular busquedaTitular = new BusquedaTitular();
        busquedaTitular.setVisible(true);

        ConexionDefault conectar = new ConexionDefault();
        Connection con = conectar.openConnection();
    }

    public void seteoCamposTitular(String datosTitularBD, String tipoDoc, String numDoc, String claseSolicitada){

        String[] datosSplitteados = datosTitularBD.split(",");

        String nombre = datosSplitteados[0];
        String apellido = datosSplitteados[1];
        //String cuil = datosSplitteados[2];
        String direccion = datosSplitteados[3];
        String fechaNacimiento = datosSplitteados[4];
        String grupoSanguineo = datosSplitteados[5];
        String donanteOrganos = datosSplitteados[6];
        //String codigoPostal = datosSplitteados[7];

        tipoDocumentoTextField.setText(tipoDoc);
        nombreTextField.setText(apellido.toUpperCase()+", "+nombre.toUpperCase());
        fechaNacimientoTextField.setText(fechaNacimiento);
        direccionTextField.setText(direccion);
        claseTextField.setText(claseSolicitada);
        grupoSanguineoTextField.setText(grupoSanguineo);
        donanteTextField.setText(donanteOrganos);
        documentoTextField.setText(numDoc);


    }


}

