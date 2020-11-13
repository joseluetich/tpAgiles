package src.interfaces;
import src.bd.ConexionDefault;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.JTableHeader;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import static src.bd.EmitirCopiaDB.*;

public class BusquedaTitular extends JFrame implements MouseListener{
    private JPanel buscarTitularPanel;
    private JTable licenciasTable;
    private JTextField documentoTextField;
    private JButton buscarButton;
    private JButton seleccionarButton;
    private JComboBox documentoComboBox;
    private JPanel buscarPanel;
    private JButton atrásButton;
    private JScrollPane scrollPaneTabla;
    ModeloTabla modelo;
    private int filasTabla = 0;
    private int columnasTabla;

    public BusquedaTitular() throws SQLException {
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

        add(buscarTitularPanel);
        setTitle("Busqueda Titular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        iniciarComponentes();
        setLocationRelativeTo(null);
        construirTabla();
        atrásButton.setVisible(false);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String tipoDoc, num_doc;
                tipoDoc = new String();
                num_doc = new String();
                tipoDoc = documentoComboBox.getSelectedItem().toString();
                num_doc = documentoTextField.getText().toString();

                if(!tipoDoc.equals("Seleccionar") && !num_doc.isEmpty()) {
                    try {
                        buscarTitular(tipoDoc, num_doc);
                        atrásButton.setVisible(true);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Debe completar ambos campos para buscar un titular");
                }
            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoDoc = modelo.getValueAt(licenciasTable.getSelectedRow(),2).toString();
                String numDoc = modelo.getValueAt(licenciasTable.getSelectedRow(),3).toString();
                String claseSolicitada = modelo.getValueAt(licenciasTable.getSelectedRow(),5).toString();

                String datosTitularBD = "";
                try {
                    datosTitularBD = buscarTitularBD(numDoc,tipoDoc);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                assert datosTitularBD != null;

                DatosTitular datosTitular = new DatosTitular(datosTitularBD, tipoDoc, numDoc, claseSolicitada);
                datosTitular.setVisible(true);
            }
        });

        atrásButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    construirTabla();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                atrásButton.setVisible(false);
            }
        });

    }

    public static void main (String [] args)throws SQLException{

        BusquedaTitular busquedaTitular = new BusquedaTitular();
        busquedaTitular.setVisible(true);

        ConexionDefault conectar = new ConexionDefault();
        Connection con = conectar.openConnection();
    }

    private void iniciarComponentes() {

        scrollPaneTabla = new JScrollPane();
        buscarTitularPanel.add(scrollPaneTabla);

        licenciasTable.setBackground(Color.WHITE);
        licenciasTable.addMouseListener(this);
        licenciasTable.setOpaque(false);
        licenciasTable.setSize(150,300);
        scrollPaneTabla.setViewportView( licenciasTable);

        buscarTitularPanel.add(buscarPanel, BorderLayout.AFTER_LAST_LINE);

    }

