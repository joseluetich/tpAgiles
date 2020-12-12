package src.interfaces;
import src.bd.ConectarBD;
import src.bd.ConexionDefault;
import src.bd.RenovarLicenciaBD;
import src.bd.EmitirCopiaDB;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.JTableHeader;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import static src.bd.EmitirCopiaDB.*;

public class RenovarLicencia extends JFrame implements MouseListener{
    private JPanel renovarLicenciaPanel;
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
    private static RenovarLicencia renovarLicencia;

    public RenovarLicencia(MenuPrincipalUI menuPrincipalUI) throws SQLException {
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

        renovarLicencia = this;
        add(renovarLicenciaPanel);
        setTitle("Renovar Licencia");
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
                    try {
                        buscarTitular(tipoDoc, num_doc);
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

                if(licenciasTable.getSelectedRowCount()>1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un solo titular");
                }
                else if(licenciasTable.getSelectedRowCount()==0){
                    JOptionPane.showMessageDialog(null, "Seleccione un titular");
                }
                else {
                    String numLicencia = modelo.getValueAt(licenciasTable.getSelectedRow(),4).toString();
                    String clase = modelo.getValueAt(licenciasTable.getSelectedRow(),5).toString();

                    String datosLicencia = "";
                    try {
                        datosLicencia = RenovarLicenciaBD.getIdLicencia(numLicencia,clase);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String[] datosSplitteadosLicencias = datosLicencia.split(",");

                    String idLicencia = datosSplitteadosLicencias[0];
                    String fechaVencimiento = datosSplitteadosLicencias[1];

                    Date dateVencimiento = null;
                    try {
                        dateVencimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVencimiento);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }

                    Date fechaActual = new Date(); //Obtengo fecha actual
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaEmision = sdf.format(fechaActual);

                    int dias = (int) ((dateVencimiento.getTime() - fechaActual.getTime()));
                    System.out.println("Diferencia: "+dias);

                    if(dias>45){
                        JOptionPane.showMessageDialog(null, "Solo se puede realizar una renovación de licencia 45 días antes del vencimiento de la misma");
                    }else{
                        MotivoRenovación motivosRenovacion = null;
                        try {
                            motivosRenovacion = new MotivoRenovación(renovarLicencia,idLicencia);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        motivosRenovacion.show();
                        renovarLicencia.hide();
                    }
                }
            }
        });

        atrásButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renovarLicencia.hide();
                menuPrincipalUI.show();
            }
        });

    }

    private void iniciarComponentes() {

        scrollPaneTabla = new JScrollPane();
        renovarLicenciaPanel.add(scrollPaneTabla);

        licenciasTable.setBackground(Color.WHITE);
        licenciasTable.addMouseListener(this);
        licenciasTable.setOpaque(false);
        licenciasTable.setSize(150,300);
        scrollPaneTabla.setViewportView( licenciasTable);

        renovarLicenciaPanel.add(buscarPanel, BorderLayout.AFTER_LAST_LINE);

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

        String informacion[][] = RenovarLicenciaBD.getInformacionTabla();
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


}