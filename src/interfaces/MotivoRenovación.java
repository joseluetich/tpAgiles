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

    public MotivoRenovación(JFrame frameQueLoEjecuta, String idLicencia) throws IOException {

        motivoRenovación = this;
        add(motivoRenovacionPanel);
        setTitle("Motivos de Renovación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 350);
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
                //pasar idLicencia y el motivo de renovacion al emitir
            }
        });


    }
}