package interfaces;
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
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class BusquedaDeTitular extends JFrame implements MouseListener{
    private JPanel buscarTitularPanel;
    private JLabel licenceListTextLabel;
    private JTable licenciasVigentesTable;
    private JCheckBox apellidoCheckBox;
    private JCheckBox nombreCheckBox;
    private JCheckBox esDonanteCheckBox;
    private JButton ordenarButton;
    private JComboBox comboGrupoSanguineo;
    private JButton seleccionarButton;
    private JPanel CriteriosOrdenamiento;
    private JScrollPane scrollPaneTabla;
    ModeloTabla modelo;
    private int filasTabla;
    private int columnasTabla;


    public BusquedaDeTitular() {
        add(buscarTitularPanel);
        setTitle("BusquedaDeTitular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        iniciarComponentes();
        setLocationRelativeTo(null);
        construirTabla();


        ordenarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(apellidoCheckBox.isSelected() && !nombreCheckBox.isSelected() && !esDonanteCheckBox.isSelected() && comboGrupoSanguineo.getSelectedIndex()==0){
                    ordenarPorApellido();
                }

                if(!apellidoCheckBox.isSelected() && nombreCheckBox.isSelected() && !esDonanteCheckBox.isSelected() && comboGrupoSanguineo.getSelectedIndex()==0){
                    ordenarPorNombre();
                }

                if(!apellidoCheckBox.isSelected() && !nombreCheckBox.isSelected() && esDonanteCheckBox.isSelected() && comboGrupoSanguineo.getSelectedIndex()==0){
                    ordenarEsDonante();
                }

                if(!apellidoCheckBox.isSelected() && !nombreCheckBox.isSelected() && !esDonanteCheckBox.isSelected() && comboGrupoSanguineo.getSelectedIndex()!=0){
                    ordenarPorGrupoSanguineo();
                }

            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });



    }

    private void iniciarComponentes() {

        buscarTitularPanel = new JPanel();
        buscarTitularPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(buscarTitularPanel);
        buscarTitularPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblTablaPersonas = new JLabel("Licencias vigentes");
        lblTablaPersonas.setFont(new Font("Rockwell", Font.BOLD, 25));
        buscarTitularPanel.add(lblTablaPersonas, BorderLayout.NORTH);

        scrollPaneTabla = new JScrollPane();
        buscarTitularPanel.add(scrollPaneTabla);

        licenciasVigentesTable = new JTable();
        licenciasVigentesTable.setBackground(Color.WHITE);
        licenciasVigentesTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        licenciasVigentesTable.addMouseListener(this);
        licenciasVigentesTable.setOpaque(false);
        scrollPaneTabla.setViewportView(licenciasVigentesTable);

        CriteriosOrdenamiento = new JPanel();
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Criterios de ordenamiento");
        CriteriosOrdenamiento.setBorder(bordejpanel);
        CriteriosOrdenamiento.setLayout(new GridLayout(3,2));
        CriteriosOrdenamiento.add(apellidoCheckBox);
        CriteriosOrdenamiento.add(nombreCheckBox);
        CriteriosOrdenamiento.add(esDonanteCheckBox);
        inicializarComboGrupoSanguineo();
        CriteriosOrdenamiento.add(comboGrupoSanguineo);
        CriteriosOrdenamiento.add(ordenarButton);
        CriteriosOrdenamiento.add(seleccionarButton);
        buscarTitularPanel.add(CriteriosOrdenamiento, BorderLayout.AFTER_LAST_LINE);


    }

