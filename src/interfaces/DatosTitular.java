package src.interfaces;

import src.bd.ConexionDefault;
import src.clases.CopiaLicencia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private JButton atrásButton;
    private CopiaLicencia copiaLicencia;
    private static DatosTitular datosTitular;

    public DatosTitular(JFrame frameQueLoEjecuta, String DatosTitular, String tipoDoc, String numDoc, String claseSolicitada) {

        datosTitular = this;
        add(datosTitularPanel);
        setTitle("Datos del Titular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 350);

        //Se llenan los campos del tituar en la pantalla
        seteoCamposTitular(DatosTitular, tipoDoc, numDoc, claseSolicitada);

        //Botón donde se pasa a la siguiente pantalla
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MotivoEmision motivoEmision = null;
                try {
                    //Se crea la instancia de la próxima pantalla
                    motivoEmision = new MotivoEmision(datosTitular, numDoc,claseSolicitada);
                    motivoEmision.show();
                    datosTitular.hide();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //Botón para volver a la pantalla anterior
        atrásButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datosTitular.hide();
                frameQueLoEjecuta.show();
            }
        });
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

