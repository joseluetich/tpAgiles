package src.interfaces;
import src.bd.ConexionDefault;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BusquedaTitular extends JFrame implements MouseListener{
    private JPanel buscarTitularPanel;
    private JTable licenciasTable;
    private JTextField documentoTextField;
    private JButton buscarButton;
    private JButton seleccionarButton;
    private JComboBox documentoComboBox;
    private JPanel buscarPanel;
    private JScrollPane scrollPaneTabla;
    ModeloTabla modelo;
    private int filasTabla;
    private int columnasTabla;


    public BusquedaTitular() {
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

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String tipoDoc, num_doc;
                tipoDoc = new String();
                num_doc = new String();
                tipoDoc = documentoComboBox.getSelectedItem().toString();
                num_doc = documentoTextField.getText().toString();

                if(!tipoDoc.equals("Seleccionar") && !num_doc.isEmpty()) {
                    buscarTitular(tipoDoc, num_doc);
                }
            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = licenciasTable.getSelectedRow();
                String  nombre = modelo.getValueAt(licenciasTable.getSelectedRow(),1).toString();
                String apellido = modelo.getValueAt(licenciasTable.getSelectedRow(),0).toString();
                String tipoDoc = modelo.getValueAt(licenciasTable.getSelectedRow(),2).toString();
                String numDoc = modelo.getValueAt(licenciasTable.getSelectedRow(),3).toString();
                String fechaNacimiento = "5/5/1999";
                String direccion = "Pasaje ingenieros 7045";
                String claseSolicitada = "C";
                String grupo_y_factor_sanguineo = "B+";
                String donante = "Sí";

                DatosTitular datosTitular = new DatosTitular(tipoDoc, nombre, apellido,numDoc,fechaNacimiento,direccion,claseSolicitada,grupo_y_factor_sanguineo,donante);
                datosTitular.setVisible(true);
            }
        });

        /*addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });*/
    }

    public static void main (String [] args) {
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

    private void construirTabla() {

        ArrayList<String> titulosList = new ArrayList<String>();

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


    private Object[][] obtenerMatrizDatos(ArrayList titulosList) {
        //se crea la matriz donde las filas son dinamicas pues corresponde mientras que las columnas son estaticas

        String informacion[][] = new String[101][titulosList.size()];

        informacion[0][ColumnasTabla.APELLIDO] = "Triverio";
        informacion[0][ColumnasTabla.NOMBRE] = "Fiorella";
        informacion[0][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[0][ColumnasTabla.DOCUMENTO] = "1";
        informacion[0][ColumnasTabla.NUM_LICENCIA] = "1";
        informacion[0][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[1][ColumnasTabla.APELLIDO] = "Luetich";
        informacion[1][ColumnasTabla.NOMBRE] = "Josefina";
        informacion[1][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[1][ColumnasTabla.DOCUMENTO] = "2";
        informacion[1][ColumnasTabla.NUM_LICENCIA] = "2";
        informacion[1][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[2][ColumnasTabla.APELLIDO] = "Sanchez";
        informacion[2][ColumnasTabla.NOMBRE] = "Exequiel";
        informacion[2][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[2][ColumnasTabla.DOCUMENTO] = "3";
        informacion[2][ColumnasTabla.NUM_LICENCIA] = "3";
        informacion[2][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[3][ColumnasTabla.APELLIDO] = "Guiter";
        informacion[3][ColumnasTabla.NOMBRE] = "Alejandro";
        informacion[3][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[3][ColumnasTabla.DOCUMENTO] = "4";
        informacion[3][ColumnasTabla.NUM_LICENCIA] = "4";
        informacion[3][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[4][ColumnasTabla.APELLIDO] = "Eceiza";
        informacion[4][ColumnasTabla.NOMBRE] = "Belen";
        informacion[4][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[4][ColumnasTabla.DOCUMENTO] = "5";
        informacion[4][ColumnasTabla.NUM_LICENCIA] = "5";
        informacion[4][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[5][ColumnasTabla.APELLIDO] = "David";
        informacion[5][ColumnasTabla.NOMBRE] = "Fausto";
        informacion[5][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[5][ColumnasTabla.DOCUMENTO] = "6";
        informacion[5][ColumnasTabla.NUM_LICENCIA] = "6";
        informacion[5][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[6][ColumnasTabla.APELLIDO] = "Acosta";
        informacion[6][ColumnasTabla.NOMBRE] = "Diego";
        informacion[6][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[6][ColumnasTabla.DOCUMENTO] = "7";
        informacion[6][ColumnasTabla.NUM_LICENCIA] = "7";
        informacion[6][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[7][ColumnasTabla.APELLIDO] = "Acosta";
        informacion[7][ColumnasTabla.NOMBRE] = "Diego";
        informacion[7][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[7][ColumnasTabla.DOCUMENTO] = "7";
        informacion[7][ColumnasTabla.NUM_LICENCIA] = "8";
        informacion[7][ColumnasTabla.CLASE_LICENCIA] = "C";

        for(int i=8; i<101; i++) {
            informacion[i][ColumnasTabla.APELLIDO] = "Apellido";
            informacion[i][ColumnasTabla.NOMBRE] = "Nombre";
            informacion[i][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
            informacion[i][ColumnasTabla.DOCUMENTO] = Integer.toString(i+1);
            informacion[i][ColumnasTabla.NUM_LICENCIA] = Integer.toString(i+1);
            informacion[i][ColumnasTabla.CLASE_LICENCIA] = "B1";
        }

        return informacion;
    }

// Con los titulos y la información a mostrar se crea el modelo para
// poder personalizar la tabla, asignando tamaño de celdas tanto en ancho como en alto
// así como los tipos de datos que va a poder soportar.

    private void construirTabla(String[] titulos, Object[][] data) {
        modelo=new ModeloTabla(data, titulos);

        //se asigna el modelo a la tabla
        licenciasTable.setModel(modelo);

        filasTabla=licenciasTable.getRowCount();
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
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.APELLIDO).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.TIPO_DOCUMENTO).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.DOCUMENTO).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.NUM_LICENCIA).setPreferredWidth(10);
        licenciasTable.getColumnModel().getColumn(ColumnasTabla.CLASE_LICENCIA).setPreferredWidth(10);

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

    public void buscarTitular(String tipoDoc, String num_doc){

        int filas = licenciasTable.getRowCount();
        ArrayList<String> titulosList = new ArrayList<String>();
        String nueva_info[][] = new String[101][titulosList.size()];

        titulosList.add("Apellido");
        titulosList.add("Nombre");
        titulosList.add("Tipo documento");
        titulosList.add("Documento");
        titulosList.add("Num. licencia");
        titulosList.add("Clase licencia");

        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i]=titulosList.get(i);
        }

        String informacion[][] = new String[101][titulosList.size()];

        informacion[0][ColumnasTabla.APELLIDO] = "Triverio";
        informacion[0][ColumnasTabla.NOMBRE] = "Fiorella";
        informacion[0][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[0][ColumnasTabla.DOCUMENTO] = "1";
        informacion[0][ColumnasTabla.NUM_LICENCIA] = "1";
        informacion[0][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[1][ColumnasTabla.APELLIDO] = "Luetich";
        informacion[1][ColumnasTabla.NOMBRE] = "Josefina";
        informacion[1][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[1][ColumnasTabla.DOCUMENTO] = "2";
        informacion[1][ColumnasTabla.NUM_LICENCIA] = "2";
        informacion[1][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[2][ColumnasTabla.APELLIDO] = "Sanchez";
        informacion[2][ColumnasTabla.NOMBRE] = "Exequiel";
        informacion[2][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[2][ColumnasTabla.DOCUMENTO] = "3";
        informacion[2][ColumnasTabla.NUM_LICENCIA] = "3";
        informacion[2][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[3][ColumnasTabla.APELLIDO] = "Guiter";
        informacion[3][ColumnasTabla.NOMBRE] = "Alejandro";
        informacion[3][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[3][ColumnasTabla.DOCUMENTO] = "4";
        informacion[3][ColumnasTabla.NUM_LICENCIA] = "4";
        informacion[3][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[4][ColumnasTabla.APELLIDO] = "Eceiza";
        informacion[4][ColumnasTabla.NOMBRE] = "Belen";
        informacion[4][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[4][ColumnasTabla.DOCUMENTO] = "5";
        informacion[4][ColumnasTabla.NUM_LICENCIA] = "5";
        informacion[4][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[5][ColumnasTabla.APELLIDO] = "David";
        informacion[5][ColumnasTabla.NOMBRE] = "Fausto";
        informacion[5][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[5][ColumnasTabla.DOCUMENTO] = "6";
        informacion[5][ColumnasTabla.NUM_LICENCIA] = "6";
        informacion[5][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[6][ColumnasTabla.APELLIDO] = "Acosta";
        informacion[6][ColumnasTabla.NOMBRE] = "Diego";
        informacion[6][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[6][ColumnasTabla.DOCUMENTO] = "7";
        informacion[6][ColumnasTabla.NUM_LICENCIA] = "7";
        informacion[6][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[7][ColumnasTabla.APELLIDO] = "Acosta";
        informacion[7][ColumnasTabla.NOMBRE] = "Diego";
        informacion[7][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
        informacion[7][ColumnasTabla.DOCUMENTO] = "7";
        informacion[7][ColumnasTabla.NUM_LICENCIA] = "8";
        informacion[7][ColumnasTabla.CLASE_LICENCIA] = "C";

        for(int i=8; i<101; i++) {
            informacion[i][ColumnasTabla.APELLIDO] = "Apellido";
            informacion[i][ColumnasTabla.NOMBRE] = "Nombre";
            informacion[i][ColumnasTabla.TIPO_DOCUMENTO] = "DNI";
            informacion[i][ColumnasTabla.DOCUMENTO] = Integer.toString(i+1);
            informacion[i][ColumnasTabla.NUM_LICENCIA] = Integer.toString(i+1);
            informacion[i][ColumnasTabla.CLASE_LICENCIA] = "B1";
        }

        int j=0;

        for(int i=0; i<filas; i++){
            if(informacion[i][2].equals(tipoDoc) && informacion[i][3].equals(num_doc)){
                String[] filaTemp = informacion[i];
                nueva_info[j] = filaTemp;
                j++;
            }
        }

        construirTabla(titulos, nueva_info);


    }

}