//Metodo que permite construir la tabla de licencias
//se crean primero las columnas y luego se asigna la información

    private void construirTabla() {

        ArrayList<String> titulosList=new ArrayList<String>();

        titulosList.add("Apellido");
        titulosList.add("Nombre");
        titulosList.add("Grupo Sanguineo");
        titulosList.add("Donante");
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
        informacion[0][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[0][ColumnasTabla.DONANTE] = "Sí";
        informacion[0][ColumnasTabla.NUM_LICENCIA] = "1";
        informacion[0][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[1][ColumnasTabla.APELLIDO] = "Luetich";
        informacion[1][ColumnasTabla.NOMBRE] = "Josefina";
        informacion[1][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[1][ColumnasTabla.DONANTE] = "Sí";
        informacion[1][ColumnasTabla.NUM_LICENCIA] = "2";
        informacion[1][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[2][ColumnasTabla.APELLIDO] = "Sanchez";
        informacion[2][ColumnasTabla.NOMBRE] = "Exequiel";
        informacion[2][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[2][ColumnasTabla.DONANTE] = "Sí";
        informacion[2][ColumnasTabla.NUM_LICENCIA] = "3";
        informacion[2][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[3][ColumnasTabla.APELLIDO] = "Guiter";
        informacion[3][ColumnasTabla.NOMBRE] = "Alejandro";
        informacion[3][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[3][ColumnasTabla.DONANTE] = "Sí";
        informacion[3][ColumnasTabla.NUM_LICENCIA] = "4";
        informacion[3][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[4][ColumnasTabla.APELLIDO] = "Eceiza";
        informacion[4][ColumnasTabla.NOMBRE] = "Belen";
        informacion[4][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[4][ColumnasTabla.DONANTE] = "Sí";
        informacion[4][ColumnasTabla.NUM_LICENCIA] = "5";
        informacion[4][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[5][ColumnasTabla.APELLIDO] = "David";
        informacion[5][ColumnasTabla.NOMBRE] = "Fausto";
        informacion[5][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[5][ColumnasTabla.DONANTE] = "Sí";
        informacion[5][ColumnasTabla.NUM_LICENCIA] = "6";
        informacion[5][ColumnasTabla.CLASE_LICENCIA] = "B1";

        informacion[6][ColumnasTabla.APELLIDO] = "Acosta";
        informacion[6][ColumnasTabla.NOMBRE] = "Diego";
        informacion[6][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
        informacion[6][ColumnasTabla.DONANTE] = "Sí";
        informacion[6][ColumnasTabla.NUM_LICENCIA] = "7";
        informacion[6][ColumnasTabla.CLASE_LICENCIA] = "B1";

        for(int i=7; i<101; i++) {
            informacion[i][ColumnasTabla.APELLIDO] = "Apellido";
            informacion[i][ColumnasTabla.NOMBRE] = "Nombre";
            informacion[i][ColumnasTabla.GRUPO_SANGUINEO] = "B+";
            informacion[i][ColumnasTabla.DONANTE] = "Sí";
            informacion[i][ColumnasTabla.NUM_LICENCIA] = Integer.toString(i);
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
        licenciasVigentesTable.setModel(modelo);

        filasTabla=licenciasVigentesTable.getRowCount();
        columnasTabla=licenciasVigentesTable.getColumnCount();

        //se asigna el tipo de dato que tendrán las celdas de cada columna definida respectivamente para validar su personalización
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.APELLIDO).setCellRenderer(new GestionCeldas("texto"));
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setCellRenderer(new GestionCeldas("texto"));
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.GRUPO_SANGUINEO).setCellRenderer(new GestionCeldas("texto"));
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.DONANTE).setCellRenderer(new GestionCeldas("texto"));
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NUM_LICENCIA).setCellRenderer(new GestionCeldas("texto"));
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.CLASE_LICENCIA).setCellRenderer(new GestionCeldas("texto"));
        //licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setCellRenderer(new GestionCeldas("numerico"));


        licenciasVigentesTable.getTableHeader().setReorderingAllowed(false);
        licenciasVigentesTable.setRowHeight(25);//tamaño de las celdas
        licenciasVigentesTable.setGridColor(new java.awt.Color(0, 0, 0));

        //Se define el tamaño de largo para cada columna y su contenido
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.APELLIDO).setPreferredWidth(10);
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NOMBRE).setPreferredWidth(10);
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.GRUPO_SANGUINEO).setPreferredWidth(10);
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.DONANTE).setPreferredWidth(10);
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.NUM_LICENCIA).setPreferredWidth(10);
        licenciasVigentesTable.getColumnModel().getColumn(ColumnasTabla.CLASE_LICENCIA).setPreferredWidth(10);

        //personaliza el encabezado
        JTableHeader jtableHeader = licenciasVigentesTable.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        licenciasVigentesTable.setTableHeader(jtableHeader);

        //se asigna la tabla al scrollPane
        scrollPaneTabla.setViewportView(licenciasVigentesTable);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //capturo fila o columna dependiendo de mi necesidad
        int fila = licenciasVigentesTable.rowAtPoint(e.getPoint());
        int columna = licenciasVigentesTable.columnAtPoint(e.getPoint());

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

    public void inicializarComboGrupoSanguineo(){
        comboGrupoSanguineo.addItem("Grupo sanguíneo");
        comboGrupoSanguineo.addItem("0");
        comboGrupoSanguineo.addItem("A");
        comboGrupoSanguineo.addItem("B");
        comboGrupoSanguineo.addItem("AB");
        comboGrupoSanguineo.setEditable(false);
    }


    //A-Z
    public void ordenarPorApellido(){

        JTable tablaOrdenada = new JTable();

        for (int i = 0; i < licenciasVigentesTable.getRowCount(); i++) {
            String apellido = licenciasVigentesTable.getValueAt(i, 0).toString();

        }

        licenciasVigentesTable = tablaOrdenada;

    }

    //A-Z
    public void ordenarPorNombre(){

    }

    //grupo+ - grupo-
    public void ordenarEsDonante(){

    }


    //donantes-no_donantes
    public void ordenarPorGrupoSanguineo(){

    }



}