//Metodo que permite construir la tabla de licencias
//se crean primero las columnas y luego se asigna la información

    private void construirTabla() throws SQLException {

        ArrayList<String> titulosList = new ArrayList<>();

        titulosList.add("Apellido");
        titulosList.add("Nombre");
        titulosList.add("Tipo documento");
        titulosList.add("Documento");
        titulosList.add("Num. licencia");
        titulosList.add("Clase licencia");

        //se asignan las columnas al arreglo para enviarse al momento de construir la tabla
        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i]=titulosList.get(i);
        }

        //obtenemos los datos de la lista y los guardamos en la matriz
        //que luego se manda a construir la tabla

        Object[][] data = obtenerMatrizDatos(titulosList);
        construirTabla(titulos,data);
    }


    private Object[][] obtenerMatrizDatos(ArrayList titulosList) throws SQLException {
        //se crea la matriz donde las filas son dinamicas pues corresponde mientras que las columnas son estaticas

        ArrayList<String> licenciasVigentes = new ArrayList<String>();
        String informacion[][] = new String[0][0];

        try {
            licenciasVigentes = getLicenciasVigentes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert licenciasVigentes != null;
        if(licenciasVigentes.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se pudieron encontrar licencias vigentes");
        }
        else{
            ArrayList<String> licenciasVigentesAux = new ArrayList<String>(licenciasVigentes);

            while (!licenciasVigentesAux.isEmpty()) {
                String[] datosSplitteadosLicencias = licenciasVigentesAux.get(0).split(",");
                String idLicencia = datosSplitteadosLicencias[0];

                ArrayList<String> clasesBD = getClaseByID(idLicencia);

                for (int j = 0; j < clasesBD.size(); j++) filasTabla++;
                licenciasVigentesAux.remove(0);
            }

            System.out.println(filasTabla);
            System.out.println(licenciasVigentesAux);
            System.out.println(licenciasVigentes);

            informacion = new String[filasTabla][titulosList.size()];
            seteoCamposLicencias(licenciasVigentes, informacion);
            filasTabla = 0;
        }

        return informacion;
    }

// Con los titulos y la información a mostrar se crea el modelo para
// poder personalizar la tabla, asignando tamaño de celdas tanto en ancho como en alto
// así como los tipos de datos que va a poder soportar.

    private void construirTabla(String[] titulos, Object[][] data) {
        modelo = new ModeloTabla(data, titulos);

        //se asigna el modelo a la tabla
        licenciasTable.setModel(modelo);

        //filasTabla=data.length;
        //filasTabla=licenciasTable.getRowCount();
        columnasTabla=licenciasTable.getColumnCount();

        //se asigna el tipo de dato que tendrán las celdas de cada columna definida respectivamente para validar su personalización
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.APELLIDO).setCellRenderer(new GestionCeldas("texto"));
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setCellRenderer(new GestionCeldas("texto"));
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.TIPO_DOCUMENTO).setCellRenderer(new GestionCeldas("texto"));
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.DOCUMENTO).setCellRenderer(new GestionCeldas("texto"));
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.NUM_LICENCIA).setCellRenderer(new GestionCeldas("texto"));
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.CLASE_LICENCIA).setCellRenderer(new GestionCeldas("texto"));
        //licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setCellRenderer(new GestionCeldas("numerico"));


        licenciasTable.getTableHeader().setReorderingAllowed(false);
        licenciasTable.setRowHeight(25);//tamaño de las celdas
        licenciasTable.setGridColor(new java.awt.Color(0, 0, 0));

        //Se define el tamaño de largo para cada columna y su contenido
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.APELLIDO).setMinWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setMinWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.TIPO_DOCUMENTO).setMinWidth(13);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.DOCUMENTO).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.NUM_LICENCIA).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.CLASE_LICENCIA).setPreferredWidth(5);

        //personaliza el encabezado
        JTableHeader jtableHeader = licenciasTable.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        licenciasTable.setTableHeader(jtableHeader);

        //se asigna la tabla al scrollPane
        //scrollPaneTabla.setViewportView(licenciasTable);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //capturo fila o columna dependiendo de mi necesidad
        int fila = licenciasTable.rowAtPoint(e.getPoint());
        int columna = licenciasTable.columnAtPoint(e.getPoint());

    }

    //estos metododos pueden ser usados dependiendo de nuestra necesidad, por ejemplo para cambiar el tamaño del icono al ser presionado
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void buscarTitular(String tipoDoc, String num_doc) throws SQLException {

        int filas = licenciasTable.getRowCount();
        ArrayList<String> titulosList = new ArrayList<String>();

        titulosList.add("Apellido");
        titulosList.add("Nombre");
        titulosList.add("Tipo documento");
        titulosList.add("Documento");
        titulosList.add("Num. licencia");
        titulosList.add("Clase licencia");

        String[] titulos = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i]=titulosList.get(i);
        }

        Object[][] informacion = obtenerMatrizDatos(titulosList);

        String[][] nuevaInfoAux = new String[informacion.length][titulosList.size()];
        int j=0;

        for(int i=0; i<filas; i++){
            if(informacion[i][2].equals(tipoDoc) && informacion[i][3].equals(num_doc)){
                nuevaInfoAux[j] = (String[]) informacion[i];
                j++;
            }
        }
        String[][] nuevaInfo = new String[j][titulosList.size()];
        for(int i=0; i<j; i++){
            nuevaInfo[i] = nuevaInfoAux[i];
        }

        if(nuevaInfo.length==0) {
            JOptionPane.showMessageDialog(null, "No se encontraron titulares");
            construirTabla(titulos, informacion);
        } else {
            construirTabla(titulos, nuevaInfo);
        }

    }

    public void seteoCamposLicencias(ArrayList<String> licenciasVigentes, String[][] informacion) {

        int fila = 0;

        while (!licenciasVigentes.isEmpty()) {

            String[] datosSplitteadosLicencias = licenciasVigentes.get(0).split(",");

            String idLicencia = datosSplitteadosLicencias[0];
            String titular = datosSplitteadosLicencias[1];
            String num_licencia = datosSplitteadosLicencias[2];

            String titularBD = "";
            try {
                titularBD = getTitularByID(titular);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert titularBD != null;

            String[] datosSplitteadosTitular = titularBD.split(",");

            String tipoDoc = datosSplitteadosTitular[0];
            String numDoc = datosSplitteadosTitular[1];
            String apellido = datosSplitteadosTitular[2];
            String nombre = datosSplitteadosTitular[3];

            ArrayList<String> clasesBD = new ArrayList<String>();
            try {
                clasesBD = getClaseByID(idLicencia);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert clasesBD != null;

            for (int j = 0; j < clasesBD.size(); j++) {
                informacion[fila][ColumnasTabla.APELLIDO] = apellido;
                informacion[fila][ColumnasTabla.NOMBRE] = nombre;
                informacion[fila][ColumnasTabla.TIPO_DOCUMENTO] = tipoDoc;
                informacion[fila][ColumnasTabla.DOCUMENTO] = numDoc;
                informacion[fila][ColumnasTabla.NUM_LICENCIA] = num_licencia;
                informacion[fila][ColumnasTabla.CLASE_LICENCIA] = clasesBD.get(j);
                fila++;
            }

            licenciasVigentes.remove(0);
        }

    }

}

