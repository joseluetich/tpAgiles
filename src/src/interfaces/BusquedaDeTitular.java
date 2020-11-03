package interfaces;

import javax.swing.*;

public class BusquedaDeTitular extends JFrame {
    private JPanel buscarTitularPanel;
    private JLabel licenceListTextLabel;
    private JTable licenciasVigentesTable;
    private JCheckBox apellidoCheckBox;
    private JCheckBox nombreCheckBox;
    private JCheckBox esDonanteCheckBox;
    private JButton ordenarButton;
    private JComboBox comboBox1;
    private JButton seleccionarButton;

    public BusquedaDeTitular() {
        add(buscarTitularPanel);
        setTitle("BusquedaDeTitular");
        setSize(500,500);
    }
}
