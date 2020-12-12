package src.interfaces;

import src.bd.EmitirCopiaDB;
import src.bd.RenovarLicenciaBD;
import src.clases.CopiaLicencia;
import src.clases.motivosCopia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MotivoRenovación extends JFrame {
    private JPanel motivoRenovacionPanel;
    private JRadioButton vencimientoRadioButton;
    private JRadioButton modificacionRadioButton;
    private JButton renovarLicenciaButton;
    private JButton atrásButton;
    private CopiaLicencia copiaLicencia = new CopiaLicencia();
    MotivoRenovación motivoRenovación;
   // MenuPrincipalUI menuPrincipalUI = new MenuPrincipalUI();
    private String motivo;

    public MotivoRenovación(JFrame frameQueLoEjecuta, String idLicencia) throws IOException {
        motivoRenovación = this;
        add(motivoRenovacionPanel);
        setTitle("Motivos de Renovación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);
        vencimientoRadioButton.setSelected(true);

        vencimientoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vencimientoRadioButton.isSelected()){
                    modificacionRadioButton.setSelected(false);
                }
            }
        });
        modificacionRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modificacionRadioButton.isSelected()){
                    vencimientoRadioButton.setSelected(false);
                }
            }
        });

        atrásButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motivoRenovación.hide();
                frameQueLoEjecuta.show();
            }
        });

        renovarLicenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modificacionRadioButton.isSelected()){
                    motivo="MODIFICACION";
                }
                else if(vencimientoRadioButton.isSelected()){
                    motivo="VENCIMIENTO";
                }
                try {
                    EmitirLicenciaUI emitirLicenciaUI = new EmitirLicenciaUI(motivoRenovación, motivo, idLicencia);
                    emitirLicenciaUI.show();
                    motivoRenovación.hide();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


    }
}