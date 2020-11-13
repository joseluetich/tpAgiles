package src.interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class MenuPrincipalUI  extends JFrame {


    private JPanel panelMenuPrincipal;
    private JButton emitirLicenciaButton;
    private JButton darDeAltaTitularButton;
    private JButton emitirCopiaLicenciaButton;
    private JLabel picLabel;
    private static MenuPrincipalUI menuPrincipalUI;

    public static void main(String[] args) throws IOException {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            ///
        }

        menuPrincipalUI = new MenuPrincipalUI();
        menuPrincipalUI.show();

    }

    public MenuPrincipalUI() throws IOException {

        add(panelMenuPrincipal);
        setTitle("Menú principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("interfaces/resources/muni-santafe-icon.png");
        setIconImage(img.getImage());
        setSize(1000,500);
        setLocationRelativeTo(null);
        setResizable(false);

        darDeAltaTitularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDeAltaTitular darDeAltaTitularUI = new darDeAltaTitular(menuPrincipalUI);
                darDeAltaTitularUI.show();
                menuPrincipalUI.hide();
            }
        });

        emitirLicenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EmitirLicenciaUI emitirLicenciaUI = new EmitirLicenciaUI(menuPrincipalUI);
                    emitirLicenciaUI.show();
                    menuPrincipalUI.hide();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        emitirCopiaLicenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BusquedaTitular busquedaTitular = new BusquedaTitular(menuPrincipalUI);
                    busquedaTitular.show();
                    menuPrincipalUI.hide();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
