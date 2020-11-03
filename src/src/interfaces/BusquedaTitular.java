package interfaces;

import javax.swing.*;

public class BusquedaTitular extends JFrame {

    private JLabel licenceListTextLabel;
    private JTable licenciasVigentesTable;
    private JCheckBox apellidoCheckBox;
    private JCheckBox esDonanteCheckBox;
    private JCheckBox nombreCheckBox;
    private JComboBox comboBox1;
    private JButton ordenarButton;
    private JButton seleccionarButton;

    public BusquedaTitular() {
        setTitle("BusquedaTitular");
        setSize(400,500);
    }
}
