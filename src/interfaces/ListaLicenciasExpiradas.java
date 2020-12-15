package src.interfaces;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static src.bd.ListadoLicenciasBD.*;
import static src.bd.EmitirCopiaDB.*;

public class ListaLicenciasExpiradas extends JFrame {

    private JDateChooser JDateDesde;
    private JDateChooser JDateHasta;
    private JPanel panel1;
    private JTable table1;
    private JButton atrasButton;
    private JButton buscarButton;
    private JButton cancelarButton;
    private int filasTabla = 0;
    private ArrayList<String> titulosList;
    private String[] titulos;

    private ModeloTabla modelo;

    public static ListaLicenciasExpiradas listaUI;

    public ListaLicenciasExpiradas(JFrame frameQueLoEjecuta) throws SQLException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        listaUI = this;
        add(panel1);
        setTitle("Lista Licencias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        iniciarComponentes();
        setLocationRelativeTo(null);
        construirTabla();

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Date fechaDesde = JDateDesde.getDate();
                Date fechaHasta = JDateHasta.getDate();

                //Validamos que la fecha desde sea menor o igual a la fecha hasta
                if(fechaDesde.before(fechaHasta)||fechaDesde.equals(fechaHasta)){
                    try {
                        Object[][] informacion = obtenerMatrizDatos(titulosList, true, fechaDesde, fechaHasta);
                        construirTabla(titulos, informacion);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha 'desde' que sea inferior o igual a la fecha hasta");



            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDateDesde.setDate(new Date());
                JDateHasta.setDate(new Date());
            }
        });


        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaUI.hide();
                frameQueLoEjecuta.show();
            }
        });

    }

    private void iniciarComponentes() {
        table1.setBackground(Color.WHITE);
        table1.setOpaque(false);
        table1.setSize(150, 300);
    }

    private void construirTabla() throws SQLException {
        titulosList = new ArrayList<>();

        titulosList.add("ID Licencia");
        titulosList.add("Nombre y Apellido");
        titulosList.add("Fecha de Expiración");
        titulosList.add("Clase");

        //se asignan las columnas al arreglo para enviarse al momento de construir la tabla
        titulos = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }

        Object[][] informacion = obtenerMatrizDatos(titulosList, false, null, null);
        construirTabla(titulos, informacion);
    }

    public Object[][] obtenerMatrizDatos(ArrayList titulosList, Boolean filtro, Date desde, Date hasta) throws SQLException {
        //se crea la matriz donde las filas son dinamicas pues corresponde mientras que las columnas son estaticas

        ArrayList<String> licenciasNoVigentes = new ArrayList<String>();
        String informacion[][] = new String[0][0];

        try {
            licenciasNoVigentes = getLicenciasNoVigentes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert licenciasNoVigentes != null;
        if(licenciasNoVigentes.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se pudieron encontrar licencias vigentes");
        }
        else{
            ArrayList<String> licenciasNoVigentesAux = new ArrayList<String>(licenciasNoVigentes);

            while (!licenciasNoVigentesAux.isEmpty()) {
                String[] datosSplitteadosLicencias = licenciasNoVigentesAux.get(0).split(",");
                String idLicencia = datosSplitteadosLicencias[0];

                ArrayList<String> clasesBD = getClaseByID(idLicencia);

                for (int j = 0; j < clasesBD.size(); j++) filasTabla++;
                licenciasNoVigentesAux.remove(0);
            }

            informacion = new String[filasTabla][titulosList.size()];
            if(!filtro) seteoCamposLicencias(licenciasNoVigentes, informacion);
            else {
                try {
                    seteoCamposLicenciasConFechas(licenciasNoVigentes, informacion, desde, hasta);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            filasTabla = 0;
        }

        return informacion;
    }

    public void seteoCamposLicenciasConFechas(ArrayList<String> licenciasNoVigentes, String[][] informacion, Date desde, Date hasta) throws ParseException {
        int fila = 0;
        while (!licenciasNoVigentes.isEmpty()) {
            String[] datosSplitteadosLicencias = licenciasNoVigentes.get(0).split(",");

            String idLicencia = datosSplitteadosLicencias[0];
            String titular = datosSplitteadosLicencias[1];
            String fecha = datosSplitteadosLicencias[2];

            //COMPARAMOS LAS FECHAS DESDE HASTA Y FECHA.

            Boolean filtro_fecha;
            Date fechaD = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
            if(desde.before(fechaD)&&hasta.after(fechaD)) filtro_fecha=true;
            else filtro_fecha=false;

            String titularBD = "";
            try {
                titularBD = getTitularByID(titular);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert titularBD != null;

            String[] datosSplitteadosTitular = titularBD.split(",");
            String apellido_nombre = datosSplitteadosTitular[3] + " " + datosSplitteadosTitular[2];
            ArrayList<String> clasesBD = new ArrayList<String>();
            try {
                clasesBD = getClaseByID(idLicencia);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert clasesBD != null;

            //SI el dato pasa el filtro se procede a insertarlo en la tabla
            if(filtro_fecha) {
                for (int j = 0; j < clasesBD.size(); j++) {
                    informacion[fila][ColumnasTablaLicExp.ID] = idLicencia;
                    informacion[fila][ColumnasTablaLicExp.NOMBRE_APELLIDO] = apellido_nombre;
                    informacion[fila][ColumnasTablaLicExp.FECHA] = fecha;
                    informacion[fila][ColumnasTablaLicExp.CLASE_LICENCIA] = clasesBD.get(j);
                    fila++;
                }
            }

            licenciasNoVigentes.remove(0);
        }
    }

    public void seteoCamposLicencias(ArrayList<String> licenciasNoVigentes, String[][] informacion) {
        int fila = 0;
        while (!licenciasNoVigentes.isEmpty()) {
            String[] datosSplitteadosLicencias = licenciasNoVigentes.get(0).split(",");

            String idLicencia = datosSplitteadosLicencias[0];
            String titular = datosSplitteadosLicencias[1];
            String fecha = datosSplitteadosLicencias[2];

            String titularBD = "";
            try {
                titularBD = getTitularByID(titular);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert titularBD != null;

            String[] datosSplitteadosTitular = titularBD.split(",");

            String apellido_nombre = datosSplitteadosTitular[3] + " " + datosSplitteadosTitular[2];

            ArrayList<String> clasesBD = new ArrayList<String>();
            try {
                clasesBD = getClaseByID(idLicencia);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert clasesBD != null;

            for (int j = 0; j < clasesBD.size(); j++) {
                informacion[fila][ColumnasTablaLicExp.ID] = idLicencia;
                informacion[fila][ColumnasTablaLicExp.NOMBRE_APELLIDO] = apellido_nombre;
                informacion[fila][ColumnasTablaLicExp.FECHA] = fecha;
                informacion[fila][ColumnasTablaLicExp.CLASE_LICENCIA] = clasesBD.get(j);
                fila++;
            }

            licenciasNoVigentes.remove(0);
        }

    }

    private void construirTabla(String[] titulos, Object[][] data) {
        modelo = new ModeloTabla(data, titulos);

        //se asigna el modelo a la tabla
        table1.setModel(modelo);

        //filasTabla=data.length;
        //filasTabla=licenciasTable.getRowCount();
        //columnasTabla = table1.getColumnCount();

        //se asigna el tipo de dato que tendrán las celdas de cada columna definida respectivamente para validar su personalización
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.ID).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.NOMBRE_APELLIDO).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.FECHA).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(ColumnasTablaLicExp.CLASE_LICENCIA).setCellRenderer(new GestionCeldas("texto"));

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
