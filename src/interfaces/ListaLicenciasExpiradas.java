package src.interfaces;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ListaLicenciasExpiradas extends JFrame {

    private JDateChooser JDateDesde;
    private JDateChooser JDateHasta;
    private JPanel panel1;
    private ModeloTabla modelo;
    private JTable table1;
    private JButton atrásButton;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private int columnasTabla;

    public static ListaLicenciasExpiradas listaUI;

    public static void main(String[] args) throws IOException, SQLException {
        listaUI = new ListaLicenciasExpiradas();
        listaUI.show();

    }

    public ListaLicenciasExpiradas() throws SQLException {
        add(panel1);
        setTitle("Lista Licencias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setResizable(false);
        construirTabla();
    }

    private void construirTabla() throws SQLException {

        ArrayList<String> titulosList = new ArrayList<>();

        titulosList.add("ID Licencia");
        titulosList.add("Nombre y Apellido");
        titulosList.add("Fecha de Expiración");
        titulosList.add("Clase");

        //se asignan las columnas al arreglo para enviarse al momento de construir la tabla
        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }

        String informacion[][] = new String[0][0];

        //obtenerMatrizDatos(titulosList);
        construirTabla(titulos, informacion);
    }

    private void construirTabla(String[] titulos, Object[][] data) {
        modelo = new ModeloTabla(data, titulos);

        //se asigna el modelo a la tabla
        table1.setModel(modelo);

        //filasTabla=data.length;
        //filasTabla=licenciasTable.getRowCount();
        columnasTabla = table1.getColumnCount();

        //se asigna el tipo de dato que tendrán las celdas de cada columna definida respectivamente para validar su personalización
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.ID).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.NOMBRE_APELLIDO).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.FECHA).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.CLASE_LICENCIA).setCellRenderer(new GestionCeldas("texto"));
        //licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setCellRenderer(new GestionCeldas("numerico"));


        table1.getTableHeader().setReorderingAllowed(false);
        table1.setRowHeight(25);//tamaño de las celdas
        table1.setGridColor(new java.awt.Color(0, 0, 0));

        //Se define el tamaño de largo para cada columna y su contenido
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.ID).setMinWidth(10);
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.NOMBRE_APELLIDO).setMinWidth(10);
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.FECHA).setMinWidth(13);
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.CLASE_LICENCIA).setPreferredWidth(10);

        //personaliza el encabezado
        JTableHeader jtableHeader = table1.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        table1.setTableHeader(jtableHeader);

        //se asigna la tabla al scrollPane
        //scrollPaneTabla.setViewportView(licenciasTable);


    }




    private void createUIComponents() {
        JDateDesde = new JDateChooser();
        JDateDesde.setDate(new Date());

        JDateHasta = new JDateChooser();
        JDateHasta.setDate(new Date());
    }
}
