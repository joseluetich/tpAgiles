package src.interfaces;
import src.bd.ConexionDefault;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BusquedaTitular extends JFrame {
    private JPanel buscarTitularPanel;
    private JTable licenciasTable;
    private JTextField textField1;
    private JButton buscarButton;
    private JCheckBox esDonanteCheckBox;
    private JScrollPane scrollPaneTabla;
    ModeloTabla modelo;
    private int filasTabla;
    private int columnasTabla;


    public BusquedaTitular() {
        add(buscarTitularPanel);
        setTitle("Busqueda Titular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);




    }

    public static void main (String [] args) {
        BusquedaTitular busquedaTitular = new BusquedaTitular();
        busquedaTitular.setVisible(true);

        ConexionDefault conectar = new ConexionDefault();
        Connection con = conectar.openConnection();
    }

}