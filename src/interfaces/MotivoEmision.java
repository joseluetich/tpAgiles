package src.interfaces;

import src.bd.EmitirCopiaDB;
import src.clases.CopiaLicencia;
import src.clases.motivosCopia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MotivoEmision extends JFrame {
    private JPanel motivoEmisionPanel;
    private JRadioButton extravíoRadioButton;
    private JRadioButton roboRadioButton;
    private JRadioButton deterioroRadioButton;
    private JButton emitirCopiaButton;
    private JButton atrásButton;
    private CopiaLicencia copiaLicencia = new CopiaLicencia();
    MotivoEmision motivoEmision;
    MenuPrincipalUI menuPrincipalUI = new MenuPrincipalUI();

    public MotivoEmision(JFrame frameQueLoEjecuta, String numDoc, String claseSolicitada) throws IOException {

        motivoEmision = this;
        add(motivoEmisionPanel);
        setTitle("Motivos de Emisión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 350);
        extravíoRadioButton.setSelected(true);

        extravíoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(extravíoRadioButton.isSelected()){
                    roboRadioButton.setSelected(false);
                    deterioroRadioButton.setSelected(false);
                }
            }
        });
        roboRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roboRadioButton.isSelected()){
                    extravíoRadioButton.setSelected(false);
                    deterioroRadioButton.setSelected(false);
                }
            }
        });
        deterioroRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deterioroRadioButton.isSelected()){
                    extravíoRadioButton.setSelected(false);
                    roboRadioButton.setSelected(false);
                }
            }
        });


        atrásButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motivoEmision.hide();
                frameQueLoEjecuta.show();
            }
        });

        emitirCopiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idLicencia = null;
                try {
                    idLicencia = Integer.parseInt(EmitirCopiaDB.getIdLicencia(numDoc,claseSolicitada));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

               // copiaLicencia.setIdCopiaLicencia(idLicencia);

                Date fechaActual = new Date(); //Obtengo fecha actual
                copiaLicencia.setFechaDeEmision(fechaActual);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaEmision = sdf.format(fechaActual);


                if(extravíoRadioButton.isSelected()){
                    copiaLicencia.setMotivos(motivosCopia.EXTRAVIO);
                }
                if(roboRadioButton.isSelected()){
                    copiaLicencia.setMotivos(motivosCopia.ROBO);
                }
                if(deterioroRadioButton.isSelected()){
                    copiaLicencia.setMotivos(motivosCopia.DETERIORO);
                }

                try {
                    copiaLicencia.setNumeroDeCopia(1+Integer.parseInt(EmitirCopiaDB.getNumCopiasDeLicencia(String.valueOf(idLicencia))));
                    String idCopiaLicencia = EmitirCopiaDB.getIdCopiaLicenciaBD();

                    if(idCopiaLicencia.equals("")){
                        copiaLicencia.setIdCopiaLicencia(1);
                    }
                    else {
                        copiaLicencia.setIdCopiaLicencia(1+Integer.parseInt(idCopiaLicencia));
                    }
                    EmitirCopiaDB.insertCopiaLicencia(copiaLicencia, String.valueOf(idLicencia), fechaEmision);
                    JOptionPane.showMessageDialog(null, "Se ha creado la copia con éxito");
                    motivoEmision.hide();
                    menuPrincipalUI.show();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });


    }
}
